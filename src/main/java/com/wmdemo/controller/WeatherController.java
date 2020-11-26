package com.wmdemo.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.InputStream;
import java.util.Random;
import java.util.Scanner;

@RestController
public class WeatherController {

    @PostMapping(value = "/weather", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String weatherForecast(@ModelAttribute("location") String location) {
        Resource resource = new ClassPathResource("weather.json");
        String content = "";

        if (location == null || location.equals("")){
            return "unkonwn location";
        }

        try (InputStream input = resource.getInputStream();) {
            File file = resource.getFile();
            Scanner sc = new Scanner(file);
            StringBuilder builder = new StringBuilder();
            while (sc.hasNextLine()) {
                builder.append(sc.nextLine());
            }
            content = builder.toString();
            content = content.replace("${LOCATION}", location);
            content = content.replace("${TEMPMIN}", String.valueOf(random(18.0, 25.0)));
            content = content.replace("${TEMP}", String.valueOf(random(18.0, 25.0)));
            content = content.replace("${TEMPFEEL}", String.valueOf(random(18.0, 25.0)));
            content = content.replace("${TEMPMAX}", String.valueOf(random(18.0, 25.0)));
        } catch (Exception e) {
            content = "";
        }
        return content;
    }


    double random(double min, double max) {
        Random r = new Random();
        return (r.nextInt((int)((max-min)*10+1))+min*10) / 10.0;
    }

}
