package com.example.a100nts.common;

import android.os.StrictMode;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public final class RestUtil {

    public static final RestTemplate REST_TEMPLATE;
    public static final ObjectMapper OBJECT_MAPPER;
    public static final String SERVER_URL;

    static {
        REST_TEMPLATE = new RestTemplate();
        List<HttpMessageConverter<?>> converters = new ArrayList<>();
        converters.add(new MappingJackson2HttpMessageConverter());
        REST_TEMPLATE.setMessageConverters(converters);

        OBJECT_MAPPER = new ObjectMapper();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        SERVER_URL = "http://IPv4:8080/api/v1";
    }

    private RestUtil() {
    }

}
