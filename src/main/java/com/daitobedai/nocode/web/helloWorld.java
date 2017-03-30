package com.daitobedai.nocode.web;

import com.daitobedai.nocode.dao.SomethingBad;
import com.daitobedai.nocode.domain.badUrl;
import com.daitobedai.nocode.util.DruidCipherUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/hello")
public class helloWorld {

    @Autowired
    private SomethingBad somethingBad;

    @RequestMapping(method = RequestMethod.GET)
    public
    @ResponseBody
    List<badUrl> home(@RequestParam(value = "text",required = false) String text) {
        List<badUrl> strings = somethingBad.getUrl(text);
        return strings;
    }

    @RequestMapping(value = "/encrypt", method = RequestMethod.GET)
    public
    @ResponseBody
    String encrypt(@RequestParam(value = "plainText") String plainText) {
        DruidCipherUtil druidCipherUtil = new DruidCipherUtil();
        return druidCipherUtil.encrypt(plainText);
    }
}