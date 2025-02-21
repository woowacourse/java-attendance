package attendance.controller;

import attendance.domain.AttendanceStatus;
import attendance.domain.dto.AttendanceDto;
import attendance.domain.dto.AttendanceHistoryDto;
import attendance.domain.dto.AttendanceModifyDto;
import attendance.domain.dto.DateValidateDto;
import attendance.domain.dto.NicknameValidateDto;
import attendance.domain.dto.TimeValidateDto;
import attendance.service.AttendanceManagerService;
import attendance.service.CrewDismissService;
import java.time.LocalDate;
import java.time.LocalTime;

public class AttendanceController {

    private final AttendanceManagerService attendanceManagerService;
    private final CrewDismissService crewDismissService;

    public AttendanceController(AttendanceManagerService attendanceManagerService,
                                CrewDismissService crewDismissService) {
        this.attendanceManagerService = attendanceManagerService;
        this.crewDismissService = crewDismissService;
    }

    public String attendance(AttendanceDto attendanceDto) {
        attendanceManagerService.addAttendance(attendanceDto.nickname(), attendanceDto.attendanceDate());
        return attendanceManagerService.attendanceResult(attendanceDto.nickname(),
                attendanceDto.attendanceDate().toLocalDate());
    }

    public String attendanceModify(AttendanceModifyDto attendanceDto) {
        LocalDate modifyDate = attendanceDto.attendanceDate().toLocalDate();
        LocalTime afterModifyTime = attendanceDto.attendanceDate().toLocalTime();
        String nickname = attendanceDto.nickname();
        String beforeAttendance = attendanceManagerService.attendanceResult(nickname, modifyDate);
        attendanceManagerService.modify(nickname, modifyDate, afterModifyTime);
        AttendanceStatus attendanceStatus = attendanceManagerService.getAttendanceStatus(modifyDate, nickname);
        return attendanceManagerService.formattingAttendanceModify(afterModifyTime, beforeAttendance, attendanceStatus);
    }

    public String attendanceHistory(AttendanceHistoryDto attendanceHistoryDto) {
        return attendanceManagerService.crewAttendanceHistory(attendanceHistoryDto.nickname());
    }

    public String crewDismiss() {
        return crewDismissService.formattingCrewDismiss();
    }

    public void validateNickname(NicknameValidateDto nicknameValidateDto) {
        attendanceManagerService.validateNickname(nicknameValidateDto.nickname());
    }

    public void validateTime(TimeValidateDto timeValidateDto) {
        attendanceManagerService.validateTime(timeValidateDto.time());
    }

    public void validateDate(DateValidateDto dateValidateDto) {
        attendanceManagerService.validateDate(dateValidateDto.date());
    }
}
