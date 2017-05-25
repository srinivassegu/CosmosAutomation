package com.org.service.email;

import java.util.List;

import com.org.model.EmployeeBean;
import com.org.model.TimeSheetBean;

public interface EmailSender {

    public void sendEmail(EmployeeBean employeeBean, List<TimeSheetBean> timeSheetBeans);
}
