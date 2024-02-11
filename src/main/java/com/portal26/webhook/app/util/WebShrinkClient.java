package com.portal26.webhook.app.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;


import com.portal26.webhook.app.pojo.WebShrinkerApiResponse;
import com.portal26.webhook.app.pojo.WebShrinkerWrapperResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;


@Component
public class WebShrinkClient {

    @Value("${webshrinker.api.url}")
    private String WEBSHRINK_SERVICE_URL;

    @Value("${webshrinker.key}")
    private String WEBSHRINK_KEY;

    @Value("${webshrinker.secret}")
    private String WEBSHRINK_SECRET;

    private HttpHeaders getHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Basic " + Base64.getEncoder().encodeToString(
                (WEBSHRINK_KEY + ":" + WEBSHRINK_SECRET).getBytes()));
        return httpHeaders;
    }

    public WebShrinkerApiResponse extractDomainDetails(String url) throws IOException {

        System.out.println("extractDomainDetails "+url);
        String final_url = WEBSHRINK_SERVICE_URL + Base64.getEncoder().encodeToString(url.getBytes());

        URL weburl = new URL(final_url);
        HttpURLConnection conn = (HttpURLConnection) weburl.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        // snippet begins
        conn.setRequestProperty("Authorization",
                "Basic " + Base64.getEncoder().encodeToString(
                        (WEBSHRINK_KEY + ":" + WEBSHRINK_SECRET).getBytes()
                )
        );

        //read data from webshrinker response
        BufferedReader br = null;
        StringBuilder sbr = new StringBuilder();
        if (conn.getResponseCode() == 200) {
            System.out.println("response found ");
            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String strCurrentLine;
            while ((strCurrentLine = br.readLine()) != null) {
                sbr.append(strCurrentLine);
            }

            String jsonString = sbr.toString();
            System.out.println("response found "+jsonString);
            WebShrinkerWrapperResponse webShrinkerWrapperResponse = new ObjectMapper().readValue(jsonString, WebShrinkerWrapperResponse.class);
            return webShrinkerWrapperResponse.getData().get(0);

        } else {
            System.out.println("response not found ");
            return null;
        }
    }
}
