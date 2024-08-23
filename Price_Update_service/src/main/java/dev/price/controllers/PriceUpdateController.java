package dev.price.controllers;

import dev.price.models.PriceUpdateRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/price-update")
public class PriceUpdateController {

    @Value("${product.management.service.url}")
    private String productManagementServiceUrl;

    private final RestTemplate restTemplate;

    public PriceUpdateController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping
    public ResponseEntity<String> updatePrice(@Valid @RequestBody PriceUpdateRequest request) {
        try {
            // Construct URL for the Product Management Service
            String url = productManagementServiceUrl + "/" + request.getProductId();

            // Prepare request body for the PUT call
            Map<String, Object> updates = new HashMap<>();
            updates.put("price", request.getNewPrice());

            // Make a PUT request to update the price
            restTemplate.put(url, updates);

            return ResponseEntity.ok("Price updated successfully for product: " + request.getProductId());
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to update price", ex);
        }
    }
}
