package com.exemplo.literatura.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GutendxResponse {
    
    @JsonProperty("count")
    private Integer count;
    
    @JsonProperty("next")
    private String next;
    
    @JsonProperty("previous")
    private String previous;
    
    @JsonProperty("results")
    private List<LivroDto> results;
    
    // Construtores
    public GutendxResponse() {}
    
    public GutendxResponse(Integer count, String next, String previous, List<LivroDto> results) {
        this.count = count;
        this.next = next;
        this.previous = previous;
        this.results = results;
    }
    
    // Getters e Setters
    public Integer getCount() {
        return count;
    }
    
    public void setCount(Integer count) {
        this.count = count;
    }
    
    public String getNext() {
        return next;
    }
    
    public void setNext(String next) {
        this.next = next;
    }
    
    public String getPrevious() {
        return previous;
    }
    
    public void setPrevious(String previous) {
        this.previous = previous;
    }
    
    public List<LivroDto> getResults() {
        return results;
    }
    
    public void setResults(List<LivroDto> results) {
        this.results = results;
    }
    
    @Override
    public String toString() {
        return "GutendxResponse{" +
                "count=" + count +
                ", next='" + next + '\'' +
                ", previous='" + previous + '\'' +
                ", results=" + results +
                '}';
    }
}
