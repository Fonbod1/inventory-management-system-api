package com.project.pk48.inventoryms_springboot_api.models;

public class BrandStats {
    private Long currentMonthCount;
    private Long previousMonthCount;
    private double percentageDifference;

    public BrandStats(Long currentMonthCount, Long previousMonthCount, double percentageDifference) {
        this.currentMonthCount = currentMonthCount;
        this.previousMonthCount = previousMonthCount;
        this.percentageDifference = percentageDifference;
    }

    public Long getCurrentMonthCount() {
        return currentMonthCount;
    }
}