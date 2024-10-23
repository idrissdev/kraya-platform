package com.kraya.platform.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public abstract class AbstractController {
    // Common functionality for all controllers can be added here
}