package domain;

import static constants.AttendanceCriteria.OPERATING_END;
import static constants.AttendanceCriteria.OPERATING_START;

import dto.CheckAttendanceResponse;
import dto.ModifyAttendanceResponse;
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
        validateTimeIsInTheRangeOfOperation(time);
        String attendanceStatus = validateTrainingDay(date, time);

        Crew foundCrew = findCrewByName(name);
        validateAlreadyAttendance(foundCrew, date);

        foundCrew.addNewTimeLog(date, time);
        return new CheckAttendanceResponse(time, attendanceStatus);
    }

    private static void validateTimeIsInTheRangeOfOperation(LocalTime time) {
        if (time.isBefore(OPERATING_START.getTime()) || time.isAfter(OPERATING_END.getTime())) {
            throw new IllegalArgumentException(ErrorMessage.NOTICE_TIME_IS_NOT_A_CAMPUS_OPERATING_TIME.getFormat());
        }
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

    public ModifyAttendanceResponse modifyAttendance(String name, LocalDate date, LocalTime time) {
        findCrewByName(name).addNewTimeLog(date, time);
        String attendanceStatus = validateTrainingDay(date, time);
        return new ModifyAttendanceResponse(date, time, attendanceStatus);
    }
}