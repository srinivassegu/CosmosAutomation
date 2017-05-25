package com.org.dao.impl;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.org.dao.ConsultantTimesheetDao;
import com.org.model.TimeSheetBean;

@Repository
public class ConsultantTimesheetDaoImpl implements ConsultantTimesheetDao {

    private static Logger logger = LoggerFactory.getLogger(ConsultantTimesheetDaoImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return this.jdbcTemplate;
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public List<TimeSheetBean> getUnsubmittedList() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ct.first_name, ct.email_id, timesheet.week_ending, ct.id ");
        sb.append("FROM automation.consultant_timesheet_tbl timesheet ");
        sb.append("INNER JOIN automation.consultant_tbl ct on ct.id= timesheet.consultant_id ");
        sb.append("WHERE timesheet.is_submitted=?");
        try {
            return this.getJdbcTemplate().query(sb.toString(), new Object[] { 0 }, (resultSet, rowNum) -> {
                TimeSheetBean bean = new TimeSheetBean();
                bean.setConsultantFirstName(resultSet.getString("first_name"));
                bean.setConsultantEmailId(resultSet.getString("email_id"));
                bean.setEndDate(resultSet.getDate("week_ending").toLocalDate());
                bean.setStartDate(bean.getEndDate().minusWeeks(1));
                bean.setConsultantId(resultSet.getLong("id"));
                return bean;
            });
        } catch (IncorrectResultSizeDataAccessException e) {
            String error = String.format("Error: %s while retreiving unsubmitted list", e.getMessage());
            logger.error("{}", error);
            return Collections.emptyList();
        }
    }
}
