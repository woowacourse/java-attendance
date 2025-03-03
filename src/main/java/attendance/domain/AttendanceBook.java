package attendance.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendanceBook {

    private final Map<String, AttendanceHistory> attendances = new HashMap<>();

    public void add(final String name, final AttendanceTime attendanceTime) {

        if (!attendances.containsKey(name)) {
            attendances.put(name, new AttendanceHistory());
        }
        attendances.get(name).add(attendanceTime);
    }

    public AttendanceTime getAttendance(final String name, final LocalDate date) {

        return attendances.get(name).getAttendanceTime(date);
    }

    public boolean isCrewExists(final String name) {

        return attendances.containsKey(name);
    }

    public boolean isAlreadyExists(final String name, final LocalDate localDate) {

        return attendances.get(name).isAlreadyExists(localDate);
    }

    public List<AttendanceTime> getAttendancesByName(final String name) {

        return attendances.get(name).getHistory();
    }

    public void initCrewsAbsence() {

        for (String name : attendances.keySet()) {
            initAbsence(name);
        }
    }

    public int getAttendanceStatusCount(final String name, final AttendanceStatus attendanceStatus) {

        return attendances.get(name).getAttendanceStatusCount(attendanceStatus);
    }

    public List<ExpulsionCandidate> getExpulsionCandidates() {

        List<ExpulsionCandidate> expulsionCandidates = new ArrayList<>();
        expulsionCandidates.addAll(getCrewsByAcademicStatus(AcademicStatus.EXPELLED));
        expulsionCandidates.addAll(getCrewsByAcademicStatus(AcademicStatus.INTERVIEW));
        expulsionCandidates.addAll(getCrewsByAcademicStatus(AcademicStatus.WARN));
        return expulsionCandidates;
    }

    private void initAbsence(final String name) {

        LocalDate now = LocalDate.now();
        for (int day = 1; day < now.getDayOfMonth(); day++) {
            LocalDate attendDate = LocalDate.of(now.getYear(), now.getMonthValue(), day);
            judgeAbsence(name, attendDate);
        }
    }

    private void judgeAbsence(final String name, final LocalDate attendDate) {

        if (!AttendanceTime.isWeekend(attendDate.getDayOfWeek()) && !isAlreadyExists(name, attendDate)) {
            attendances.get(name).add(new AttendanceTime(attendDate));
        }
    }

    private List<ExpulsionCandidate> getCrewsByAcademicStatus(final AcademicStatus academicStatus) {

        return attendances.keySet()
                .stream()
                .map(this::getExpulsionCandidate)
                .filter(crew -> crew.status() == academicStatus)
                .sorted(Comparator.comparing(ExpulsionCandidate::absent).thenComparing(ExpulsionCandidate::name))
                .toList();
    }

    private ExpulsionCandidate getExpulsionCandidate(final String name) {

        int absent = getAttendanceStatusCount(name, AttendanceStatus.ABSENT);
        int late = getAttendanceStatusCount(name, AttendanceStatus.LATE);
        return new ExpulsionCandidate(name, absent, late, AcademicStatus.getAcademicStatus(late, absent));
    }
}
