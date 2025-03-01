package attendance.domain;

import attendance.dto.AttendanceCheckDto;
import attendance.dto.AttendanceEditDto;
import attendance.dto.AttendanceFileDto;
import attendance.dto.AttendanceInfoDto;
import attendance.dto.PenaltyCrewDto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AttendanceManager {

    private final AttendanceReader reader;
    private final AttendanceBooks attendanceBooks;

    public AttendanceManager(AttendanceReader reader) {
        this.reader = reader;
        this.attendanceBooks = readAttendance();
    }

    private AttendanceBooks readAttendance() {
        List<AttendanceFileDto> read = reader.read();
        AttendanceBooks newAttendanceBooks = new AttendanceBooks();
        for (AttendanceFileDto attendanceFileDto : read) {
            newAttendanceBooks.addAttendance(attendanceFileDto.name(),
                new Attendance(attendanceFileDto.attendanceDate(), attendanceFileDto.attendanceTime()));
        }
        return newAttendanceBooks;
    }

    public AttendanceInfoDto remarkAttendance(String name, LocalDate attendanceDate, LocalTime attendanceTime) {
        attendanceBooks.validateNameExists(name);
        attendanceBooks.hasAttendance(name, attendanceDate);
        attendanceBooks.addAttendance(name, new Attendance(attendanceDate, attendanceTime));
        AttendanceStatus attendanceStatus = AttendanceStatus.findAttendanceStatus(attendanceDate, attendanceTime);
        return AttendanceInfoDto.of(attendanceDate, attendanceTime, attendanceStatus);
    }

    public AttendanceEditDto editAttendance(String name, LocalDate editAttendanceDate, LocalTime editAttendanceTime) {
        attendanceBooks.validateNameExists(name);
        Optional<LocalTime> beforeEditTime = attendanceBooks.editAttendance(name, editAttendanceDate, editAttendanceTime);
        AttendanceStatus beforeEditStatus = AttendanceStatus.findAttendanceStatus(editAttendanceDate, beforeEditTime.orElse(null));
        AttendanceStatus editStatus = AttendanceStatus.findAttendanceStatus(editAttendanceDate, editAttendanceTime);
        return AttendanceEditDto.of(
            editAttendanceDate, beforeEditTime.orElse(null), beforeEditStatus, editAttendanceTime, editStatus);
    }

    public AttendanceCheckDto checkAttendance(String name, LocalDate today) {
        attendanceBooks.validateNameExists(name);
        List<Attendance> attendanceUntilYesterday = attendanceBooks.findAttendanceUntilYesterday(name, today);
        Map<AttendanceStatus, Integer> attendanceStatusCount = attendanceBooks.countAttendanceStatus(name, today);
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

    public List<PenaltyCrewDto> findPenaltyCrews(LocalDate today) {
        List<String> allCrewNames = attendanceBooks.getAllCrewNames();

        List<PenaltyCrew> penaltyCrews = findPenaltyOfCrews(today, allCrewNames);

        return penaltyCrews.stream()
            .filter(penaltyCrew -> penaltyCrew.getPenalty() != AttendancePenalty.NONE)
            .sorted()
            .map(PenaltyCrewDto::of)
            .toList();
    }

    private List<PenaltyCrew> findPenaltyOfCrews(LocalDate today, List<String> allCrewNames) {
        List<PenaltyCrew> penaltyCrews = new ArrayList<>();
        for (String crewName : allCrewNames) {
            Map<AttendanceStatus, Integer> attendanceStatusIntegerMap = attendanceBooks.countAttendanceStatus(crewName, today);
            penaltyCrews.add(new PenaltyCrew(crewName, attendanceStatusIntegerMap));
        }
        return penaltyCrews;
    }
}
