package com.banking.util;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.springframework.util.StringUtils;

public class TimeUtil {
    public static final DateTimeFormatter DATE_TME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public static final LocalDate MIN_DATE = LocalDate.of(0, 1, 1);
    public static final LocalDate MAX_DATE = LocalDate.of(3000, 1, 1);

    private TimeUtil(){
    }

    public static String toString(LocalDateTime operationTime) {
        return operationTime == null ? "" : operationTime.format(DATE_TME_FORMATTER);
    }

    public static LocalDate parseLocalDate(String currentDate, LocalDate date) {
        return StringUtils.isEmpty(currentDate) ? date : LocalDate.parse(currentDate);
    }

    public static LocalTime parseLocalTime(String currentTime, LocalTime time){
        return StringUtils.isEmpty(currentTime) ? time : LocalTime.parse(currentTime);
    }
}
