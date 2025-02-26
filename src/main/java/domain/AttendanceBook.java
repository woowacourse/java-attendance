package domain;

import dto.CheckAttendanceResponse;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AttendanceBook {
    private final List<Crew> crews;

    public AttendanceBook() {
        this.crews = new ArrayList<>();
    }

    public void registerCrew(String name, LocalDate date, LocalTime time) {
        if (!checkCrewExisted(name)) {
            crews.add(new Crew(name));
        }
        findCrewByName(name).addNewTimeLog(date, time);
    }

    public Crew findCrewByName(String name) {
        return crews.stream()
                .filter(crew -> crew.isMyName(name))
                .findAny()
                .orElseThrow();
    }

    public boolean checkCrewExisted(String name) {
        return crews.stream()
                .anyMatch(crew -> crew.isMyName(name));
    }

    public CheckAttendanceResponse checkAttendance(String name, LocalDate date, LocalTime time) {
        String attendanceStatus = validateWorkingDay(date, time);

        Crew foundCrew = findCrewByName(name);
        foundCrew.addNewTimeLog(date, time);
        return new CheckAttendanceResponse(time, attendanceStatus);
    }

    public static String validateWorkingDay(LocalDate date, LocalTime time) {
        String attendanceStatus = AttendanceStatus.judgeAttendanceStatusByDateAndTime(date, time);

        if (!attendanceStatus.equals("출석") && !attendanceStatus.equals("결석") && !attendanceStatus.equals("지각")) {
            throw new IllegalArgumentException(
                    String.format("[ERROR] %02d월 %02d일 %s은 등교일이 아닙니다.",
                            date.getMonthValue(), date.getDayOfMonth(), attendanceStatus));
        }

        return attendanceStatus;
    }
}