package attendance.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendanceBook {
    private final Map<String, List<AttendanceTime>> attendances = new HashMap<>();

    public void add(final String name, final AttendanceTime attendanceTime) {

        if (!attendances.containsKey(name)) {
            attendances.put(name, new ArrayList<>());
        }
        attendances.get(name).add(attendanceTime);
    }

    public AttendanceTime getAttendance(final String name, final LocalDate date) {

        return attendances.get(name)
                .stream()
                .filter(time -> time.isSameDay(date))
                .toList()
                .getFirst();
    }

    public boolean isCrewExists(final String name) {

        return attendances.containsKey(name);
    }

    public boolean isAlreadyExists(final String name, final LocalDate localDate) {

        List<AttendanceTime> crewAttendances = attendances.get(name);
        return crewAttendances.stream().anyMatch(attendanceTime -> attendanceTime.isSameDay(localDate));
    }

    public List<AttendanceTime> getAttendancesByName(final String name) {

        return List.copyOf(attendances.get(name));
    }

    public void initCrewsAbsence() {

        for (String name : attendances.keySet()) {
            initAbsence(name);
        }
    }

    private void initAbsence(final String name) {

        LocalDate now = LocalDate.now();
        for (int day = 1; day <= now.getDayOfMonth(); day++) {
            LocalDate attendDate = LocalDate.of(now.getYear(), now.getMonthValue(), day);
            judgeAbsence(name, attendDate);
        }
    }

    private void judgeAbsence(String name, LocalDate attendDate) {

        if (!AttendanceTime.isWeekend(attendDate.getDayOfWeek()) && !isAlreadyExists(name, attendDate)) {
            attendances.get(name).add(new AttendanceTime(attendDate));
        }
    }

    public int getAttendanceStatusCount(final String name, final AttendanceStatus attendanceStatus) {

        return (int) attendances.get(name)
                .stream()
                .filter(attendanceTime -> AttendanceStatus.getAttendanceStatus(attendanceTime) == attendanceStatus)
                .count();
    }
}
