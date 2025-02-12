package com.example.routes;


import com.example.model.User;
import com.example.processor.XmlToHl7Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class MyRestRoute extends RouteBuilder {

    @Override
    public void configure() {
        // Global REST Configuration
        restConfiguration()
                .component("servlet")  // Use the embedded servlet
//                .contextPath("/api")   // Base path: /camel-api/api/
                .apiProperty("api.title", "Camel REST API")
                .apiProperty("api.version", "1.0");

        // GET API - Simple greeting
        rest("/hello")
                .get()
                .to("direct:helloRoute");

        from("direct:helloRoute")
                .setBody(constant("Hello from Apache Camel!"));

        // POST API - Accepts JSON User object
        rest("/user")
                .post()
                .type(User.class)  // Maps request JSON to User object
                .consumes("application/json")
                .produces("application/json")
                .to("direct:processUser");

        from("direct:processUser")
                .log("Received user: ${body}")
                .unmarshal().json(User.class)
                .log("Converted User: ${body}")
                .process(exchange -> {
                    User user = exchange.getIn().getBody(User.class);
                    exchange.getMessage().setBody("User " + user.getName() + " with age " + user.getAge() + " has been processed.");
                });

        rest("/convert")
                .post()
                .consumes("application/xml")
                .produces("text/plain")
                .to("direct:processXml");

        from("direct:processXml")
                .log("Received XML: ${body}")
                .process(new XmlToHl7Processor()) // Call Java Processor
                .log("Generated HL7 Message: ${body}");
    }
}

