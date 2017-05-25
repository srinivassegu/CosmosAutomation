package com.org.dao;

import java.time.Instant;
import java.util.List;

import com.org.model.VendorScheduleBean;

public interface VendorScheduleDao {

    public List<VendorScheduleBean> getVendorSchedules();

    public void updateVendorSchedule(int vendorId, Instant instant);
}
