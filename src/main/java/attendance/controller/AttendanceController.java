package attendance.controller;

import attendance.service.AttendanceManagerService;
import attendance.service.CrewDismissService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class AttendanceController {

    private final AttendanceManagerService attendanceManagerService;
    private final CrewDismissService crewDismissService;

    public AttendanceController(AttendanceManagerService attendanceManagerService,
                                CrewDismissService crewDismissService) {
        this.attendanceManagerService = attendanceManagerService;
        this.crewDismissService = crewDismissService;
    }

    public String attendance(String nickname, LocalDateTime attendanceDateTime) {
        attendanceManagerService.addAttendance(nickname, attendanceDateTime);
        return attendanceManagerService.attendanceResult(nickname, attendanceDateTime.toLocalDate());
    }

    public String attendanceModify(String nickname, LocalDate modifyDate, LocalTime afterModifyTime) {
        String beforeAttendance = attendanceManagerService.attendanceResult(nickname, modifyDate);
        attendanceManagerService.modify(nickname, modifyDate, afterModifyTime);
        String attendanceStatus = attendanceManagerService.getAttendanceStatus(modifyDate, nickname);
        return attendanceManagerService.formattingAttendanceModify(afterModifyTime, beforeAttendance, attendanceStatus);
    }

    public String attendanceHistory(String nickname) {
        return attendanceManagerService.crewAttendanceHistory(nickname);
    }

    public String crewDismiss() {
        List<String> attendancesNicknames = attendanceManagerService.attendancesNicknames();
        return crewDismissService.formattingCrewDismiss(attendancesNicknames);
    }

    public void validateNickname(String nickname) {
        attendanceManagerService.validateNickname(nickname);
    }

    public void validateTime(LocalTime time) {
        attendanceManagerService.validateTime(time);
    }

    public void validateDate(LocalDate date) {
        attendanceManagerService.validateDate(date);
    }
}
