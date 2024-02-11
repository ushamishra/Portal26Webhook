package com.portal26.webhook.app.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class WebShrinkerData {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long webshrinker_reponse_id;

    String url;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="webshrinker_reponse_id",referencedColumnName = "webshrinker_reponse_id")
    List<Category> categories;
}
