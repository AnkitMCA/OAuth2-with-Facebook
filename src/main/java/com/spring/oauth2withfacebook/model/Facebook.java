package com.spring.oauth2withfacebook.model;

import com.spring.oauth2withfacebook.service.ApiBinding;
import org.springframework.social.facebook.api.User;

import java.util.LinkedHashMap;

public class Facebook extends ApiBinding {

    private static final String GRAPH_API_BASE_URL = "https://graph.facebook.com/v4.0";

    public Facebook(String accessToken) {
        super(accessToken);
    }

    public User getProfile() {
        return restTemplate.getForObject(GRAPH_API_BASE_URL + "/me", org.springframework.social.facebook.api.User.class);
    }

    public String getImageUrl() {
        Object obj = restTemplate.getForObject(GRAPH_API_BASE_URL + "/me/picture?redirect=false", Object.class);
        LinkedHashMap<String, Object> pictureData = (LinkedHashMap<String, Object>) obj;
        LinkedHashMap<String, Object> data = (LinkedHashMap<String, Object>) pictureData.get("data");
        String imageUrl = (String) data.get("url");

        return imageUrl;
    }
}
