package com.layermark.layermark_sarismet.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/survey")
public class SurveyController {

    @GetMapping("/get_survey_result/id={_id}")
    public String getSurveyResult(@PathVariable("_id") String id){
        return "getSurveyResult is done!";
    }
}
