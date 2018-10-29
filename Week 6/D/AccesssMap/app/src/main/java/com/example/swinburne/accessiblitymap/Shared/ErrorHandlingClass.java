package com.example.swinburne.accessiblitymap.Shared;

public class ErrorHandlingClass {
    public static String handlingRequestError(int statusCode) {
        switch (statusCode) {
            case 404:
                return  "Page not found";
            case 500:
                return "Server broken";
            default:
                return "Unknown error";
        }
    }
}
