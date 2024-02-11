package com.portal26.webhook.app.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class WebhookDetails {

	  @Id
	  @GeneratedValue(strategy=GenerationType.AUTO)
	  @Column(name ="Id")
	  private Long id;

	  @Column(name ="user_id")
	  private String user_id;

	  @Column(name ="event_timestamp")
	  private String event_timestamp;

	  @Column(name ="event_creation_date")
	  private long creationDate;

	  @Column(name ="url")
	  private String url;

	  @Column(name ="body")
	  private String body;

	  @Column(name ="tenant_name")
	  private String tenant_name;

	  @OneToOne(cascade = CascadeType.ALL)
	  @JoinColumn(name="webshrinker_reponse_id")
	  WebShrinkerData webShrinkerData;

}
