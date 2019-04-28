package model;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class MessageMixIn {

    MessageMixIn(@JsonProperty("name") String name,
                 @JsonProperty("content") String content,
                 @JsonProperty("startTime") String startTime
                 ){
    }
}
