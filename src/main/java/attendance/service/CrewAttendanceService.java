package attendance.service;

import attendance.domain.*;
import attendance.dto.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CrewAttendanceService {

    private CrewAttendances crewAttendances;

    public CrewAttendanceService(CrewAttendances crewAttendances) {
        this.crewAttendances = crewAttendances;
    }

    public Crew findRegisteredCrew(final String nickname) {
        Crew crew = new Crew(nickname);
        if (!crewAttendances.isRegisteredCrew(crew)) {
            throw new IllegalArgumentException("등록되지 않은 닉네임입니다.");
        }
        return crew;
    }

    public ConfirmAttendanceDto saveCrewTodayAttendance(final Crew crew, final LocalTime time) {
        LocalDate today = LocalDate.now();
        AttendanceDate attendanceDate = new AttendanceDate(today);
        AttendanceTime attendanceTime = new AttendanceTime(time);
        Attendance attendance = new Attendance(attendanceDate, attendanceTime);
        crewAttendances.addAttendance(crew, attendance);
        return new ConfirmAttendanceDto(attendance);
    }

    public ChangeAttendanceDto changeAttendanceTime(Crew crew, LocalDate changeDate, LocalTime changeTime) {
        Attendance originAttendance = crewAttendances.findAttendanceByCrewAndLocalDate(crew, changeDate);
        Attendance newAttendance = originAttendance.changeAttendanceTime(changeTime);
        crewAttendances.removeAttendance(crew, originAttendance);
        crewAttendances.addAttendance(crew, newAttendance);
        return new ChangeAttendanceDto(originAttendance, newAttendance);
    }

    public CheckCrewAttendanceRecordsDto checkCrewAttendanceRecords(Crew crew) {
        List<LocalDateTime> attendanceDateTimes = crewAttendances.findCrewAllAttendanceLocalDateTime(crew);
        List<AttendanceStatus> attendanceStatuses = AttendanceStatus.findAllStatusesByLocalDateTimes(attendanceDateTimes);
        return new CheckCrewAttendanceRecordsDto(attendanceDateTimes, attendanceStatuses);
    }

    public Map<String, AttendanceHistoryDto> calculateAllCrewAttendanceHistories() {
        Map<String, AttendanceHistoryDto> attendanceHistories = new HashMap<>();
        for (Map.Entry<Crew, Attendances> entry : crewAttendances.getCrewAttendanceEntrySet()) {
            Attendances attendances = entry.getValue();
            Map<String, Integer> attendanceStatusCounts = attendances.calculateStatusCount();
            ExpulsionStatus expulsionStatus = attendances.calculateExpulsionStatus();
            AttendanceHistoryDto attendanceHistoryDto = new AttendanceHistoryDto(
                    attendanceStatusCounts.get(AttendanceStatus.ABSENT.getText()),
                    attendanceStatusCounts.get(AttendanceStatus.LATE.getText()), expulsionStatus.getText());
            attendanceHistories.put(entry.getKey().getNickname(), attendanceHistoryDto);
        }
        return attendanceHistories;
    }
}
