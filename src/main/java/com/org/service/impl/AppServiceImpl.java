package com.org.service.impl;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.org.dao.ConsultantTimesheetDao;
import com.org.dao.EmployeeDao;
import com.org.model.EmployeeBean;
import com.org.model.TimeSheetBean;
import com.org.service.AppService;
import com.org.service.email.EmailSender;

@Service
public class AppServiceImpl implements AppService {

    @Autowired
    private ConsultantTimesheetDao consultantTimesheetDao;

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private EmailSender emailSender;

    @Override
    public void startService() {

        List<TimeSheetBean> timeSheetBeans = this.getConsultantTimesheetDao().getUnsubmittedList();

        EmployeeBean employeeBean = this.getEmployeeDao().getHumanResourceEmployee(1);

        this.getEmailSender().sendEmail(employeeBean, timeSheetBeans);
        System.out.println("Service");

        LocalDate initialDate = LocalDate.of(2017, 5, 1);

        LocalDate adjustedFirstWeekEnding = initialDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.FRIDAY));
        LocalDate adjustedLastWeekEnding = null;

        String type = "months";
        if (type.equalsIgnoreCase("months")) {
            adjustedLastWeekEnding = adjustedFirstWeekEnding.plusMonths(1);
        }
        if (type.equalsIgnoreCase("years")) {
            adjustedLastWeekEnding = adjustedFirstWeekEnding.plusYears(1);
        }
        if (type.equalsIgnoreCase("weeks")) {
            adjustedLastWeekEnding = adjustedFirstWeekEnding.plusWeeks(1);
        }

        List<String> weeks = new ArrayList<String>();

        long noOfWeeks = ChronoUnit.WEEKS.between(adjustedFirstWeekEnding, adjustedLastWeekEnding);
        System.out.println("weeks: " + noOfWeeks);

        int weekFrequency = 1;
        for (int i = 0; i < noOfWeeks; i = i + 1 + weekFrequency - 1) {
            weeks.add(adjustedFirstWeekEnding.plusWeeks(i).toString());
        }

        List<String> filteredRemindersList = new ArrayList<String>();
        LocalDate currentLocalDate = LocalDate.now();

        for (String week : weeks) {
            long diff = ChronoUnit.WEEKS.between(currentLocalDate, LocalDate.parse(week));
            if (diff == weekFrequency) {
                filteredRemindersList.add(week);
                System.out.println("Valid week: " + week);
            }
            System.out.println("DIFF: " + diff);
        }

        System.out.println("currentlocaldate: " + currentLocalDate);
        LocalDate finalDate = initialDate.plus(Period.ofWeeks(1));
        System.out.println(finalDate);
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

}
