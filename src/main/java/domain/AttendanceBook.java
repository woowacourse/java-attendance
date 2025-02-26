package domain;

import dto.CheckAttendanceResponse;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import view.ErrorMessage;

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
                .orElseThrow(
                        () -> new IllegalArgumentException(ErrorMessage.NOTICE_NICKNAME_IS_NOT_REGISTERED.getFormat()));
    }

    public boolean checkCrewExisted(String name) {
        return crews.stream()
                .anyMatch(crew -> crew.isMyName(name));
    }

    public CheckAttendanceResponse checkAttendance(String name, LocalDate date, LocalTime time) {
        String attendanceStatus = validateTrainingDay(date, time);

        Crew foundCrew = findCrewByName(name);
        validateAlreadyAttendance(foundCrew, date);

        foundCrew.addNewTimeLog(date, time);
        return new CheckAttendanceResponse(time, attendanceStatus);
    }

    public static String validateTrainingDay(LocalDate date, LocalTime time) {
        String attendanceStatus = AttendanceStatus.judgeAttendanceStatusByDateAndTime(date, time);
        if (!attendanceStatus.equals("출석") && !attendanceStatus.equals("결석") && !attendanceStatus.equals("지각")) {
            throw new IllegalArgumentException(
                    ErrorMessage.NOTICE_NOT_TRAINING_DAY.format(date.getMonthValue(), date.getDayOfMonth(),
                            attendanceStatus));
        }

        return attendanceStatus;
    }

    public static void validateAlreadyAttendance(Crew foundCrew, LocalDate date) {
        if (foundCrew.isDateExisted(date)) { // 날짜가 존재한다면
            throw new IllegalArgumentException(ErrorMessage.NOTICE_ATTENDANCE_ALREADY_EXISTED.getFormat());
        }
    }
}