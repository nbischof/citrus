## Changes in Citrus 1.4.x

### Refactoring

It was time for us to do some code refactoring in Citrus. Many users struggled with the configuration of the Citrus components and project setup was too verbose in some areas. This is why we tried to improve things with working over the basic concepts and components in Citrus.

The outcome is a new Citrus 1.4 which has new configuration components for sending and receiving messages. Also the client and server components for HTTP and SOAP have changed in terms of simplification. Unfortunately refactoring comes along with code deprecation. This is why you have to also change your project code and configuration in the future. This is especially when you have made code adjustments and extensions to the Citrus API.

The good news now is that with Citrus 1.4 both old and new configuration works fine, so you do not have to change your existing project configuration when coming from Citrus 1.3.x and earlier versions. But there is a lot of code marked as deprecated in Citrus 1.4. Have a look at what has been marked as deprecated and update your code to use the new API.

We have set up a migration sheet for users coming from Citrus 1.3.x and earlier versions in order to find a quick overview of what has changed and how to use the new configuration components: [http://citrusframework.org/migration-sheet.html](Citrus 1.4 migration-sheet)

### Data dictionaries

Data dictionaries define dynamic placeholders for message payload element values in general manner. In terms of setting the same message element across all test cases and all test actions the dictionary provides an easy key-value approach.

When dealing with any kind of message payload Citrus will ask the data dictionary for possible translation of the message elements contained. The dictionary keys do match to a specific message element defined by XPath expression or document path expression for instance. The respective value is then set on all messages in Citrus (inbound and outbound).

Dictionaries do apply to XML or JSON message data and can be defined in global or specific scope. Find out more detailed information about this topic in [data-dictionary](data-dictionary)

### Mail adapter

With the new mail adapter you are able to both send and receive mail messages within a test case. The new Citrus mail client produces a mail mime part message with usual mail headers and a text body part. Optional attachment parts are supported, too.

On the server side Citrus provides a SMTP server to accept client mail messages. The incoming mail messages can have multiple text parts and attachment parts. As usual you can validate the incoming mail messages regarding headers and payload with the well known validation capabilities in Citrus.

Read more about the new mail module in [mail](mail)

### Endpoint adapter

Endpoint adapters help to customize the behavior of a Citrus server such as HTTP or SOAP web servers. The endpoint adapter is responsible of creating an endpoint that responds to inbound requests. You can customize the behavior so the Citrus server handles incoming requests as you like.

By default the Citrus server uses a channel endpoint adapter so incoming messages get forwarded to an in memory message channel. There are several other implementations available as endpoint adapter. Read more about that in [endpoint-adapter](endpoint-adapter)

### Global variables component

We added a global variables XML configuration component for more comfortable usage in basic Spring application context configuration. The component is able to create new global variables that are valid across all Citrus test cases. This can also be done by loading a property file from an external file resource. Find out how to us it in [testcase-global-variables](testcase-global-variables)

### Json text validator mode

The Json text validator is now able to operate in two different modes. The **strict** mode is the default mode and validation includes also a strict check on all sub-objects and JSON array elements. So if there is an object missing the validation will fail immediately. Sometimes it may be accurate to only validate a subset of all JSON objects in the data structure. Therefore the non-strict mode does not check on object attribute counts. See more description in [validation-json](validation-json)

### HTTP REST specific Java DSL options

When sending and receiving HTTP messages on REST APIs you can now use interface specific options in the Java DSL. This refers to request uri, context path, query parameters and HTTP status codes for instance. With this enhancement you are now more comfortable in handling REST API calls in Citrus. Find out how to us it in [http](http)

### SOAP HTTP validation

While receiving SOAP messages over HTTP we are now able to also verify the used HTTP uri, context-path and query parameters. You can expect clients to use those values in your receive action as you would do in normal HTTP communication within Citrus. This completes the HTTP server validation when using SOAP over HTTP. Read more about it in [soap-webservices](soap-webservices)

### Apache Camel integration

Apache Camel is a great enterprise integration platform that implements the enterprise integration patterns for building powerful mediation and routing rules for message based integration applications. With the new support for camel endpoints in Citrus you can interact with Apache Camel components for sending and receiving messages. Apache Camel offers a fine support for different message transports that now can be used in Citrus also. In addition to that you can put your Camel application to the test with loading of the Apache Camel context with all your route definitions. Citrus is able to interact with these routes in asynchronous and synchronous communication scenarios. Read about it in [camel](camel).

### Vert.x integration

Vert.x is a very powerful application platform that provides scalable messaging for several message transports such as HTTP, WebSockets, TCP and more. Vert.x also has a distributed event bus that connects multiple Vert.x instances across the network. With Citrus 1.4 the Vert.x platform is integrated with Citrus event bus endpoints. So you can participate in communicating to the Vert.x event bus from Citrus test case. This enables you to add automated integration tests to the Vert.x platform. Read about that in [vertx](vertx).

### Dynamic endpoint components

Endpoints represent the base component in Citrus for sending and receiving messages. The endpoint usually is defined inside the Citrus Spring application context as Spring bean component. Now it is also possible to create dynamic endpoint definitions at test runtime. This comes in very handy when you just want to send or receive a message with Citrus as is. You do not need to add the complete endpoint configuration but only use a special endpoint uri pattern. Citrus will create the endpoint at runtime automatically. Learn how to use the dynamic endpoint pattern in [endpoint-components](endpoint-components).

