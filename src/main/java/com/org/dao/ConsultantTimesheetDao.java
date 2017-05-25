package com.org.dao;

import java.util.List;

import com.org.model.TimeSheetBean;

public interface ConsultantTimesheetDao {

    public List<TimeSheetBean> getUnsubmittedList(int vendorId);
}
