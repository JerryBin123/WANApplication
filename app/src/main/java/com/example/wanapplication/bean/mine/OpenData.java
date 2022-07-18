package com.example.wanapplication.bean.mine;

public class OpenData {
    private String project;
    private String description;
    private String link;

    public OpenData(String project, String description, String link) {
        this.project = project;
        this.description = description;
        this.link = link;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
