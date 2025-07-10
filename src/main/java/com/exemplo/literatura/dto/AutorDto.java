package com.exemplo.literatura.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AutorDto {
    
    @JsonProperty("name")
    private String name;
    
    @JsonProperty("birth_year")
    private Integer birthYear;
    
    @JsonProperty("death_year")
    private Integer deathYear;
    
    // Construtores
    public AutorDto() {}
    
    public AutorDto(String name, Integer birthYear, Integer deathYear) {
        this.name = name;
        this.birthYear = birthYear;
        this.deathYear = deathYear;
    }
    
    // Getters e Setters
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Integer getBirthYear() {
        return birthYear;
    }
    
    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }
    
    public Integer getDeathYear() {
        return deathYear;
    }
    
    public void setDeathYear(Integer deathYear) {
        this.deathYear = deathYear;
    }
    
    @Override
    public String toString() {
        return "AutorDto{" +
                "name='" + name + '\'' +
                ", birthYear=" + birthYear +
                ", deathYear=" + deathYear +
                '}';
    }
}
