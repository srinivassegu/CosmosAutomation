package com.org.dao.impl;

import java.sql.ResultSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.org.dao.ReminderConfigDao;
import com.org.model.ReminderConfigBean;

@Repository
public class ReminderConfigDaoImpl implements ReminderConfigDao {

    private static Logger logger = LoggerFactory.getLogger(EmployeeDaoImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return this.jdbcTemplate;
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public ReminderConfigBean getAllReminderConfigs(int vendorId) {
        try {
            return this.getJdbcTemplate().queryForObject(
                    "SELECT vendor_id, week_frequency, hour_frequency FROM automation.reminder_config_tbl where vendor_id=?",
                    new Object[] { vendorId }, (ResultSet resultSet, int rowNum) -> {
                        ReminderConfigBean configBean = new ReminderConfigBean();
                        configBean.setVendorId(resultSet.getInt("vendor_id"));
                        configBean.setWeekFrequency(resultSet.getInt("week_frequency"));
                        configBean.setHourFrequency(resultSet.getInt("hour_frequency"));
                        return configBean;
                    });
        } catch (IncorrectResultSizeDataAccessException e) {
            String error = String.format("No resultset while retreiving reminder config");
            logger.error(error);
            return null;
        }
    }
}
