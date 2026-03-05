package com.swiftcart.order_service.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
public class FeignClientInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        // Grab the current incoming request (from Postman)
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (attributes != null) {
            // Extract the "Authorization" header (JWT token)
            String token = attributes.getRequest().getHeader("Authorization");

            // If a token exists, stick it onto the outgoing Feign request!
            if (token != null) {
                requestTemplate.header("Authorization", token);
                System.out.println("Feign Interceptor: Successfully attached JWT token to outgoing request.");
            }
        }
    }
}