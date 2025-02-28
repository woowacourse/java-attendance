package attendance.domain;

import static attendance.domain.AttendancePolicy.calculateAttendanceStatus;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

public class AttendanceManager {
    private final Map<Nickname, AttendanceHistory> attendanceBook = new HashMap<>();

    public boolean isCrewExists(final Nickname crewNickname) {
        return attendanceBook.containsKey(crewNickname);
    }

    public void addCrew(final Nickname crewNickname) {
        attendanceBook.put(crewNickname, new AttendanceHistory());
    }

    public Attendance addAttendance(final Nickname crewNickname,
                                    final LocalDate attendanceDate,
                                    final LocalTime attendanceTime) {
        AttendanceStatus status = calculateAttendanceStatus(attendanceDate.getDayOfWeek(), attendanceTime);
        AttendanceHistory attendanceHistory = attendanceBook.get(crewNickname);
        Attendance attendance = new Attendance(attendanceDate, attendanceTime, status);
        return attendanceHistory.addAttendance(attendance);
    }

    public Optional<Attendance> findAttendance(final Nickname crewNickname, final LocalDate attendanceDate) {
        AttendanceHistory attendanceHistory = attendanceBook.get(crewNickname);
        return attendanceHistory.findAttendance(attendanceDate);
    }

    public Attendance modifyAttendance(final Nickname crewNickname,
                                       final LocalDate dateToModify,
                                       final LocalTime modificationTime) {
        AttendanceStatus modificationStatus = calculateAttendanceStatus(dateToModify.getDayOfWeek(), modificationTime);
        AttendanceHistory attendanceHistory = attendanceBook.get(crewNickname);
        Attendance modifiedAttendance = new Attendance(dateToModify, modificationTime, modificationStatus);
        return attendanceHistory.modifyAttendance(modifiedAttendance);
    }

    public List<Attendance> getMonthlyAttendances(final LocalDate today, final Nickname crewNickname) {
        AttendanceHistory attendanceHistory = attendanceBook.get(crewNickname);
        return attendanceHistory.getMonthlyAttendances(today);
    }

    public AttendanceStatistics getAttendanceStatistics(final LocalDate today, final Nickname crewNickname) {
        AttendanceHistory attendanceHistory = attendanceBook.get(crewNickname);
        return attendanceHistory.getAttendanceStatistics(today);
    }

    public Map<Nickname, AttendanceStatistics> getDangerousCrewsInformation(final LocalDate today) {
        Map<Nickname, AttendanceStatistics> dangerousCrewsStatistics = new HashMap<>();
        for (Entry<Nickname, AttendanceHistory> entry : attendanceBook.entrySet()) {
            AttendanceHistory attendanceHistory = entry.getValue();
            AttendanceStatistics attendanceStatistics = attendanceHistory.getAttendanceStatistics(today);
            CrewStatus crewStatus = attendanceStatistics.calculateCrewStatus();
            if (!crewStatus.equals(CrewStatus.NONE)) {
                dangerousCrewsStatistics.put(entry.getKey(), attendanceStatistics);
            }
        }
        return dangerousCrewsStatistics;
    }
}
