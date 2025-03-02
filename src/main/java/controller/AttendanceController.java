package controller;

import static domain.Crew.DECEMBER_DAYS_END;
import static domain.Crew.DECEMBER_DAYS_START;
import static domain.Crew.SYSTEM_MONTH;
import static domain.Crew.SYSTEM_YEAR;

import domain.AttendanceBook;
import domain.AttendanceStatus;
import domain.Crew;
import domain.Penalty;
import dto.AttendanceRecordResponse;
import dto.AttendanceStatusCountResponse;
import dto.CheckAttendanceResponse;
import dto.CrewsWithPenaltyResponse;
import dto.ModifyAttendanceResponse;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import util.OutputParser;

public class AttendanceController {

    private final AttendanceBook attendanceBook;

    public AttendanceController(AttendanceBook attendanceBook) {
        this.attendanceBook = attendanceBook;
    }

    public void start() {
    }

    public CheckAttendanceResponse checkAttendance(String name, LocalDate date, LocalTime time) {
        attendanceBook.putAttendanceRecordByName(name, date, time);

        return new CheckAttendanceResponse(
                OutputParser.parseDateInKorean(date), OutputParser.parseTimeToString(time),
                AttendanceStatus.findMessageByAttendDateAndTime(date, time)
        );
    }

    public ModifyAttendanceResponse modifyAttendance(String name, LocalDate date, LocalTime timeToModify) {
        LocalTime originalTime = attendanceBook.findTimeByNameAndDate(name, date);
        attendanceBook.putAttendanceRecordByName(name, date, timeToModify);

        return new ModifyAttendanceResponse(
                OutputParser.parseDateInKorean(date),
                OutputParser.parseTimeToString(originalTime),
                OutputParser.parseTimeToString(timeToModify),
                AttendanceStatus.findMessageByAttendDateAndTime(date, originalTime),
                AttendanceStatus.findMessageByAttendDateAndTime(date, timeToModify)
        );
    }

    public List<AttendanceRecordResponse> getAttendanceRecordResponses(String name) {
        return IntStream.rangeClosed(DECEMBER_DAYS_START, DECEMBER_DAYS_END)
                .mapToObj(day -> createAttendanceRecordResponseByName(name, day))
                .collect(Collectors.toList());
    }

    private AttendanceRecordResponse createAttendanceRecordResponseByName(String name, int day) {
        LocalDate date = LocalDate.of(SYSTEM_YEAR, SYSTEM_MONTH, day);
        LocalTime time = attendanceBook.findTimeByNameAndDate(name, date);

        return new AttendanceRecordResponse(
                OutputParser.parseDateInKorean(date),
                OutputParser.parseTimeToString(time),
                AttendanceStatus.findByAttendDateAndTime(date, time).getMessage()
        );
    }

    public AttendanceStatusCountResponse getAttendanceStatusCountResponseByName(String name) {
        return new AttendanceStatusCountResponse(
                attendanceBook.getAttendanceStatusCountByName(name, AttendanceStatus.ATTEND),
                attendanceBook.getAttendanceStatusCountByName(name, AttendanceStatus.LATE),
                attendanceBook.getAttendanceStatusCountByName(name, AttendanceStatus.ABSENT)
        );
    }

    public String getPenaltyResponseByName(String name) {
        int lateCount = attendanceBook.getAttendanceStatusCountByName(name, AttendanceStatus.LATE);
        int absentCount = attendanceBook.getAttendanceStatusCountByName(name, AttendanceStatus.ABSENT);

        return Penalty.findPenaltyMessageByAttendanceStatusCount(lateCount, absentCount);
    }

    public List<CrewsWithPenaltyResponse> getCrewsWithPenaltyResponse() {
        List<CrewsWithPenaltyResponse> mergedResponses = new ArrayList<>();

        Penalty.valuesWithoutNone().stream()
                .map(this::createCrewsWithPenaltyResponseByPenalty)
                .forEach(mergedResponses::addAll);

        return mergedResponses;
    }

    private List<CrewsWithPenaltyResponse> createCrewsWithPenaltyResponseByPenalty(Penalty penalty) {
        List<Crew> penaltyCrews = attendanceBook.findCrewsWithPenalty();

        return penaltyCrews.stream()
                .map(CrewsWithPenaltyResponse::fromCrew)
                .toList();
    }
}
