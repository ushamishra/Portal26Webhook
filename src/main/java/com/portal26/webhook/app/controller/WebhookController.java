package com.portal26.webhook.app.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;


import com.portal26.webhook.app.constants.WebhookContstants;
import com.portal26.webhook.app.pojo.*;
import com.portal26.webhook.app.service.WebhookService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/v1/webhooks/")
@Slf4j
public class WebhookController {

    @Autowired
    WebhookService webhookService;

    @PostMapping(path = "/{tenant_name}/events") // push events
    public ResponseEntity addWebhookEvent(@PathVariable String tenant_name,@Valid @RequestBody EventRequest eventDetails)
            throws ParseException, IOException {

        if (log.isDebugEnabled())
            log.debug("tenant_name: {} EventRequest : {} ", tenant_name, eventDetails.toString());

        EventResponse response = webhookService.saveWebhookEvents(tenant_name, eventDetails);
        if (response != null) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ErrorResponse errorresponse = new ErrorResponse();
            errorresponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            errorresponse.setMessage(WebhookContstants.PORTAL26_WEBHOOK_INPUR_ERROR_MESSAGE);
            return new ResponseEntity<>(errorresponse, HttpStatus.OK);
        }

    }

    @PostMapping(path = "/{tenant_name}/query") // query events
    public ResponseEntity searchWebhookEvent(@PathVariable String tenant_name,@RequestBody SearchCriteria criteria) throws ParseException {

        if (log.isDebugEnabled())
            log.debug("tenant_name: {} criteria : {} ", tenant_name, criteria.toString());

        List<SearchResponse> list = webhookService.retrieveWebhookDetails(tenant_name, criteria);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
