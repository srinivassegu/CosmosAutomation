package com.org.service.handler.impl;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.org.dao.VendorScheduleDao;
import com.org.model.ReminderConfigBean;
import com.org.service.handler.VendorScheduleUpdateHandler;

@Component
public class VendorScheduleUpdateHandlerImpl implements VendorScheduleUpdateHandler {

    @Autowired
    private VendorScheduleDao vendorScheduleDao;

    @Override
    public void updateSchedules(int vendorId, ReminderConfigBean reminderConfigBean) {

        LocalDateTime localDateTime = Instant.now().atZone(ZoneId.systemDefault()).toLocalDateTime();
        localDateTime = localDateTime.plusWeeks(reminderConfigBean.getWeekFrequency()).plusHours(
                reminderConfigBean.getHourFrequency());

        this.getVendorScheduleDao().updateVendorSchedule(vendorId, localDateTime.toInstant(ZoneOffset.UTC));
    }

    private VendorScheduleDao getVendorScheduleDao() {
        return vendorScheduleDao;
    }

    public void setVendorScheduleDao(VendorScheduleDao vendorScheduleDao) {
        this.vendorScheduleDao = vendorScheduleDao;
    }
}
