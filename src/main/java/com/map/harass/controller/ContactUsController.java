package com.map.harass.controller;


import com.map.harass.entity.ContactUs;
import com.map.harass.service.ContactUsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/contactus")
public class ContactUsController {

    @Autowired
    private ContactUsService contactUsService;

    @PostMapping("/addcontact")
    public ResponseEntity<ContactUs> addContact(@RequestBody ContactUs contactUs){
        ContactUs contactUs1 = contactUsService.saveContactUs(contactUs);
        return ResponseEntity.ok(contactUs1);
    }
    @GetMapping("/getcontact")
    public ResponseEntity<List<ContactUs>> getAllContact(){
        List<ContactUs> contactUsList = contactUsService.getAllContactUs();
        return ResponseEntity.ok(contactUsList);
    }
}
