package com.example.springboot.microservice;

import org.slf4j.LoggerFactory;

import java.util.logging.Logger;

public class LoggerMicroservice {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(LoggerMicroservice.class);

    public void logUserCreation(String username, String email) {
        logger.info("Usuário cadastrado - Nome: {}, Email: {}");
    }

    public void logProductCreation(String productName, double price) {
        logger.info("Produto cadastrado - Nome: {}, Preço: {}");
    }
}
