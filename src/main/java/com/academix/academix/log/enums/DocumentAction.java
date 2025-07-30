package com.academix.academix.log.enums;

public enum DocumentAction {
    CREATED,
    UPDATED,
    APPROVED,
    REJECTED,
    RELEASED,
    CANCELLED,
    IN_PROGRESS,
    READY_FOR_PICKUP,
    AUTO_EXPIRED,   // e.g., system-driven for clean up
    DOWNLOADED      // for extracting request to PDF
}
