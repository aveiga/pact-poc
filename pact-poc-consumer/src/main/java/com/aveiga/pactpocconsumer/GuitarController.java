package com.aveiga.pactpocconsumer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
public class GuitarController {

    private final ProducerClient producerClient;

    public GuitarController(ProducerClient producerClient) {
        this.producerClient = producerClient;
    }

    @GetMapping("/guitars")
    public ResponseEntity<List<Guitar>> findAll() {
        return new ResponseEntity<>(producerClient.getGuitars(), HttpStatus.NOT_IMPLEMENTED);
    }
}
