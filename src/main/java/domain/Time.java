package domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Time {

    public static final String INVALID_TIME_FORMAT = "적절한 시간 형식으로 입력해주세요.";
    private final int hour;
    private final int minute;

    public Time(String rawTime) {
        validate(rawTime);

        String[] spilttedTime = rawTime.split(":");
        this.hour = Integer.parseInt(spilttedTime[0]);
        this.minute = Integer.parseInt(spilttedTime[1]);
    }

    private void validate(String rawTime) {
        validateTimeFormat(rawTime);
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
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
        String[] spilttedTime = rawTime.split(":");
        int spittedHour = Integer.parseInt(spilttedTime[0]);
        if (spittedHour < 0 || spittedHour > 23) {
            throw new IllegalArgumentException(INVALID_TIME_FORMAT);
        }
        int spittedMinute = Integer.parseInt(spilttedTime[1]);
        if (spittedMinute < 0 || spittedMinute > 59) {
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
