package com.org.service.handler;

import java.util.List;

import com.org.model.VendorScheduleBean;

public interface NotificationFilter {

    public List<VendorScheduleBean> getValidJobsToSendNotifications();
}
