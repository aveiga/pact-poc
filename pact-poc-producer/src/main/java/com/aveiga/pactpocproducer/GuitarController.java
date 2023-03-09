package com.aveiga.pactpocproducer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController()
public class GuitarController {

    @GetMapping("/guitars")
    public ResponseEntity<List<Guitar>> findAll() {
        List<Guitar> guitars = Arrays.asList(new Guitar("Fender", "Strat"), new Guitar("Gibson", "Les Paul"), new Guitar("Jackson", "Warrior"));
        return new ResponseEntity<>(guitars, HttpStatus.OK);
    }
}
