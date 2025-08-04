package com.academix.academix.document.enums;

import java.util.Set;

public enum ActionPermission {
    // All the statuses inside Set.of(...), will go into Set<DocumentStatus> allowedPreviousStatus individually per enums
    APPROVE(Set.of(DocumentStatus.REQUESTED, DocumentStatus.IN_PROGRESS)),
    REJECT(Set.of(DocumentStatus.REQUESTED, DocumentStatus.IN_PROGRESS)),
    SET_READY_FOR_PICKUP(Set.of(DocumentStatus.APPROVED)),
    RELEASE(Set.of(DocumentStatus.READY_FOR_PICKUP)),
    CANCEL(Set.of(DocumentStatus.REQUESTED, DocumentStatus.IN_PROGRESS)),
    SET_IN_PROGRESS(Set.of(DocumentStatus.REQUESTED));

    private final Set<DocumentStatus> allowedPreviousStatus;

    ActionPermission(Set<DocumentStatus> allowedPreviousStatuses) {
        this.allowedPreviousStatus = allowedPreviousStatuses;
    }

    public boolean isAllowed(DocumentStatus currentStatus) {
        return allowedPreviousStatus.contains(currentStatus);
    }
}
