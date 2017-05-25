package com.org.service.handler;

import com.org.model.ReminderConfigBean;

public interface VendorScheduleUpdateHandler {

    public void updateSchedules(int vendorId, ReminderConfigBean reminderConfigBean);
}
