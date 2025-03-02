package attendance.domain;

import static attendance.domain.AttendancePolicy.calculateAttendanceStatus;
import static attendance.exception.ErrorMessage.NOT_EXISTS_CREW_NICKNAME;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

public class AttendanceManager {
    private final Map<Nickname, AttendanceHistory> attendanceBook = new HashMap<>();

    public void addCrew(final Nickname crewNickname) {
        attendanceBook.putIfAbsent(crewNickname, new AttendanceHistory());
    }

    public Attendance addAttendance(final Nickname crewNickname,
                                    final LocalDate attendanceDate,
                                    final LocalTime attendanceTime) {
        AttendanceHistory attendanceHistory = getAttendanceHistory(crewNickname);
        AttendanceStatus status = calculateAttendanceStatus(attendanceDate.getDayOfWeek(), attendanceTime);
        Attendance attendance = new Attendance(attendanceDate, attendanceTime, status);
        return attendanceHistory.addAttendance(attendance);
    }

    public Attendance modifyAttendance(final Nickname crewNickname,
                                       final LocalDate dateToModify,
                                       final LocalTime modificationTime) {
        AttendanceHistory attendanceHistory = getAttendanceHistory(crewNickname);
        AttendanceStatus modificationStatus = calculateAttendanceStatus(dateToModify.getDayOfWeek(), modificationTime);
        Attendance modifiedAttendance = new Attendance(dateToModify, modificationTime, modificationStatus);
        return attendanceHistory.modifyAttendance(modifiedAttendance);
    }

    public void validateDuplicatedAttendance(final Nickname crewNickname, final LocalDate attendanceDate) {
        AttendanceHistory attendanceHistory = getAttendanceHistory(crewNickname);
        attendanceHistory.validateDuplicatedAttendance(attendanceDate);
    }

    public Optional<Attendance> findAttendance(final Nickname crewNickname, final LocalDate attendanceDate) {
        AttendanceHistory attendanceHistory = getAttendanceHistory(crewNickname);
        return attendanceHistory.findAttendance(attendanceDate);
    }

    public List<Attendance> getMonthlyAttendances(final LocalDate today, final Nickname crewNickname) {
        AttendanceHistory attendanceHistory = getAttendanceHistory(crewNickname);
        return attendanceHistory.getMonthlyAttendances(today);
    }

    public AttendanceStatistics getAttendanceStatistics(final LocalDate today, final Nickname crewNickname) {
        AttendanceHistory attendanceHistory = getAttendanceHistory(crewNickname);
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

    public void validateExistingCrew(final Nickname crewNickname) {
        boolean isCrewExists = attendanceBook.containsKey(crewNickname);
        if (!isCrewExists) {
            throw new IllegalArgumentException(NOT_EXISTS_CREW_NICKNAME.getMessage());
        }
    }

    private AttendanceHistory getAttendanceHistory(final Nickname crewNickname) {
        validateExistingCrew(crewNickname);
        return attendanceBook.get(crewNickname);
    }
}
