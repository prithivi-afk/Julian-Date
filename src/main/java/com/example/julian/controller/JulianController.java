package com.example.julian.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
public class JulianController {

    @GetMapping("/")
    public String form(Model model) {
        model.addAttribute("date", "");
        model.addAttribute("jd", null);
        return "index";
    }

    @PostMapping("/convert")
    public String convert(@RequestParam String date, Model model) {
        try {
            LocalDate d = LocalDate.parse(date);
            double jd = toJulianDate(d);
            model.addAttribute("date", date);
            model.addAttribute("jd", jd);
        } catch (Exception e) {
            model.addAttribute("error", "Invalid date format. Use YYYY-MM-DD");
        }
        return "index";
    }

    private double toJulianDate(LocalDate date) {
        int year = date.getYear();
        int month = date.getMonthValue();
        int day = date.getDayOfMonth();

        if (month <= 2) {
            year -= 1;
            month += 12;
        }

        int A = year / 100;
        int B = 2 - A + (A / 4);

        double jd = Math.floor(365.25 * (year + 4713))
                + Math.floor(30.6001 * (month + 1))
                + day + B - 1524.5;

        return jd;
    }
}
