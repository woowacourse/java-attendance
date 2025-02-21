package attendance.domain;

import attendance.domain.dto.AttendanceDto;
import attendance.domain.dto.AttendanceHistoryDto;
import attendance.domain.dto.AttendanceModifyDto;
import attendance.domain.dto.DateValidateDto;
import attendance.domain.dto.NicknameValidateDto;
import attendance.domain.dto.TimeValidateDto;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class RequestParser {

    public AttendanceDto parseAttendanceRequest(String nickname, String time) {
        LocalDate now = LocalDate.now();
        LocalTime attendanceTime = DateTimeFormatterWrapper.parsingAttendanceTime(time);
        return new AttendanceDto(nickname, LocalDateTime.of(now, attendanceTime));
    }

    public NicknameValidateDto parseNicknameValidateRequest(String nickname) {
        return new NicknameValidateDto(nickname);
    }

    public TimeValidateDto parseTimeValidateRequest(String time) {
        return new TimeValidateDto(DateTimeFormatterWrapper.parsingAttendanceTime(time));
    }

    public DateValidateDto parseDateValidateRequest(String date) {
        return new DateValidateDto(DateTimeFormatterWrapper.parsingAttendanceDate(date));
    }

    public AttendanceModifyDto parseAttendanceModifyRequest(String nickname, String time, String date) {
        LocalDate now = DateTimeFormatterWrapper.parsingAttendanceDate(date);
        LocalTime attendanceTime = DateTimeFormatterWrapper.parsingAttendanceTime(time);
        return new AttendanceModifyDto(nickname, LocalDateTime.of(now, attendanceTime));
    }

    public AttendanceHistoryDto parseAttendanceNickname(String nickname) {
        return new AttendanceHistoryDto(nickname);
    }
}
