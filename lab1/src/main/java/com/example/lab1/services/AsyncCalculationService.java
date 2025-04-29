package com.example.lab1.services;

import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import java.util.concurrent.Future;
import java.util.logging.Logger;

@Stateless
public class AsyncCalculationService {
    
    private static final Logger logger = Logger.getLogger(AsyncCalculationService.class.getName());
    
    @Asynchronous
    public Future<Integer> performLongCalculation(int iterations) {
        logger.info("Starting long calculation with " + iterations + " iterations");
        
        int result = 0;
        
        for (int i = 0; i < iterations; i++) {
            try {
                Thread.sleep(100);
                result += i;
                
                if (i % 10 == 0) {
                    logger.info("Progress: " + (i * 100 / iterations) + "%");
                }
                
            } catch (InterruptedException e) {
                logger.severe("Calculation was interrupted: " + e.getMessage());
                Thread.currentThread().interrupt();
                return new AsyncResult<>(-1);
            }
        }
        
        logger.info("Calculation completed. Result: " + result);
        return new AsyncResult<>(result);
    }
}