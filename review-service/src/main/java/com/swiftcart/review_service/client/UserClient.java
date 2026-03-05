package com.swiftcart.review_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "identity-service", path = "/auth")
public interface UserClient {
    @GetMapping("/{id}")
    UserResponse getUserById(@PathVariable("id") Long id);
}
