package com.org.service.impl;

import java.time.Instant;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.org.dao.ConsultantTimesheetDao;
import com.org.dao.EmployeeDao;
import com.org.dao.ReminderConfigDao;
import com.org.model.EmployeeBean;
import com.org.model.ReminderConfigBean;
import com.org.model.TimeSheetBean;
import com.org.model.VendorScheduleBean;
import com.org.service.AppService;
import com.org.service.email.EmailSender;
import com.org.service.handler.NotificationFilter;
import com.org.service.handler.VendorScheduleUpdateHandler;

@Service
public class AppServiceImpl implements AppService {

    private static Logger logger = LoggerFactory.getLogger(AppServiceImpl.class);

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private NotificationFilter notificationFilter;

    @Autowired
    private ConsultantTimesheetDao consultantTimesheetDao;

    @Autowired
    private EmailSender emailSender;

    @Autowired
    private VendorScheduleUpdateHandler vendorScheduleUpdateHandler;

    @Autowired
    private ReminderConfigDao reminderConfigDao;

    @Override
    public void startService() {

        EmployeeBean employeeBean = this.getEmployeeDao().getHumanResourceEmployee(1);

        List<VendorScheduleBean> vendors = this.getNotificationFilter().getValidJobsToSendNotifications();

        if (CollectionUtils.isEmpty(vendors)) {
            logger.error("No job/schedule is valid for current time: {}, exiting!", Instant.now());
            return;
        }

        for (VendorScheduleBean vendorScheduleBean : vendors) {
            List<TimeSheetBean> timeSheetBeans = this.getConsultantTimesheetDao().getUnsubmittedList(
                    vendorScheduleBean.getVendorId());

            this.getEmailSender().sendEmail(employeeBean, timeSheetBeans);

            ReminderConfigBean reminderConfigBean = this.getReminderConfigDao().getAllReminderConfigs(
                    vendorScheduleBean.getVendorId());
            this.getVendorScheduleUpdateHandler().updateSchedules(vendorScheduleBean.getVendorId(), reminderConfigBean);
        }
    }

    private ConsultantTimesheetDao getConsultantTimesheetDao() {
        return consultantTimesheetDao;
    }

    public void setConsultantTimesheetDao(ConsultantTimesheetDao consultantTimesheetDao) {
        this.consultantTimesheetDao = consultantTimesheetDao;
    }

    private EmployeeDao getEmployeeDao() {
        return employeeDao;
    }

    public void setEmployeeDao(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    private EmailSender getEmailSender() {
        return emailSender;
    }

    public void setEmailSender(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    public NotificationFilter getNotificationFilter() {
        return notificationFilter;
    }

    public void setNotificationFilter(NotificationFilter notificationFilter) {
        this.notificationFilter = notificationFilter;
    }

    private VendorScheduleUpdateHandler getVendorScheduleUpdateHandler() {
        return vendorScheduleUpdateHandler;
    }

    public void setVendorScheduleUpdateHandler(VendorScheduleUpdateHandler vendorScheduleUpdateHandler) {
        this.vendorScheduleUpdateHandler = vendorScheduleUpdateHandler;
    }

    private ReminderConfigDao getReminderConfigDao() {
        return reminderConfigDao;
    }

    public void setReminderConfigDao(ReminderConfigDao reminderConfigDao) {
        this.reminderConfigDao = reminderConfigDao;
    }

}
