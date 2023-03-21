package com.example.example_xml_xslt_csv.location;


public class DbException extends Exception {

    public DbException() {
        super();
    }

    public DbException(String message) {
        super(message);
    }

    public DbException(String message, Throwable cause) {
        super(message, cause);
    } // причина усключения

    public DbException(Throwable cause) {
        super(cause);
    }

    protected DbException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
