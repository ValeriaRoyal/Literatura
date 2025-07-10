package com.exemplo.literatura.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LivroDto {
    
    @JsonProperty("id")
    private Long id;
    
    @JsonProperty("title")
    private String title;
    
    @JsonProperty("authors")
    private List<AutorDto> authors;
    
    @JsonProperty("subjects")
    private List<String> subjects;
    
    @JsonProperty("bookshelves")
    private List<String> bookshelves;
    
    @JsonProperty("languages")
    private List<String> languages;
    
    @JsonProperty("copyright")
    private Boolean copyright;
    
    @JsonProperty("media_type")
    private String mediaType;
    
    @JsonProperty("formats")
    private Map<String, String> formats;
    
    @JsonProperty("download_count")
    private Integer downloadCount;
    
    // Construtores
    public LivroDto() {}
    
    public LivroDto(Long id, String title, List<AutorDto> authors, List<String> subjects, 
                    List<String> bookshelves, List<String> languages, Boolean copyright, 
                    String mediaType, Map<String, String> formats, Integer downloadCount) {
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.subjects = subjects;
        this.bookshelves = bookshelves;
        this.languages = languages;
        this.copyright = copyright;
        this.mediaType = mediaType;
        this.formats = formats;
        this.downloadCount = downloadCount;
    }
    
    // Getters e Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public List<AutorDto> getAuthors() {
        return authors;
    }
    
    public void setAuthors(List<AutorDto> authors) {
        this.authors = authors;
    }
    
    public List<String> getSubjects() {
        return subjects;
    }
    
    public void setSubjects(List<String> subjects) {
        this.subjects = subjects;
    }
    
    public List<String> getBookshelves() {
        return bookshelves;
    }
    
    public void setBookshelves(List<String> bookshelves) {
        this.bookshelves = bookshelves;
    }
    
    public List<String> getLanguages() {
        return languages;
    }
    
    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }
    
    public Boolean getCopyright() {
        return copyright;
    }
    
    public void setCopyright(Boolean copyright) {
        this.copyright = copyright;
    }
    
    public String getMediaType() {
        return mediaType;
    }
    
    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }
    
    public Map<String, String> getFormats() {
        return formats;
    }
    
    public void setFormats(Map<String, String> formats) {
        this.formats = formats;
    }
    
    public Integer getDownloadCount() {
        return downloadCount;
    }
    
    public void setDownloadCount(Integer downloadCount) {
        this.downloadCount = downloadCount;
    }
    
    @Override
    public String toString() {
        return "LivroDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", authors=" + authors +
                ", subjects=" + subjects +
                ", bookshelves=" + bookshelves +
                ", languages=" + languages +
                ", copyright=" + copyright +
                ", mediaType='" + mediaType + '\'' +
                ", formats=" + formats +
                ", downloadCount=" + downloadCount +
                '}';
    }
}
