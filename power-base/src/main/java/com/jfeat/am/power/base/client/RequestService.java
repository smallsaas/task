package com.jfeat.am.power.base.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public class RequestService {

//    public static final String BASE_URL = "http://127.0.0.1:8801";
    private static final String BASE_URL = "http://power-sn:8080";
    public RestResponse get(String api, Map<String, String> params) {
        RestClient client = new RestClient();
        String url = BASE_URL.concat(api);
        HttpEntity entity = new HttpEntity(client.getHeaders());
        ResponseEntity<RestResponse> responseEntity =
                client.exchange(url, HttpMethod.GET,entity, RestResponse.class, params);
        return null == responseEntity ? null : responseEntity.getBody();
    }
}
