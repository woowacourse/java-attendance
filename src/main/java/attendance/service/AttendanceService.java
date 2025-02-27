package attendance.service;

import attendance.domain.Attendance;
import attendance.domain.AttendancePenalty;
import attendance.domain.AttendanceReader;
import attendance.domain.AttendanceStatus;
import attendance.domain.Attendances;
import attendance.domain.PenaltyCount;
import attendance.dto.AttendanceCheckDto;
import attendance.dto.AttendanceEditDto;
import attendance.dto.AttendanceFileDto;
import attendance.dto.AttendanceInfoDto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AttendanceService {

    private final AttendanceReader reader;
    private Attendances attendances;

    public AttendanceService(AttendanceReader reader) {
        this.reader = reader;
        readAttendance();
    }

    private void readAttendance() {
        List<AttendanceFileDto> read = reader.read();
        Attendances newAttendances = new Attendances();
        for (AttendanceFileDto attendanceFileDto : read) {
            newAttendances.addAttendance(attendanceFileDto.name(),
                new Attendance(attendanceFileDto.attendanceDate(), attendanceFileDto.attendanceTime()));
        }
        this.attendances = newAttendances;
    }

    public void validateNameExists(String name) {
        attendances.validateNameExists(name);
    }

    public boolean hasAttendance(String name, LocalDate attendanceDate) {
        return attendances.hasAttendance(name, attendanceDate);
    }

    public AttendanceInfoDto remarkAttendance(String name, LocalDate attendanceDate, LocalTime attendanceTime) {
        attendances.addAttendance(name, new Attendance(attendanceDate, attendanceTime));
        AttendanceStatus attendanceStatus = AttendanceStatus.findAttendanceStatus(attendanceDate, attendanceTime);
        return AttendanceInfoDto.of(attendanceDate, attendanceTime, attendanceStatus);
    }

    public AttendanceEditDto editAttendance(String name, LocalDate editAttendanceDate, LocalTime editAttendanceTime) {
        Optional<LocalTime> beforeEditTime = attendances.editAttendance(name, editAttendanceDate, editAttendanceTime);
        AttendanceStatus beforeEditStatus = AttendanceStatus.findAttendanceStatus(editAttendanceDate, beforeEditTime.orElse(null));
        AttendanceStatus editStatus = AttendanceStatus.findAttendanceStatus(editAttendanceDate, editAttendanceTime);
        return AttendanceEditDto.of(
            editAttendanceDate, beforeEditTime.orElse(null), beforeEditStatus, editAttendanceTime, editStatus);
    }

    public AttendanceCheckDto checkAttendance(String name, LocalDate today) {
        List<Attendance> attendanceUntilYesterday = attendances.findAttendanceUntilYesterday(name, today);
        Map<AttendanceStatus, Integer> attendanceStatusCount = attendances.countAttendanceStatus(name, today);
        PenaltyCount penaltyCount = new PenaltyCount(attendanceStatusCount);
        AttendancePenalty attendancePenalty = penaltyCount.findAttendancePenalty();

        List<AttendanceInfoDto> attendanceInfoDtos = convertToAttendanceInfo(attendanceUntilYesterday);
        return AttendanceCheckDto.of(name, attendanceInfoDtos, attendanceStatusCount, attendancePenalty);
    }

    private static List<AttendanceInfoDto> convertToAttendanceInfo(List<Attendance> attendanceUntilYesterday) {
        return attendanceUntilYesterday.stream()
            .map(attendance -> AttendanceInfoDto.of(
                attendance.getAttendanceDate(), attendance.getAttendanceTime(),
                AttendanceStatus.findAttendanceStatus(attendance.getAttendanceDate(), attendance.getAttendanceTime())))
            .toList();
    }
}
