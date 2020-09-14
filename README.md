# secs4java8

## Introduction

This library is SEMI-SECS-communicate implementation on Java8.

## Supports

- SECS-I (SEMI-E4)
- SECS-II (SEMI-E5)
- GEM (SEMI-E30, partially)
- HSMS-SS (SEMI-E37.1)
- [SML (PEER Group)](https://www.peergroup.com/expertise/resources/secs-message-language/)

## Create Communicator instance and open

- For use HSMS-SS-Passive

```
    /* HSMS-SS-Passive open example */
    HsmsSsCommunicatorConfig config = new HsmsSsCommunicatorConfig();
    config.protocol(HsmsSsProtocol.PASSIVE);
    config.socketAddress(new InetSocketAddress("127.0.0.1", 5000));
    config.sessionId(10);
    config.isEquip(true);
    config.timeout().t3(45.0F);
    config.timeout().t6( 5.0F);
    config.timeout().t7(10.0F);
    config.timeout().t8( 6.0F);
    config.gem().mdln("MDLN-A");
    config.gem().softrev("000001");

    SecsCommunicator passive = HsmsSsCommunicator.open(config);
```

- For use HSMS-SS-Active

```
    /* HSMS-SS-Active open example */
    HsmsSsCommunicatorConfig config = new HsmsSsCommunicatorConfig();
    config.protocol(HsmsSsProtocol.ACTIVE);
    config.socketAddress(new InetSocketAddress("127.0.0.1", 5000));
    config.sessionId(10);
    config.isEquip(false);
    config.timeout().t3(45.0F);
    config.timeout().t5(10.0F);
    config.timeout().t6( 5.0F);
    config.timeout().t8( 6.0F);

    SecsCommunicator active = HsmsSsCommunicator.open(config);
```

- For use SECS-I (onTcpIp)

```
    /* SECS-I (onTcpIp) open example */
    Secs1OnTcpIpConfig config = new Secs1OnTcpIpConfig();
    config.socketAddress(new InetSocketAddress("127.0.0.1", 10000));
    config.deviceId(10);
    config.isMaster(true);
    config.isEquip(true);
    config.timeout().t1( 1.0F);
    config.timeout().t2(15.0F);
    config.timeout().t3(45.0F);
    config.timeout().t4(45.0F);
    config.retry(3);

    SecsCommunicator secs1 = Secs1OnTcpIpCommunicator.open(config);
```

How to convert TCP/IP <-> RS232C
- [Use XPort](https://www.lantronix.com/products/xport/)
- [Use Raspberry Pi sample](/src/main/raspi-python/TcpIpSerialConverter)

## Send Primary-Message, and receive Reply-Message

1. Create SECS-II

```
    /* example */
    Secs2 secs2 = Secs2.list(               /* <L                       */
        Secs2.binary((byte)0x81),           /*   <B  0x81>              */
        Secs2.uint2(1001),                  /*   <U2 1001>              */
        Secs2.ascii("ON FIRE")              /*   <A  "ON FIRE">         */
    );                                      /* >.                       */
```

See also ["/src/examples/example3/ExampleBuildSecs2.java"](/src/examples/example3/ExampleBuildSecs2.java)

2. Send Primary-Message

```
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
    If Timeout-T3, throw SecsWaitReplyMessageException.  

## Received Primary-Message, parse, and send Reply-Message

1. Add Listener

```
    /* Add-Listener example */
    active.addSecsMessageReceiveListener((SecsMessage msg) -> {

        int strm     = msg.getStream();     /* Stream-Number   */
        int func     = msg.getFunction();   /* Function-Number */
        boolean wbit = msg.wbit();          /* W-Bit           */
        Secs2 secs2  = msg.secs2();         /* SECS-II         */
    });
```

2. SECS-II parse

```
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


See also ["/src/examples/example4/ExampleGetSecs2Value.java"](/src/examples/example4/ExampleGetSecs2Value.java)

3. Send Reply-Message

```
    Secs2 reply = Secs2.binary((byte)0x0);  /* <B 0x0> */

    active.send(
        primaryMsg, /* Primary-Message */
        5,          /* Stream-Number   */
        2,          /* Function-Number */
        false,      /* W-Bit           */
        reply       /* Reply-SECS-II   */
    );
```

## SML

1. GetInstance SML-Parser

```
    SmlMessageParser parser = SmlMessageParser.getInstance();
```

2. Send Primary-Message

```
    /* Send S1F1 W. example */
    SmlMessage primarySml = parser.parse(
        "S1F1 W."
    );

    active.send(primarySml);
```

3. Send Reply-Message

```
    /* Send S1F2. example */
    SmlMessage replySml = parser.parse(
        "S1F2           " +
        "<L             " +
        "   <A 'MDLN-A'>" +
        "   <A '000001'>" +
        ">.             "
    );

    passive.send(primarySecsMsg, replySml);
```

## GEM

Access by SecsCommunicator#gem

### Clock

```
    /* Send S2F17, parse to LocalDateTime example */
    Clock clock = passive.gem().s2f17();
    LocalDateTime ldt = clock.toLocalDateTime();

    /* Reply S2F18 Now example */
    active.gem().s2f18(Clock.from(LocalDateTime.now()));
    or
    active.gem().s2f18(Clock.now());
    or
    active.gem().s2f18Now();

    /* Send S2F31 Now example */
    TIACK tiack = active.gem().s2f31(Clock.from(LocalDateTime.now()));
    or
    TIACK tiack = active.gem().s2f31(Clock.now());
    or
    TIACK tiack = active.gem().s2f31Now();

    /* Receive S2F31, parse to LocalDateTime example */
    Clock clock = Clock.from(recvS2F31Msg.secs2());
    LocalDateTime ldt = clock.toLocalDateTime();
```

    TimeFormat (A[12] or A[16]) can be set from `AbstractConfig#gem#clockSize`.

### Others

```
    /* example */
    COMMACK commack = active.gem().s1f13();
    OFLACK  oflack  = active.gem().s1f15();
    ONLACK  onlack  = acitve.gem().s1f17();

    passive.gem().s1f14(primaryMsg, COMMACK.OK);
    passive.gem().s1f16(primaryMsg);
    passive.gem().s1f18(primaryMsg, ONLACK.OK);

    passive.gem().s5f2(primaryMsg, ACKC5.OK);
    passive.gem().s6f12(primaryMsg, ACKC6.OK);
    passive.gem().s9f1(referenceMsg);
    passive.gem().s9f3(referenceMsg);
    passive.gem().s9f5(referenceMsg);
    passive.gem().s9f7(referenceMsg);
    passive.gem().s9f9(referenceMsg);
    passive.gem().s9f11(referenceMsg);

    Secs2 dataId = passive.gem().autoDataId();
```
