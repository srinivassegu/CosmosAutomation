package com.org.model;

import java.util.Date;

public class ConsultantWeekBean {

    private String firstName;
    private String emailId;
    private Date weekEndDate;

    public Date getWeekEndDate() {
        return weekEndDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public void setWeekEndDate(Date weekEndDate) {
        this.weekEndDate = weekEndDate;
    }

}
