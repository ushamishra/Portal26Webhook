package com.portal26.webhook.app.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryPojo {

    private String id;
    private String label;
    private String parent;
    private String score;
    private String confident;
}
