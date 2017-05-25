package com.org.model;

import java.time.LocalDate;

public class TimeSheetBean {

    private LocalDate startDate;
    private LocalDate endDate;
    private String submissionLink;
    private String consultantFirstName;
    private String consultantEmailId;
    private long consultantId;
    private boolean isSendingFailed;

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getSubmissionLink() {
        return submissionLink;
    }

    public void setSubmissionLink(String submissionLink) {
        this.submissionLink = submissionLink;
    }

    public String getConsultantFirstName() {
        return consultantFirstName;
    }

    public void setConsultantFirstName(String consultantFirstName) {
        this.consultantFirstName = consultantFirstName;
    }

    public String getConsultantEmailId() {
        return consultantEmailId;
    }

    public void setConsultantEmailId(String consultantEmailId) {
        this.consultantEmailId = consultantEmailId;
    }

    public long getConsultantId() {
        return consultantId;
    }

    public void setConsultantId(long consultantId) {
        this.consultantId = consultantId;
    }

    public boolean isSendingFailed() {
        return isSendingFailed;
    }

    public void setSendingFailed(boolean isSendingFailed) {
        this.isSendingFailed = isSendingFailed;
    }

}
