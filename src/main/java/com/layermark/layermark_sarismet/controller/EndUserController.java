package com.layermark.layermark_sarismet.controller;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/endUser")
public class EndUserController {

    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> options){
        return "login is done!";
    }

    @PostMapping("/register")
    public String register(@RequestBody Map<String, String> options){
        return "register is done!";
    }

    @PostMapping("/submit_opinion/survey_id={_survey_id}&options_id={_options_id}")
    public String submitOpinion(@PathVariable("_survey_id") String survey_id,@PathVariable("_options_id") String options_id){
        return "register is done!";
    }

    @GetMapping("/survey_id={_survey_id}&options_id={_options_id}")
    public String hello(@PathVariable("_survey_id") String survey_id,@PathVariable("_options_id") String options_id){
        return survey_id+options_id;
    }
}
