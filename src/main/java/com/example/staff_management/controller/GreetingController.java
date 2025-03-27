package com.example.staff_management.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import java.util.Locale;

@Slf4j
@RestController
@RequestMapping("/api/v1/greeting")
@RequiredArgsConstructor
public class GreetingController {

    private final MessageSource messageSource;

//    @GetMapping
//    public String greeting2(@RequestParam String code,
//                           @RequestHeader(name = "Accept-Language", required = false, defaultValue = "en") Locale locale) {
//        return messageSource.getMessage(code, null, locale);
//    }

    @GetMapping()
    public String greeting(@RequestParam String code, Locale locale) {
        GreetingController.log.info("abc"+locale.getCountry() + " " + locale.getLanguage());
        return messageSource.getMessage(code, null, locale);
    }

}
