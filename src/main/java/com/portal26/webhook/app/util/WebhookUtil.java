package com.portal26.webhook.app.util;

import com.portal26.webhook.app.constants.WebhookContstants;
import org.springframework.context.i18n.LocaleContextHolder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class WebhookUtil {

    public static long convertStrTimeToEpoc(String dateStr) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat(WebhookContstants.PORTAL26_DATE_TIME_FORMAT);
        Date date = df.parse(dateStr);
        long epoch = date.getTime();
        return epoch;
    }

    public static long convertQueryStrTimeToEpoch(String time) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat(WebhookContstants.PORTAL26_DATE_TIME_FORMAT);
        Date date = df.parse(time);
        long epoch = date.getTime();
        return epoch;
    }

}
