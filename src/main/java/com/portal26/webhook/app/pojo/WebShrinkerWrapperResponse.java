package com.portal26.webhook.app.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class WebShrinkerWrapperResponse {

    private List<WebShrinkerApiResponse> data = new ArrayList<>();
}
