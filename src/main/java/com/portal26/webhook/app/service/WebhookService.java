package com.portal26.webhook.app.service;

import com.portal26.webhook.app.constants.WebhookContstants;
import com.portal26.webhook.app.entity.Category;
import com.portal26.webhook.app.entity.WebShrinkerData;
import com.portal26.webhook.app.entity.WebhookDetails;
import com.portal26.webhook.app.pojo.*;
import com.portal26.webhook.app.repository.CustomWebhookRepository;
import com.portal26.webhook.app.repository.WebhookRepository;
import com.portal26.webhook.app.util.WebShrinkClient;
import com.portal26.webhook.app.util.WebhookUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class WebhookService {

    @Autowired
    private WebhookRepository webhookRepository;

    @Autowired
    private CustomWebhookRepository customRepository;

    @Autowired
    WebShrinkClient webShrinkerClient;

    public EventResponse saveWebhookEvents(String tenantName, EventRequest request) throws ParseException, IOException {

        WebhookDetails webhookDetails = new WebhookDetails();
        webhookDetails.setUser_id(request.getUser_id());
        webhookDetails.setUrl(request.getUrl());
        webhookDetails.setEvent_timestamp(request.getEvent_timestamp());
        webhookDetails.setCreationDate(WebhookUtil.convertStrTimeToEpoc(request.getEvent_timestamp()));
        webhookDetails.setBody(request.getBody());
        webhookDetails.setTenant_name(tenantName);
        WebShrinkerApiResponse webShrinkerApiResponse = webShrinkerClient.extractDomainDetails(request.getUrl());
        if (webShrinkerApiResponse != null) {
            WebShrinkerData webShrinkerData = new WebShrinkerData();
            webShrinkerData.setUrl(webShrinkerApiResponse.getUrl());
            List<Category> categoryList = new ArrayList<>();
            webShrinkerApiResponse.getCategories().forEach(categoryPojo -> {
                        Category category = new Category();
                        category.setLabel(categoryPojo.getLabel());
                        category.setParent(categoryPojo.getParent());
                        category.setScore(categoryPojo.getScore());
                        category.setConfident(categoryPojo.getConfident());
                        category.setCategoryId(categoryPojo.getId());
                        categoryList.add(category);
                    }

            );
            webShrinkerData.setCategories(categoryList);
            webhookDetails.setWebShrinkerData(webShrinkerData);
            webhookRepository.save(webhookDetails);
            EventResponse eventResponse = new EventResponse();
            eventResponse.setStatus(WebhookContstants.SUCCESS_MESSAGE);
            return eventResponse;
        } else {

            log.info("Webshrink API Service returned null response...");

            return null;
        }
    }

    public List<SearchResponse> retrieveWebhookDetails(String tenantName, SearchCriteria criteria) throws ParseException {

        List<SearchResponse> listSearchResponse = new ArrayList<>();
        Long fromDate = WebhookUtil.convertQueryStrTimeToEpoch(criteria.getFrom_date());
        Long toDate = WebhookUtil.convertQueryStrTimeToEpoch(criteria.getTo_date());
        List<WebhookDetails> listWebhookDetails =  customRepository.findByFilterSearchCriteria(
                tenantName,criteria.getUser_id(),criteria.getDomain(),fromDate,toDate);

        listWebhookDetails.forEach(webhook->{
            listSearchResponse.add(new SearchResponse(webhook));
        });
        return listSearchResponse;
    }

}
