package com.goCash.enums;

public enum TransactionStatus {

    PENDING(1), SUCCESSFUL(0), FAILED(2);

    private int id;

    TransactionStatus(int id) {
        this.id = id;
    }
}
