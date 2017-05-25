package com.org.service.handler.impl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.org.dao.VendorScheduleDao;
import com.org.model.VendorScheduleBean;
import com.org.service.handler.NotificationFilter;

@Component
public class NotificationFilterImpl implements NotificationFilter {

    private static Logger logger = LoggerFactory.getLogger(NotificationFilterImpl.class);

    @Autowired
    private VendorScheduleDao vendorScheduleDao;

    @Override
    public List<VendorScheduleBean> getValidJobsToSendNotifications() {

        List<VendorScheduleBean> existingVendorSchedules = this.getVendorScheduleDao().getVendorSchedules();

        if (CollectionUtils.isEmpty(existingVendorSchedules)) {
            logger.info("No vendor schedule exists, returning!");
        }

        List<VendorScheduleBean> eligibleVendorSchedules = new ArrayList<VendorScheduleBean>();

        eligibleVendorSchedules.addAll(existingVendorSchedules.stream()
                .filter(schedule -> schedule.getNextScheduledTime().isAfter(Instant.now())).collect(Collectors.toList()));

        return eligibleVendorSchedules;
    }

    private VendorScheduleDao getVendorScheduleDao() {
        return vendorScheduleDao;
    }

    public void setVendorScheduleDao(VendorScheduleDao vendorScheduleDao) {
        this.vendorScheduleDao = vendorScheduleDao;
    }
}
