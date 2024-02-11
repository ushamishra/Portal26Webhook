package com.portal26.webhook.app.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchCriteria {

    private String user_id;
    private String domain;
    private String from_date;
    private String to_date;

}
