package com.jfeat.am.power.base.client;

import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

public class RestClient extends RestTemplate {
    private HttpHeaders headers = new HttpHeaders();

    public RestClient() {
        this.headers.add("Content-Type", "application/json");
        this.headers.add("Accept", "*/*");
    }

    public HttpHeaders getHeaders() {
        return this.headers;
    }
}
