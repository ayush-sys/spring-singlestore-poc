package com.singlestore.singlestore_application.utils;

import com.singlestore.singlestore_application.config.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/** The custom utility functions class. */

@Component
public class Utils {

    @Autowired
    private AppConfig appConfig;

    public String getCurrentTimestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat(appConfig.getTimePattern());
        sdf.setTimeZone(TimeZone.getTimeZone(appConfig.getTimezone()));
        return sdf.format(new Date());
    }
}
