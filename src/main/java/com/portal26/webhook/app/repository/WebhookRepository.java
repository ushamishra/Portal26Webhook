package com.portal26.webhook.app.repository;

import com.portal26.webhook.app.entity.WebhookDetails;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface WebhookRepository extends JpaRepository<WebhookDetails, Integer> {

}
