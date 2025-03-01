package domain;

import static constants.AttendanceCriteria.OPERATING_END;
import static constants.AttendanceCriteria.OPERATING_START;

import dto.CheckAttendanceRecordResponse;
import dto.CheckAttendanceResponse;
import dto.ModifyAttendanceResponse;
import dto.PenaltyCrewResponse;
import dto.PenaltyResponse;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import view.ErrorMessage;

public class AttendanceBook {
    private final List<Crew> crews;

    public AttendanceBook() {
        this.crews = new ArrayList<>();
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

    public static void validateTimeIsInTheRangeOfOperation(LocalTime time) {
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

    public void validateIsDateFuture(LocalDate date) {
        if (date.isAfter(LocalDate.now())) { // 현재보다 미래 시점인 경우
            throw new IllegalArgumentException(ErrorMessage.NOTICE_FUTURE_CAN_NOT_BE_MODIFIED.getFormat());
        }
    }

    public static void validateAlreadyAttendance(Crew foundCrew, LocalDate date) {
        if (foundCrew.isDateExisted(date)) { // 날짜가 존재한다면
            throw new IllegalArgumentException(ErrorMessage.NOTICE_ATTENDANCE_ALREADY_EXISTED.getFormat());
        }
    }

    // 초기 등록
    public void registerCrew(String name, LocalDate date, LocalTime time) {
        if (!checkCrewExisted(name)) {
            crews.add(new Crew(name));
        }
        findCrewByName(name).addNewTimeLog(date, time);
    }

    // 기능 1
    public CheckAttendanceResponse checkAttendance(String name, LocalDate date, LocalTime time) {
        validateTimeIsInTheRangeOfOperation(time);
        String attendanceStatus = validateTrainingDay(date, time);

        Crew foundCrew = findCrewByName(name);
        validateAlreadyAttendance(foundCrew, date);

        foundCrew.addNewTimeLog(date, time);
        return new CheckAttendanceResponse(time, attendanceStatus);
    }

    // 기능 2
    public ModifyAttendanceResponse modifyAttendance(String name, LocalDate date, LocalTime modifiedTime) {
        validateTimeIsInTheRangeOfOperation(modifiedTime);
        validateIsDateFuture(date);

        Crew foundCrew = findCrewByName(name);
        foundCrew.gratifyTimeLogs(); // 수정하려는 날짜가 기록이 없는 경우를 대비하여 빈 타임 로그 구현

        LocalTime previousTime = foundCrew.findTimeByDate(date);// 이전 시간 가져오기
        String previousStatus = validateTrainingDay(date, previousTime); // 변경 전 출결 현황

        foundCrew.addNewTimeLog(date, modifiedTime); // 시간 변경하기
        String modifiedStatus = validateTrainingDay(date, modifiedTime); // 변경 후 출결 현황
        return new ModifyAttendanceResponse(date, previousTime, modifiedTime, previousStatus, modifiedStatus);
    }

    // 기능 3
    public List<CheckAttendanceRecordResponse> checkAttendanceRecord(String name) {
        Crew foundCrew = findCrewByName(name);

        foundCrew.gratifyTimeLogs();
        return foundCrew.getTimeLogs().entrySet().stream()
                .sorted(Map.Entry.comparingByKey()) // 날짜 기준으로 정렬
                .map(entry -> new CheckAttendanceRecordResponse(
                        entry.getKey(),
                        entry.getValue(),
                        AttendanceStatus.judgeAttendanceStatusByDateAndTime(entry.getKey(), entry.getValue())))
                .collect(Collectors.toList());
    }

    // 기능 4
    public List<PenaltyCrewResponse> checkPenaltyCrew() {
        List<PenaltyCrewResponse> responses = new ArrayList<>();
        for (Crew crew : crews) {
            PenaltyResponse response = PenaltyStatus.judgeCrewAttendanceRecord(checkAttendanceRecord(crew.getName()));
            if (!response.penalty().isEmpty()) { // 패널티가 존재하는 경우
                responses.add(new PenaltyCrewResponse(crew.getName(), response.lateCount(), response.absentCount(),
                        response.penalty()));
            }
        }

        // 정렬 기준 적용
        responses.sort(Comparator
                .comparing(PenaltyCrewResponse::penalty).reversed() // 1. penalty 한글 내림차순 (제적 -> 면담 -> 경고 순)
                .thenComparing(p -> -(p.absentCount() + p.lateCount() / 3.0)) // 2. (absentCount + lateCount / 3.0) 내림차순
                .thenComparing(PenaltyCrewResponse::name)); // 3. name 기준 오름차순

        return responses;
    }
}