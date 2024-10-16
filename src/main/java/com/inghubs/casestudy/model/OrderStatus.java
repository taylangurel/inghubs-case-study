package com.inghubs.casestudy.model;

public enum OrderStatus {

    PENDING,
    MATCHED,
    CANCELED;

    public boolean isCancelable() {
        return this == PENDING;
    }

    public boolean isCanceled() {
        return this == CANCELED;
    }

}
