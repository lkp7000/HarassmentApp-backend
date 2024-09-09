package com.map.harass.service;

import com.map.harass.entity.ContactUs;
import com.map.harass.repository.ContactUsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactUsServiceImpl implements ContactUsService{

    @Autowired
    private ContactUsRepository contactUsRepository;
    @Override
    public ContactUs saveContactUs(ContactUs contactUs) {
        return contactUsRepository.save(contactUs);
    }

    @Override
    public List<ContactUs> getAllContactUs() {
        return contactUsRepository.findAll();
    }
}
