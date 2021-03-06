### Receive timeout

In some cases it might be necessary to validate that a message is **not** present on a destination. This means that this action expects a timeout when receiving a message from an endpoint destination. For instance the tester intends to ensure that no message is sent to a certain destination in a time period. In that case the timeout would not be a test aborting error but the expected behavior. And in contrast to the normal behavior when a message is received in the time period the test will fail with error.

In order to validate such a timeout situation the action <expectTimout> shall help. The usage is very simple as the following example shows:

**XML DSL** 

```xml
<testcase name="receiveJMSTimeoutTest">
    <actions>
        <expect-timeout endpoint="myEndpoint" wait="500"/>
    </actions>
</testcase>
```

**Java DSL designer** 

```java
@Autowired
@Qualifier("myEndpoint")
private Endpoint myEndpoint;

@CitrusTest
public void receiveTimeoutTest() {
    receiveTimeout(myEndpoint)
        .timeout(500);
}
```

**Java DSL runner** 

```java
@Autowired
@Qualifier("myEndpoint")
private Endpoint myEndpoint;

@CitrusTest
public void receiveTimeoutTest() {
    receiveTimeout(action -> action.endpoint(myEndpoint)
                    .timeout(500));
}
```

The action offers two attributes:

*  **endpoint** : Reference to a message endpoint that will try to receive messages.

*  **wait/timeout** : Time period to wait for messages to arrive



Sometimes you may want to add some selector on the timeout receiving action. This way you can very selective check on a message to not be present on a message destination. This is possible with defining a message selector on the test action as follows.

**XML DSL** 

```xml
<expect-timeout endpoint="myEndpoint" wait="500">
  <select>MessageId='123456789'<select/>
<expect-timeout/>
```

**Java DSL designer** 

```java
@CitrusTest
public void receiveTimeoutTest() {
    receiveTimeout(myEndpoint)
        .selector("MessageId = '123456789'")
        .timeout(500);
}
```

**Java DSL runner** 

```java
@CitrusTest
public void receiveTimeoutTest() {
    receiveTimeout(action -> action.endpoint(myEndpoint)
                    .selector("MessageId = '123456789'")
                    .timeout(500));
}
```

