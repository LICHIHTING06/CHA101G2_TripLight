package com.tw.form.controller;


import com.tw.form.dto.ContactData;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContactController {

    @PostMapping("/contacts")
    public Boolean getContact(@RequestBody ContactData contactData){
        System.out.println(contactData);
        return true;
    }
}
