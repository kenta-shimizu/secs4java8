# secs4java8 HSMS-GS

## Create HSMS-GS-Communicator instance and open

- For use HSMS-GS-Passive example

```java
    /* HSMS-GS-Passive open example */
    HsmsGsCommunicatorConfig config = new HsmsGsCommunicatorConfig();
    config.addSessionId(100);
    config.addSessionId(200);
    config.addSessionId(300);
    config.socketAddress(new InetSocketAddress("127.0.0.1", 5000));
    config.isEquip(true);
    config.connectionMode(HsmsConnectionMode.PASSIVE);
    config.rebindIfPassive(5.0F);
    config.isTrySelectRequest(false);
    config.timeout().t3(45.0F);
    config.timeout().t6( 5.0F);
    config.timeout().t8( 5.0F);
    config.gem().mdln("MDLN-A");
    config.gem().softrev("000001");
    config.gem().clockType(ClockType.A16);

    HsmsGsCommunicator passive = HsmsGsCommunicator.newInstance(config);
    passive.open();
```

- For use HSMS-GS-Active example

```java
    /* HSMS-GS-Active open example */
    HsmsGsCommunicatorConfig config = new HsmsGsCommunicatorConfig();
    config.addSessionId(100);
    config.addSessionId(200);
    config.addSessionId(300);
    config.socketAddress(new InetSocketAddress("127.0.0.1", 5000));
    config.isEquip(false);
    config.connectionMode(HsmsConnectionMode.ACTIVE);
    config.isTrySelectRequest(true);
    config.retrySelectRequestTimeout(5.0F);
    config.timeout().t3(45.0F);
    config.timeout().t5(10.0F);
    config.timeout().t6( 5.0F);
    config.timeout().t8( 5.0F);
    config.linktest(180.0F);
    config.gem().clockType(ClockType.A16);

    HsmsGsCommunicator active = HsmsGsCommunicator.newInstance(config);
    active.open();
```

Notice: `HsmsGsCommunicator` is **NOT** instance of `SecsCommunicator`.

## Get Session instance from HsmsGsCommunicator

`HsmsSession` is instance of `SecsCommunicator`. `SecsCommunicator` methods are available.

```java
    /* from Session-ID */
    HsmsSession session100 = passive.getSession(100);

    /* Session Set */
    Set<HsmsSession> sessions = passive.getSessions();
```

See also ["/src/examples/example6"](/src/examples/example6)
