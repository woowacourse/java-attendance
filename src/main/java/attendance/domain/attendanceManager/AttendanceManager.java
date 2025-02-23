package attendance.domain.attendanceManager;

import java.time.LocalDate;
import java.time.LocalTime;

import attendance.domain.attendanceBook.AttendanceBook;

public abstract class AttendanceManager {
    protected final AttendanceBook attendanceBook;

    public AttendanceManager(AttendanceBook attendanceBook) {
        this.attendanceBook = attendanceBook;
    }

    public abstract void manage(String nickname, LocalDate date, LocalTime time);

    protected enum Error {
        ATTENDANCE_NOT_AVAILABLE("출석 시스템은 2024년 12월 동안만 유효합니다"),
        DUPLICATE_DATE("이미 출석되었습니다. 수정 기능을 이용해주세요."),
        CANT_FIND_INFO("출석 정보를 찾을 수 없습니다."),

        NOT_REGISTERED_NICKNAME("등록되지 않은 닉네임입니다."),
        ;
        private final String message;

        Error(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}
