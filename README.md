# secs4java8

## Introduction
This library is SEMI-SECS-communicate implementation on Java8.

### Supports
- SECS-I (SEMI-E4)
- SECS-II (SEMI-E5)
- HSMS-SS (SEMI-E37.1)
- SML (PEER Group)

## Create Communicator instance and open
- For use HSMS-SS Passive
```
    /* HSMS-SS Passive open sample */
    HsmsSsCommunicatorConfig config = new HsmsSsCommunicatorConfig();
    config.protocol(HsmsSsProtocol.PASSIVE);
    config.socketAddress(new InetSocketAddress("127.0.0.1", 5000));
    config.sessionId(10);
    config.isEquip(true);
    config.timeout().t3(45.0F);

    SecsCommunicator passive = HsmsSsCommunicator.open(config);
```

- For use HSMS-SS Active
```
    /* HSMS-SS Active open sample */
    HsmsSsCommunicatorConfig config = new HsmsSsCommunicatorConfig();
    config.protocol(HsmsSsProtocol.ACTIVE);
    config.socketAddress(new InetSocketAddress("127.0.0.1", 5000));
    config.sessionId(10);
    config.isEquip(false);
    config.timeout().t3(45.0F);
    config.timeout().t5(10.0F);

    SecsCommunicator active = HsmsSsCommunicator.open(config);
```

- For use SECS-I (onTcpIp)
```
    /* SECS-I (onTcpIp) open sample */
    Secs1OnTcpIpConfig config = new Secs1OnTcpIpConfig();
    config.socketAddress(new InetSocketAddress("127.0.0.1", 10000));
    config.deviceId(10);
    config.isMaster(true);
    config.isEquip(true);
    config.timeout().t3(45.0F);
    config.retry(3);

    SecsCommunicator secs1 = Secs1OnTcpIpCommunicator.open(config);
```

## Send Primary-Message, and receive Reply-Message
- Create SECS-II
```
    /* Sample */
    Secs2 secs2 = Secs2.list(               /* <L                       */
        Secs2.binary((byte)0x81)            /*   <B  0x81>              */
        , Secs2.int2(1000)                  /*   <I2 1000>              */
        , Secs2.ascii("error message")      /*   <A  "error message">   */
    );                                      /* >.                       */
```

- Send Primary-Message
```
    /* Send S5F1 W. sample */
    Optional<SecsMessage> reply = passive.send(
        5           /* Stream-Number */
        , 1         /* Function-Number */
        , true      /* W-Bit */
        , secs2     /* SECS-II */
    );
```

- Receive Reply-Message
```
    SecsCommunicator#send is blocking-method.
    Blocking until reply-message received.
    Optional has value if W-Bit is true.
    Optional is empty if W-Bit is false.
    If Timeout-T3, throw SecsWaitReplyMessageException.
```

## Received Primary-Message, parse, and send Reply-Message

- Add Listener
```
    /* Add-Listener sample */
    active.addSecsMessageReceiveListener((SecsMessage primaryMsg) -> {

        int strm = msg.getStream();     /* Stream-Number */
        int func = msg.getFunction();   /* Function-Number */
        boolean wbit = msg.wbit();      /* W-Bit */
        Secs2 secs2 = msg.secs2();      /* SECS-II */
    });
```

- SECS-II parse
```
    /* Sample Receive Message */
    S5F1 W
    <L [3]
        <B  [1] 0x81>           #ALCD (0, 0)
        <I2 [1] 1000>           #ALID (1, 0)
        <A "error message">     #ALTX (2)
    >. 

    byte   alcd = msg.secs2().getByte(0, 0);
    int    alid = msg.secs2().getInt(1, 0);
    String altx = msg.secs2().getAscii(2);
```

- support parse methods maxtix

| method | B | BOOLEAN | A | I1 | I2 | I4 | I8 | F4 | F8 | U1 | U2 | U4 | U8 |
|:---|:---:|:---:|:---:|:---:|:---:|:---:|:---:|:---:|:---:|:---:|:---:|:---:|:---:|
| getByte       | ○ |   |   |   |   |   |   |   |   |   |   |   |   |
| getBoolean    |   | ○ |   |   |   |   |   |   |   |   |   |   |   |
| getAscii      |   |   | ○ |   |   |   |   |   |   |   |   |   |   |
| getInt        |   |   |   | ○ | ○ | ○ | ○ | ○ | ○ | ○ | ○ | ○ | ○ |
| getLong       |   |   |   | ○ | ○ | ○ | ○ | ○ | ○ | ○ | ○ | ○ | ○ |
| getBigInteger |   |   |   | ○ | ○ | ○ | ○ | ○ | ○ | ○ | ○ | ○ | ○ |
| getFloat      |   |   |   | ○ | ○ | ○ | ○ | ○ | ○ | ○ | ○ | ○ | ○ |
| getDouble     |   |   |   | ○ | ○ | ○ | ○ | ○ | ○ | ○ | ○ | ○ | ○ |


- Send Reply-Message
```
    Secs2 reply = Secs2.binary((byte)0x0);  /* <B 0x0> */

    active.send(
        primaryMsg  /* Primary-Message */
        , 5         /* Stream-Number */
        , 2         /* Function-Number */
        , false     /* W-Bit */
        , reply     /* Reply-SECS-II */
    );
```

## SML
- GetInstance SML-Parser
```
    SmlMessageParser parser = SmlMessageParser.getInstance();
```

- Send Primary-Message
```
    /* Send S1F1 W. sample */
    SmlMessage primarySml = parser.parse(
        "S1F1 W."
    );

    active.send(primarySml);
```
- Send Reply-Message
```
    /* Send S1F2. sample */
    SmlMessage replySml = parser.parse(
        "S1F2
        <L
            <A 'MODELNO'>
            <A 'VERSION'>
        >."
    );

    passive.send(primarySecsMsg, replySml);
```
