package com.daitobedai.nocode;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/api/hello")
public class helloWorld {

    @RequestMapping(method = RequestMethod.GET)
    public
    @ResponseBody
    String home() {
        return "Hello World!";
    }
}