package com.org.model;

import java.time.Instant;

public class VendorScheduleBean {

    private int vendorId;
    private Instant nextScheduledTime;

    public int getVendorId() {
        return vendorId;
    }

    public void setVendorId(int vendorId) {
        this.vendorId = vendorId;
    }

    public Instant getNextScheduledTime() {
        return nextScheduledTime;
    }

    public void setNextScheduledTime(Instant nextScheduledTime) {
        this.nextScheduledTime = nextScheduledTime;
    }
}
