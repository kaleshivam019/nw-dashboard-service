package com.ey.nwdashboard.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "project", schema = "public")
public class ProjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "project_id")
    private Long projectId;

    @Column(name = "project_name")
    private String projectName;

    @Column(name = "lead_name")
    private String projectLeadName;

    @Column(name = "line")
    private String projectLine;

    @Column(name = "created_by")
    private String projectCreatedBy;

    @Column(name = "created_on")
    private Timestamp projectCreatedOn;

    @Column(name = "updated_by")
    private String projectUpdatedBy;

    @Column(name = "updated_on")
    private Timestamp projectUpdatedOn;

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectLeadName() {
        return projectLeadName;
    }

    public void setProjectLeadName(String projectLeadName) {
        this.projectLeadName = projectLeadName;
    }

    public String getProjectLine() {
        return projectLine;
    }

    public void setProjectLine(String projectLine) {
        this.projectLine = projectLine;
    }

    public String getProjectCreatedBy() {
        return projectCreatedBy;
    }

    public void setProjectCreatedBy(String projectCreatedBy) {
        this.projectCreatedBy = projectCreatedBy;
    }

    public Timestamp getProjectCreatedOn() {
        return projectCreatedOn;
    }

    public void setProjectCreatedOn(Timestamp projectCreatedOn) {
        this.projectCreatedOn = projectCreatedOn;
    }

    public String getProjectUpdatedBy() {
        return projectUpdatedBy;
    }

    public void setProjectUpdatedBy(String projectUpdatedBy) {
        this.projectUpdatedBy = projectUpdatedBy;
    }

    public Timestamp getProjectUpdatedOn() {
        return projectUpdatedOn;
    }

    public void setProjectUpdatedOn(Timestamp projectUpdatedOn) {
        this.projectUpdatedOn = projectUpdatedOn;
    }
}
