package com.singlestore.singlestore_application.utils;

import com.singlestore.singlestore_application.config.AppConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
/** The Utils class */
public class Utils {

    @Autowired
    private AppConfig appConfig;

    /**
     * The getCurrentTime function
     *
     * @param timeFormat - the current time should be formatted as per param
     * @return the current time of application
     * */
    public String getCurrentTime(String timeFormat){
        ZonedDateTime currentDateTime = ZonedDateTime.now(java.time.ZoneId.of(appConfig.getTimeZone()));
        return currentDateTime.format(DateTimeFormatter.ofPattern(timeFormat));
    }

}