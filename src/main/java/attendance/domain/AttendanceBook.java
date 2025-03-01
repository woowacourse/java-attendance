package attendance.domain;

import attendance.util.ErrorMessage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class AttendanceBook {

    private final Map<Crew, Attendances> attendanceBook;

    public AttendanceBook(Map<Crew, Attendances> attendanceBook) {
        this.attendanceBook = attendanceBook;
    }

    public void add(Crew crew, Attendance attendance) {
        if (attendanceBook.containsKey(crew)) {
            attendanceBook.get(crew).add(attendance);
            return;
        }

        attendanceBook.put(crew, new Attendances(new ArrayList<>(List.of(attendance))));
    }

    public Attendance findAttendanceByCrew(Crew crew, LocalDate inputDate) {
        return attendanceBook.get(crew).findByDate(inputDate);
    }

    public void update(Crew crew, Attendance oldAttendance, Attendance newAttendance) {
        Attendances attendances = attendanceBook.get(crew);
        attendances.update(oldAttendance, newAttendance);
    }

    public void validateCrew(Crew crew) {
        if (!attendanceBook.containsKey(crew)) {
            throw new IllegalArgumentException(ErrorMessage.CREW_NICKNAME_NOT_EXIST_ERROR.getMessage());
        }
    }

    public List<Attendance> getRecordOfCrew(LocalDate today, Crew crew) {
        Attendances attendances = attendanceBook.get(crew);
        return attendances.getAttendancesUntilYesterday(today);
    }

    public Map<Crew, StatusStatistics> getSortedCrewsAndStatistics(LocalDate today) {
        Map<Crew, StatusStatistics> penaltyCrews = new LinkedHashMap<>();
        List<Crew> sortedCrews = attendanceBook.keySet()
                .stream()
                .sorted((crew, otherCrew) -> compareCrew(today, crew, otherCrew))
                .toList();

        sortedCrews.forEach(crew -> {
            List<Attendance> attendances = getRecordOfCrew(today, crew);
            StatusStatistics statusStatistics = new StatusStatistics(attendances, today);
            penaltyCrews.put(crew, statusStatistics);
        });
        return penaltyCrews;
    }

    private int compareCrew(LocalDate today, Crew crew, Crew otherCrew) {
        int totalAbsentOfCrew = calculateTotalAbsentForCrew(today, crew);
        int totalAbsentOfOtherCrew = calculateTotalAbsentForCrew(today, otherCrew);

        if (totalAbsentOfCrew != totalAbsentOfOtherCrew) {
            return Integer.compare(totalAbsentOfOtherCrew, totalAbsentOfCrew);
        }

        return crew.getNickname().compareTo(otherCrew.getNickname());
    }

    private int calculateTotalAbsentForCrew(LocalDate today, Crew crew) {
        StatusStatistics statistics = createStatusStatistics(today, crew);

        int absentCount = statistics.getAttendanceStatusCount(AttendanceStatus.ABSENT);
        int lateCount = statistics.getAttendanceStatusCount(AttendanceStatus.LATE);

        return Penalty.calculateTotalAbsent(lateCount, absentCount);
    }

    private StatusStatistics createStatusStatistics(LocalDate today, Crew crew) {
        List<Attendance> attendances = getRecordOfCrew(today, crew);
        return new StatusStatistics(attendances, today);
    }
}
