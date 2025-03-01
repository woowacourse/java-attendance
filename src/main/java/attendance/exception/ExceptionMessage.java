package attendance.exception;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import attendance.util.DateTimeUtil;

public enum ExceptionMessage {
    // IllegalArgumentException
    INVALID_COMMAND("{%s}는 잘못된 메뉴 번호입니다."),
    INVALID_DATE("{%s}는 잘못된 날짜입니다."),
    INVALID_TIME("{%s}는 잘못된 시간입니다."),
    NOT_FOUND_ATTENDANCE_DATA("%s에는 출석 기록이 존재하지 않습니다."),
    NOT_FOUND_CREW("{%s}는 존재하지 않는 크루입니다."),
    NOT_CAMPUS_OPEN_TIME("캠퍼스 운영시간(%s ~ %s) 내에만 출석할 수 있습니다."),
    ALREADY_ATTENDANCE("이미 출석한 경우 다시 출석할 수 없습니다. 출석 수정 기능을 이용해 주세요."),
    ATTENDANCE_ON_DAY_OFF("주말 및 공휴일에는 등교가 불가능합니다."),

    // IllegalStateException
    INVALID_LECTURE_STATE("{%s}에 대한 강의 시간 객체를 찾을 수 없습니다."),
    FILE_READ_ERROR("파일을 읽는 과정에서 문제가 발생했습니다."),
    ;

    private static final String PREFIX = "[ERROR] ";

    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage(Object... args) {
        List<Object> convertedArgs = new ArrayList<>();
        for (Object arg : args) {
            if (arg instanceof LocalTime localTime) {
                convertedArgs.add(localTime.format(DateTimeUtil.TIME_FORMATTER));
                continue;
            }
            if (arg instanceof LocalDate localDate) {
                convertedArgs.add(localDate.format(DateTimeUtil.DATE_FORMATTER));
                continue;
            }
            convertedArgs.add(arg);
        }
        return String.format(PREFIX + message, convertedArgs.toArray());
    }
}
