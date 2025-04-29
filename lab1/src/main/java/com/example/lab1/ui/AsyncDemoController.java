package com.example.lab1.ui;

import com.example.lab1.services.AsyncCalculationService;
import lombok.Getter;
import lombok.Setter;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Named
@SessionScoped
public class AsyncDemoController implements Serializable {
    
    @EJB
    private AsyncCalculationService calculationService;
    
    @Getter @Setter
    private int iterations = 50; // Default value
    
    @Getter @Setter
    private boolean calculationInProgress = false;
    
    @Getter
    private List<String> progressMessages = new ArrayList<>();
    
    @Getter
    private Integer result;
    
    private Future<Integer> futureResult;
    
    /**
     * Starts the asynchronous calculation
     */
    public void startCalculation() {
        progressMessages.clear();
        result = null;
        calculationInProgress = true;
        
        addMessage("Starting calculation with " + iterations + " iterations...");
        futureResult = calculationService.performLongCalculation(iterations);
        
        addMessage("Calculation started asynchronously");
    }
    
    /**
     * Checks the status of the calculation
     */
    public void checkProgress() {
        if (futureResult == null) {
            addMessage("No calculation in progress");
            return;
        }
        
        if (futureResult.isDone()) {
            try {
                result = futureResult.get();
                addMessage("Calculation completed! Result: " + result);
                calculationInProgress = false;
                
                FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Calculation completed with result: " + result));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                addMessage("Calculation was interrupted: " + e.getMessage());
                calculationInProgress = false;
            } catch (ExecutionException e) {
                addMessage("Error during calculation: " + e.getCause().getMessage());
                calculationInProgress = false;
            }
        } else {
            addMessage("Calculation still in progress...");
        }
    }
    
    /**
     * Cancels the calculation if it's still running
     */
    public void cancelCalculation() {
        if (futureResult != null && !futureResult.isDone()) {
            boolean cancelled = futureResult.cancel(true);
            if (cancelled) {
                addMessage("Calculation was cancelled");
            } else {
                addMessage("Failed to cancel the calculation");
            }
        } else {
            addMessage("No calculation to cancel");
        }
        calculationInProgress = false;
    }
    
    private void addMessage(String message) {
        progressMessages.add(message);
    }
    
    public void reset() {
        progressMessages.clear();
        result = null;
        calculationInProgress = false;
        futureResult = null;
    }
}