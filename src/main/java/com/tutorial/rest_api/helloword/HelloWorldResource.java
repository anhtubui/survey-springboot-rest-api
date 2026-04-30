package com.tutorial.rest_api.helloword;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class HelloWorldResource {

    @GetMapping(path = "/hello-world")
    public String helloWorld() {
        return "Hello World";
    }

    @RequestMapping(path = "/hello-world-bean", method = RequestMethod.GET)
    public HelloWorldBean helloWorldBean() {
        return new HelloWorldBean("Hello World Bean");
    }

    // Path variables
    @RequestMapping(path = "/hello-world-path-param/{name}/message/{message}", method = RequestMethod.GET)
    public HelloWorldBean helloWorldPathParam(@PathVariable String name, @PathVariable String message) {
        return new HelloWorldBean("Hello world, " + name +
                ", " + message);
    }

    // Request parameters
    @RequestMapping(path = "/hello-world-request-param", method = RequestMethod.GET)
    public HelloWorldBean helloWorldRequestParam(@RequestParam String name, @RequestParam String message) {
        return new HelloWorldBean("Hello world, " + name +
                ", " + message);
    }

}
