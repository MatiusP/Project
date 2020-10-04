package by.epamtc.protsko.rentcar.controller.command.util;

import javax.servlet.http.HttpServletRequest;

public class RequestURL {

    private RequestURL() {
    }

    public static String getRequestURL(HttpServletRequest request) {
        String currentRequestURI = request.getRequestURL().toString();
        String currentRequestQuery = request.getQueryString();

        return currentRequestURI + "?" + currentRequestQuery;
    }

}
