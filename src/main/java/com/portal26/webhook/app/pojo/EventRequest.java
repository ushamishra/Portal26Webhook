package com.portal26.webhook.app.pojo;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventRequest {

    @NotEmpty(message = "User Id can not be null or empty")
    @Size(min=3,message = "Minimum characters should be atleast 3")
    private String user_id;

    @NotEmpty(message = "Event timestamp can not be null or empty")
    private String event_timestamp;

    @NotEmpty(message = "Domain URL timestamp can not be null or empty")
    @Size(min=4,message = "Minimum characters should be atleast 4")
    private String url;

    @NotEmpty(message = "Payload can not be null or empty")
    private String body;
}
