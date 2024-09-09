package com.map.harass.service;

import com.map.harass.entity.ContactUs;

import java.util.List;

public interface ContactUsService {

     ContactUs saveContactUs(ContactUs contactUs);

     List<ContactUs> getAllContactUs();
}
