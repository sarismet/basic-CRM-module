package com.layermark.layermark_sarismet.controller;

import com.layermark.layermark_sarismet.model.Survey;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admin")

public class AdminController {

    @PostMapping("/login")
    public String adminLogin(@RequestBody Map<String, String> options){
        return "adminLogin is done!";
    }

    @PostMapping("/create_survey")
    public String createSurvey(@RequestBody Map<String, String> options){
        return "createSurvey is done!";
    }

    @PutMapping("/update_survey/id={_id}")
    public String updateSurvey(@RequestBody Survey survey, @PathVariable("_id") String id){
        return "updateSurvey is done!";
    }

    @PostMapping("/create_users")
    public String createUsers(@RequestBody Map<String, String> options){
        return "createUsers is done!";
    }

    @DeleteMapping("/delete_survey/id={_id}")
    public String deleteSurvey(@PathVariable("_id") String id){
        return "deleteSurvey is done!";
    }

    @GetMapping("/")
    public String hello(){
        return "hello from server side!";
    }
}


