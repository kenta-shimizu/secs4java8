# secs4java8 HSMS-GS

## Create Communicator instance and open

- For use HSMS-GS-Passive example

```java
    /* HSMS-SS-Passive open example */
    HsmsGsCommunicatorConfig config = new HsmsGsCommunicatorConfig();
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

    HsmsGsCommunicator passive = HsmsGsCommunicator.open(config);
```



## Send Primary-Message and receive Reply-Message

## Received Primary-Message, parse, and send Reply-Message

## Detect Communicatable-state changed

See also ["/src/examples/example5/ExampleGem.java"](/src/examples/example5/ExampleGem.java)  
