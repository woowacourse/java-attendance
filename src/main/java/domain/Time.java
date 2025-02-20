package domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Time {

    public static final String INVALID_TIME_FORMAT = "적절한 시간 형식으로 입력해주세요.";
    public static final int HOUR_INDEX = 0;
    public static final int MINUTE_INDEX = 1;
    public static final String TIME_DELIMITER = ":";
    public static final String TIME_FORMAT = "HH:mm";
    public static final int END_TIME = 23;
    public static final int END_MINUTE = 59;
    private final int hour;
    private final int minute;

    public Time(String rawTime) {
        validate(rawTime);

        String[] spilttedTime = rawTime.split(TIME_DELIMITER);
        this.hour = Integer.parseInt(spilttedTime[HOUR_INDEX]);
        this.minute = Integer.parseInt(spilttedTime[MINUTE_INDEX]);
    }

    private void validate(String rawTime) {
        validateTimeFormat(rawTime);
        SimpleDateFormat formatter = new SimpleDateFormat(TIME_FORMAT);
        try {
            Date time = formatter.parse(rawTime);
            Date openTime = formatter.parse("08:00");
            Date closeTime = formatter.parse("22:59");

            if (time.before(openTime) || time.after(closeTime)) {
                throw new IllegalArgumentException("캠퍼스 운영 시간은 08:00 ~ 23:00 입니다.");
            }
        } catch (ParseException e) {
            throw new IllegalArgumentException(INVALID_TIME_FORMAT);
        }
    }

    private void validateTimeFormat(String rawTime) {
        String[] spilttedTime = rawTime.split(TIME_DELIMITER);
        int spittedHour = Integer.parseInt(spilttedTime[HOUR_INDEX]);
        if (spittedHour < 0 || spittedHour > END_TIME) {
            throw new IllegalArgumentException(INVALID_TIME_FORMAT);
        }
        int spittedMinute = Integer.parseInt(spilttedTime[MINUTE_INDEX]);
        if (spittedMinute < 0 || spittedMinute > END_MINUTE) {
            throw new IllegalArgumentException(INVALID_TIME_FORMAT);
        }
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }
}
