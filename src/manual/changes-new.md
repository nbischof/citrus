## What's new in Citrus 2.7?!

Citrus 2.7 is using Java 8! The Citrus sources are compiled with Java 8 which means that from now on you need at least Java 8 runtime to work with Citrus. With this Java 8 base Citrus
is proud to welcome two new crew members for supporting Selenium and Kubernetes in tests. Not enough we have the following features included in the box.

### Java 8

Citrus is now using Java 8. This is mainly because we need to move on in using latest versions of Spring Framework, Apache Camel and so on. If you are still stuck on Java 7 you can not
update to 2.7 as the Citrus sources are compiled with Java 8. Please contact us in case you really can not update to Java 8 in your project. We can think of a minor bugfix version with Citrus 2.6 base
that still supports Java 7 runtime. On the bright side we can now use the full power of Lambda expressions and other Java 8 features in Citrus code base.  

### Kubernetes support

Citrus is now able to interact with [Kubernetes](http://kubernetes.io/) remote API in order to manage pods, services and other resources on the Kubernetes platform. The Kubernetes client is based
on the [Fabric8 Java client](https://github.com/fabric8io/kubernetes-client) that interacts with the Kubernetes API via REST services. So you can access Kubernetes resources within Citrus in order to change or validate the resource state for containerized testing.
This is very useful when dealing with container application environments as part of the integration tests. Please stay tuned for blog posts and tutorial samples on how Citrus can help you test
Microservices with Docker and Kubernetes. The basic usage is described in section [kubernetes](kubernetes.md).  

### Selenium support

User interface and browser testing has not been a focus within Citrus integration testing until now that we can integrate with the famous [Selenium](http://www.seleniumhq.org/) UI testing library. Thanks to the great contributions
made by the community - especially by [vdsrd@github](https://github.com/vdsrd) - we can use Selenium based actions and features directly in a Citrus test case. The Citrus Java and XML DSL both provide comfortable access to the Selenium API in order to 
simulate user interaction within a browser. The mix of user based actions and Citrus messaging transport simulation gives complete new ways of handling complex integration scenarios. Read more about this in chapter [Selenium](selenium.md).

### Environment based before/after suite

You can enable/disable before and after suite actions based on optional environment or system properties. Users can give property names or property values that are checked before execution. 
Only in case the environment property checks do pass the actions are executed before/after the test suite run.

### WsAddressing header customization

We have improved the header customization options when using SOAP WSAddressing feature. You can now overwrite the default WSAddressing headers per test action in addition to defining the headers on 
client endpoint component level.

### JsonPath data dictionary

Json data dictionary was based on a simple dot notated syntax. Now you can also use more complex JsonPath expressions in order to overwrite elements in Json messages based
on the data dictionary settings in Citrus. Read more about that in chapter [data-dictionary](data-dictionary.md).

### Java DSL test behavior

Test behaviors in Java DSL represent templates in XML DSL. The behavior encapsulates a set of test actions to a group that can be applied to multiple Java DSL tests. This enables
you to combine common test actions in Java DSL with more comfortable reuse of test action definitions. See chapter [test-behaviors](behaviors.md) how to use that. 

### Auto select message type

Default message type for validation tasks in Citrus has been *XML*. Based on this message type the respective message validator implementation applies for *XML*, *JSON*, *plain text* and so on. You can now change this default message type by setting a system property (`citrus.default.message.type`). Also
Citrus improved the auto select algorithm when the default message type is obviously not applicable. When a message arrives in Citrus the receiving action tries to find out which message validator fits best according to the message payload. XML message content is automatically identified 
by `<>` characters. JSON message payloads are identified by `{}` or `[]` characters for objects and array representations. This way Citrus tries to find the best matching message validator for the incoming message. Before that Citrus has always been using the default message type *XML*.

Read about different message validators in [validation](validation.md).

### Default Cucumber steps

The Citrus Cucumber extension now defines default step definitions for Http, Docker and Selenium. These default steps are ready for usage in any Cucumber Citrus feature specification. You can load
the default steps as additional glue packages in your Cucumber options. After that you are ready to go for using the default steps directly in feature specification files. With the extensions you can perform Docker and Selenium commands very easy. Also you can describe the Http REST client-server communication 
in BDD style. Read more about this in [cucumber](cucumber.md).

### Refactoring

Deprecated APIs and classes that coexisted a long time are now removed. If your project is using on of these deprecated classes you may run into compile time errors.
Please have a look at the Citrus API JavaDocs and documentation in order to find out how to use the new APIs and classes that replaced the old deprecated stuff. 

### Bugfixes

Bugs are part of our software developers world and fixing them is part of your daily business, too. Finding and solving issues makes Citrus better every day. For a detailed listing of all bugfixes please refer to the complete changes log of each release in JIRA ([http://www.citrusframework.org/changes-report.html](http://www.citrusframework.org/changes-report.html)).

