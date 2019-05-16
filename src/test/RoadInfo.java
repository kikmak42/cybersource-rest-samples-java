/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.util.List;
import java.util.Map;

/**
 *
 * @author kaumahat
 */
public class RoadInfo {
    private String name;
    private String requestClassName;
    private String responseClassName;
    private List<String> requiredFields;
    private Map<String,String> expectedValues;
    private String httpStatus;

    public RoadInfo() {
    }

    public String getRequestClassName() {
        return requestClassName;
    }

    public String getResponseClassName() {
        return responseClassName;
    }

    public Map<String, String> getExpectedValues() {
        return expectedValues;
    }

    public List<String> getRequiredFields() {
        return requiredFields;
    }

    public String getHttpStatus() {
        return httpStatus;
    }

    public String getName() {
        return name;
    }    

    public void setName(String name) {
        this.name = name;
    }

    public void setRequestClassName(String requestClassName) {
        this.requestClassName = requestClassName;
    }

    public void setResponseClassName(String responseClassName) {
        this.responseClassName = responseClassName;
    }
    
    public void setExpectedValues(Map<String, String> expectedValues) {
        this.expectedValues = expectedValues;
    }

    public void setRequiredFields(List<String> requiredFields) {
        this.requiredFields = requiredFields;
    }

    public void setHttpStatus(String httpStatus) {
        this.httpStatus = httpStatus;
    }
    
    
}
