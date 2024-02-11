package com.portal26.webhook.app.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Category {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String categoryId;
    private String label;
    private String parent;
    private String score;
    private String confident;
}
