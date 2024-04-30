package com.example.laby.controller;

import com.example.laby.service.HeartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class HeartController {

    private final HeartService heartService;

//    @PostMapping("/heart")
//    public ResponseEntity<>
}
