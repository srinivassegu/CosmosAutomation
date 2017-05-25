package com.org.dao.impl;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.Instant;
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

import com.org.dao.VendorScheduleDao;
import com.org.model.VendorScheduleBean;

@Repository
public class VendorScheduleDaoImpl implements VendorScheduleDao {

    private static Logger logger = LoggerFactory.getLogger(VendorScheduleDaoImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return this.jdbcTemplate;
    }

    @Override
    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public List<VendorScheduleBean> getVendorSchedules() {
        try {
            return this.getJdbcTemplate().query("SELECT vendor_id, next_notification_time FROM automation.vendor_schedule_tbl",
                    new Object[] {}, (ResultSet resultSet, int rowNum) -> {
                        VendorScheduleBean scheduleBean = new VendorScheduleBean();
                        scheduleBean.setVendorId(resultSet.getInt("vendor_id"));
                        scheduleBean.setNextScheduledTime(resultSet.getTimestamp("next_notification_time").toInstant());
                        return scheduleBean;
                    });
        } catch (IncorrectResultSizeDataAccessException e) {
            String error = String.format("No resultset while retreiving vendor schedules");
            logger.error(error);
            return Collections.emptyList();
        }
    }

    @Override
    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void updateVendorSchedule(int vendorId, Instant instant) {
        this.getJdbcTemplate().update("update automation.vendor_schedule_tbl set next_notification_time=? where vendor_id=?",
                new Object[] { Timestamp.from(instant), vendorId });
    }
}
