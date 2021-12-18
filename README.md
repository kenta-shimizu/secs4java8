# secs4java8

## Introduction

This library is SEMI-SECS-communicate implementation on Java8.

## Supports

- SECS-I (SEMI-E4)
- SECS-II (SEMI-E5)
- GEM (SEMI-E30, partially)
- HSMS(SEMI-E37), HSMS-SS(SEMI-E37.1), HSMS-GS(SEMI-E37.2)
- [SML (PEER Group)](https://www.peergroup.com/expertise/resources/secs-message-language/)

## Create Communicator instance and open

- For use HSMS-SS-Passive example

```java
    /* HSMS-SS-Passive open example */
    HsmsSsCommunicatorConfig config = new HsmsSsCommunicatorConfig();
    config.connectionMode(HsmsConnectionMode.PASSIVE);
    config.socketAddress(new InetSocketAddress("127.0.0.1", 5000));
    config.sessionId(10);
    config.isEquip(true);
    config.timeout().t3(45.0F);
    config.timeout().t6( 5.0F);
    config.timeout().t7(10.0F);
    config.timeout().t8( 5.0F);
    config.gem().mdln("MDLN-A");
    config.gem().softrev("000001");
    config.gem().clockType(ClockType.A16);

    SecsCommunicator passive = HsmsSsCommunicator.newInstance(config);
    passive.open();
```

- For use HSMS-SS-Active example

```java
    /* HSMS-SS-Active open example */
    HsmsSsCommunicatorConfig config = new HsmsSsCommunicatorConfig();
    config.connectionMode(HsmsConnectionMode.ACTIVE);
    config.socketAddress(new InetSocketAddress("127.0.0.1", 5000));
    config.sessionId(10);
    config.isEquip(false);
    config.timeout().t3(45.0F);
    config.timeout().t5(10.0F);
    config.timeout().t6( 5.0F);
    config.timeout().t8( 5.0F);
    config.linktest(120.0F);
    config.gem().clockType(ClockType.A16);

    SecsCommunicator active = HsmsSsCommunicator.newInstance(config);
    active.open();
```

- For Use HSMS-GS

  to [HSMS-GS README](/README-HSMS-GS.md)

- For use SECS-I (onTcpIp) example

```java
    /*
        SECS-I (onTcpIp) open example.
        This is connect/client type connection.
        This and 'Secs1OnTcpIpReceiverCommunicator' are a pair.
    */
    Secs1OnTcpIpCommunicatorConfig config = new Secs1OnTcpIpCommunicatorConfig();
    config.socketAddress(new InetSocketAddress("127.0.0.1", 10000));
    config.deviceId(10);
    config.isMaster(true);
    config.isEquip(true);
    config.timeout().t1( 1.0F);
    config.timeout().t2(15.0F);
    config.timeout().t3(45.0F);
    config.timeout().t4(45.0F);
    config.retry(3);
    config.gem().clockType(ClockType.A16);

    SecsCommunicator secs1 = Secs1OnTcpIpCommunicator.newInstance(config);
    secs1.open();
```

- For use SECS-I (onTcpIp) Receiver example

```java
    /*
        SECS-I (onTcpIp) Receiver open example.
        This is bind/server type connection.
        This and 'Secs1OnTcpIpCommunicator' are a pair.
    */
    Secs1OnTcpIpReceiverCommunicatorConfig config = new Secs1OnTcpIpReceiverCommunicatorConfig();
    config.socketAddress(new InetSocketAddress("127.0.0.1", 10000));
    config.deviceId(10);
    config.isMaster(false);
    config.isEquip(false);
    config.timeout().t1( 1.0F);
    config.timeout().t2(15.0F);
    config.timeout().t3(45.0F);
    config.timeout().t4(45.0F);
    config.retry(3);
    config.gem().clockType(ClockType.A16);

    SecsCommunicator secs1r = Secs1OnTcpIpReceiverCommunicator.newInstance(config);
    secs1r.open();
```

How to convert TCP/IP <-> RS232C
- [Use XPort](https://www.lantronix.com/products/xport/)
- [Use Raspberry Pi sample](/src/main/raspi-python/TcpIpSerialConverter)

## Send Primary-Message and receive Reply-Message

1. Create SECS-II

```java
    /* example */
    Secs2 secs2 = Secs2.list(               /* <L                       */
        Secs2.binary((byte)0x81),           /*   <B  0x81>              */
        Secs2.uint2(1001),                  /*   <U2 1001>              */
        Secs2.ascii("ON FIRE")              /*   <A  "ON FIRE">         */
    );                                      /* >.                       */
```

See also ["/src/examples/example3/ExampleBuildSecs2.java"](/src/examples/example3/ExampleBuildSecs2.java)

2. Send Primary-Message

```java
    /* Send S5F1 W. example */
    Optional<SecsMessage> reply = passive.send(
        5,          /* Stream-Number   */
        1,          /* Function-Number */
        true,       /* W-Bit           */
        secs2       /* SECS-II         */
    );
```

3. Receive Reply-Message

    SecsCommunicator#send is blocking-method.  
    Blocking until reply-message received.  
    Optional has value if W-Bit is true.  
    Optional is empty if W-Bit is false.  
    If T3-Timeout, throw SecsWaitReplyMessageException.  

## Received Primary-Message, parse, and send Reply-Message

1. Add Listener

```java
    /* Add-Listener example */
    active.addSecsMessageReceiveListener((SecsMessage msg) -> {

        int strm     = msg.getStream();     /* Stream-Number   */
        int func     = msg.getFunction();   /* Function-Number */
        boolean wbit = msg.wbit();          /* W-Bit           */
        Secs2 secs2  = msg.secs2();         /* SECS-II         */
    });
```

2. Parse SECS-II

```java
    /* example Receive Message */
    S5F1 W
    <L [3]
        <B  [1] 0x81>           /* ALCD (0, 0) */
        <U2 [1] 1001>           /* ALID (1, 0) */
        <A  "ON FIRE">          /* ALTX (2)    */
    >. 

    byte   alcd = msg.secs2().getByte(0, 0);
    int    alid = msg.secs2().getInt(1, 0);
    String altx = msg.secs2().getAscii(2);
```

### Support parse methods

| method | B | BOOLEAN | A | I1 | I2 | I4 | I8 | F4 | F8 | U1 | U2 | U4 | U8 |
|:-|:-:|:-:|:-:|:-:|:-:|:-:|:-:|:-:|:-:|:-:|:-:|:-:|:-:|
| getByte       | ✓ |   |   |   |   |   |   |   |   |   |   |   |   |
| getBoolean    |   | ✓ |   |   |   |   |   |   |   |   |   |   |   |
| getAscii      |   |   | ✓ |   |   |   |   |   |   |   |   |   |   |
| getInt        |   |   |   | ✓ | ✓ | ✓ | ✓ | ✓ | ✓ | ✓ | ✓ | ✓ | ✓ |
| getLong       |   |   |   | ✓ | ✓ | ✓ | ✓ | ✓ | ✓ | ✓ | ✓ | ✓ | ✓ |
| getBigInteger |   |   |   | ✓ | ✓ | ✓ | ✓ | ✓ | ✓ | ✓ | ✓ | ✓ | ✓ |
| getFloat      |   |   |   | ✓ | ✓ | ✓ | ✓ | ✓ | ✓ | ✓ | ✓ | ✓ | ✓ |
| getDouble     |   |   |   | ✓ | ✓ | ✓ | ✓ | ✓ | ✓ | ✓ | ✓ | ✓ | ✓ |
| getNumber     | ✓ |   |   | ✓ | ✓ | ✓ | ✓ | ✓ | ✓ | ✓ | ✓ | ✓ | ✓ |


See also ["/src/examples/example4/ExampleGetSecs2Value.java"](/src/examples/example4/ExampleGetSecs2Value.java)

3. Send Reply-Message

```java
    Secs2 reply = Secs2.binary((byte)0x0);  /* <B 0x0> */

    active.send(
        primaryMsg, /* Primary-Message */
        5,          /* Stream-Number   */
        2,          /* Function-Number */
        false,      /* W-Bit           */
        reply       /* Reply-SECS-II   */
    );
```

## Detect Communicatable-state changed

- Add Listener

```java
    /* Add-Listener example */
    active.addSecsCommunicatableStateChangeListener((boolean communicatable) -> {

        if ( communicatable ) {
            System.out.println("communicatable");
        } else {
            System.out.println("not communicatable");
        }
    });
```

Notice: This listener is blocking-method. pass through quickly.

- Waiting until communicate-state-changed

```java
    active.waitUntilCommunicatable();
    active.waitUntilNotCommunicatable();

    /* Open communicator and waiting until communicatable */
    active.openAndWaitUntilCommunicatable();
```

Notice: This method is blocking-method.

## SML

1. Get SML-Parser instance

```java
    SmlMessageParser parser = SmlMessageParser.getInstance();
```

2. Send Primary-Message

```java
    /* Send S1F1 W. example */
    SmlMessage primarySml = parser.parse(
        "S1F1 W."
    );

    active.send(primarySml);
```

3. Send Reply-Message

```java
    /* Send S1F2. example */
    SmlMessage replySml = parser.parse(
        "S1F2             " +
        "<L               " +
        "   <A \"MDLN-A\">" +
        "   <A \"000001\">" +
        ">.               "
    );

    passive.send(primaryMsg, replySml);
```

## GEM

Access from SecsCommunicator#gem

### Dynamic Event Report Configuration (User Side)

1. Create Configuration instance

```java
    DynamicEventReportConfig evRptConf = active.gem().newDynamicEventReportConfig();
```

2. Add Configs

- Add Define Reports

    Can be aliased.  
    RPTID is auto number.

```java
    /* no ALias */
    DynamicReport rptSimple = evRptConf.addDefineReport(
        Arrays.asList(
            Long.valueOf(1001L),    /* VID-1        */
            ...
        )
    );

    /* set Alias */
    DynamicReport rptAlias = evRptConf.addDefineReport(
        "report-alias",             /* Report-ALias */
        Arrays.asList(
            Long.valueOf(2001L),    /* VID-1        */
            ...
        )
    );
```

- Add Enable Collection Events

    Can be aliased.

```java
    /* no Alias */
    DynamicCollectionEvent evSimple = evRptConf.addEnableCollectionEvent(
        101L            /* CEID         */
    );

    /* set Alias */
    DynamicCollectionEvent evAlias = evRptConf.addEnableCollectionEvent(
        "event-alias",  /* Event-Alias  */
        201L            /* CEID         */
    );
```

- Add Links

```java
    /* by DynamicReport */
    evRptConf.addLinkByReport(
        evAlias,                /* DynamicCollectionEvent   */
        Arrays.asList(
            rptAlias,           /* DynamicReport-1          */
            ...
        )
    );

    /* by Report-ID */
    evRptConf.addLinkById(
        evAlias,                /* DynamicCollectionEvent   */
        Arrays.asList(
            Long.valueOf(1L),   /* RPTID-1                  */
            ...
        )
    );
```

3. Execute Scenario

```java
    ERACK erack = evRptConf.s2f37DisableAll();
    DRACK drack = evRptConf.s2f33DeleteAll();
    DRACK drack = evRptConf.s2f33Define();
    LRACK lrack = evRptConf.s2f35();
    ERACK erack = evRptConf.s2f37Enable();
```

    DATAID is auto number.

If has Report-Alias, S6F19 and S6F21 called by Alias.

```java
    Optional<SecsMessage> s6f20 = evRptConf.s6f19("report-alias");
    Optional<SecsMessage> s6f22 = evRptConf.s6f21("report-alias");
```

If has Event-Alias, S6F15 and S6F17 called by Alias.

```
    Optional<SecsMessage> s6f16 = evRptConf.s6f15("event-alias");
    Optional<SecsMessage> s6f18 = evRptConf.s6f17("event-alias");
```

From CEID in received S6F11 or S6F13, Get Event-Alias if aliased.

```java
    Secs2 ceid = recvS6F11Msg.secs2().get(1);   /* Get CEID SECS-II */

    Optional<DynamicCollectionEvent> op = evRptConf.getCollectionEvent(ceid);
    Optional<String> alias              = op.flatMap(ev -> ev.alias()); /* Get Event-ALias */
```

From RPTID in received S6F11 or S6F13, Get Report-Alias if aliased.

```java
    Secs2 rptid = recvS6F11Msg.secs2().get(2, ...);   /* Get RPTID SECS-II */

    Optional<DynamicReport> op = evRptConf.getReport(rptid);
    Optional<String> alias     = op.flatMap(rpt -> rpt.alias());    /* Get Report-Alias */
```

### Clock

- Send S2F17 and receive reply, parse to LocalDateTime

```java
    Clock clock = passive.gem().s2f17();
    LocalDateTime ldt = clock.toLocalDateTime();
```

- Reply S2F18 Now examples

```java
    active.gem().s2f18(primaryMsg, Clock.from(LocalDateTime.now()));
    active.gem().s2f18(primaryMsg, Clock.now());
    active.gem().s2f18Now(primaryMsg);
```

- Send S2F31 Now examples

```java
    TIACK tiack = active.gem().s2f31(Clock.from(LocalDateTime.now()));
    TIACK tiack = active.gem().s2f31(Clock.now());
    TIACK tiack = active.gem().s2f31Now();
```

- Receive S2F31, parse to LocalDateTime

```java
    Clock clock = Clock.from(recvS2F31Msg.secs2());
    LocalDateTime ldt = clock.toLocalDateTime();
```

TimeFormat (A[12] or A[16]) can be set from `AbstractSecsCommunicatorConfig#gem#clockType`.

### Others

```java
    /* examples */
    COMMACK commack = active.gem().s1f13();
    OFLACK  oflack  = active.gem().s1f15();
    ONLACK  onlack  = acitve.gem().s1f17();

    passive.gem().s1f14(primaryMsg, COMMACK.OK);
    passive.gem().s1f16(primaryMsg);
    passive.gem().s1f18(primaryMsg, ONLACK.OK);

    active.gem().s5f2(primaryMsg, ACKC5.OK);
    active.gem().s6f12(primaryMsg, ACKC6.OK);

    passive.gem().s9f1(referenceMsg);
    passive.gem().s9f3(referenceMsg);
    passive.gem().s9f5(referenceMsg);
    passive.gem().s9f7(referenceMsg);
    passive.gem().s9f9(referenceMsg);
    passive.gem().s9f11(referenceMsg);

    Secs2 dataId = passive.gem().autoDataId();
```

See also ["/src/examples/example5/ExampleGem.java"](/src/examples/example5/ExampleGem.java)  
