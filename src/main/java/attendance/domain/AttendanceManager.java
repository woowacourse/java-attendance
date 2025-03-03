package attendance.domain;

import attendance.dto.AttendanceFileDto;

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

    public Attendance remarkAttendance(String name, LocalDate attendanceDate, LocalTime attendanceTime) {
        attendanceBooks.validateNameExists(name);
        attendanceBooks.hasAttendance(name, attendanceDate);
        attendanceBooks.addAttendance(name, new Attendance(attendanceDate, attendanceTime));
        return new Attendance(attendanceDate, attendanceTime);
    }

    public Attendance editAttendance(String name, LocalDate editAttendanceDate, LocalTime editAttendanceTime) {
        attendanceBooks.validateNameExists(name);
        Optional<LocalTime> beforeEditTime = attendanceBooks.editAttendance(name, editAttendanceDate, editAttendanceTime);
        return new Attendance(editAttendanceDate, beforeEditTime.orElse(null));
    }

    public AttendanceCheckResult checkAttendance(String name, LocalDate today) {
        attendanceBooks.validateNameExists(name);
        List<Attendance> attendanceUntilYesterday = attendanceBooks.findAttendanceUntilYesterday(name, today);
        Map<AttendanceStatus, Integer> attendanceStatusCount = attendanceBooks.countAttendanceStatus(name, today);
        PenaltyCount penaltyCount = new PenaltyCount(attendanceStatusCount);
        AttendancePenalty attendancePenalty = penaltyCount.findAttendancePenalty();

        return new AttendanceCheckResult(name, attendanceUntilYesterday, attendanceStatusCount, attendancePenalty);
    }

    public List<PenaltyCrew> findPenaltyCrews(LocalDate today) {
        List<String> allCrewNames = attendanceBooks.getAllCrewNames();
        List<PenaltyCrew> penaltyCrews = new ArrayList<>();
        for (String crewName : allCrewNames) {
            Map<AttendanceStatus, Integer> attendanceStatusIntegerMap = attendanceBooks.countAttendanceStatus(crewName, today);
            penaltyCrews.add(new PenaltyCrew(crewName, attendanceStatusIntegerMap));
        }
        return penaltyCrews;
    }

    public AttendanceStatus getAttendanceStatus(LocalDate attendanceDate, LocalTime attendanceTime) {
        return AttendanceStatus.findAttendanceStatus(attendanceDate, attendanceTime);
    }
}
