package com.layermark.layermark_sarismet.model;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Map;

@Document
@EqualsAndHashCode
@ToString
public class Survey {

    @Id
    private String id;
    private String topic;
    private Map<String,String> options;
    private boolean isAnswered;
    public boolean isAnswered() {
        return isAnswered;
    }
    public void setAnswered(boolean answered) {
        isAnswered = answered;
    }
    public String getId() {
        return id;
    }
    public Map<String,String>getOptions() {
        return options;
    }
    public void setOptions(Map<String,String> options) {
        if(!isAnswered()) this.options = options;
    }
    public String getTopic() {
        return topic;
    }
    public void setTopic(String topic) {
        if(!isAnswered()) {
            this.topic = topic;
        }
    }
}
