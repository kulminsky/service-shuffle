package com.test.shuffle.controller;

import com.test.shuffle.model.ShuffleRequest;
import com.test.shuffle.service.ShuffleService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@RestController
@RequestMapping("/shuffle")
public class ShuffleController {

    private static final Logger logger = LoggerFactory.getLogger(ShuffleController.class);

    @Value("${service.log.url}")
    private String logServiceUrl;

    private final ShuffleService shuffleService;
    private final RestTemplate restTemplate;

    public ShuffleController(ShuffleService shuffleService, RestTemplate restTemplate) {
        this.shuffleService = shuffleService;
        this.restTemplate = restTemplate;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Integer>> shuffleArray(@Valid @RequestBody ShuffleRequest request) {
        List<Integer> shuffledArray = shuffleService.shuffleArray(request.getNumber());

        // Log the request asynchronously
        new Thread(() -> logRequest(request)).start();
        logger.info("Completed request to logging service");
        return ResponseEntity.ok(shuffledArray);
    }

    private void logRequest(ShuffleRequest request) {
        restTemplate.postForEntity(logServiceUrl, request, Void.class);
    }

}
