package com.layermark.layermark_sarismet.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Reference;

import java.util.List;
import java.util.Map;

public class Survey {

    @Id
    private String id;

    private List<Map<String,String>> options;

    private String topic;

    public String getId() {
        return id;
    }

    public List<Map<String,String>> getOptions() {
        return options;
    }

    public void setQuestions(List<Map<String,String>> options) {
        this.options = options;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
