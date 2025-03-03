package domain;

import domain.policy.DatePolicy;
import domain.policy.PenaltyPolicy;
import domain.policy.TimePolicy;
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
        TimePolicy.validateTimeIsInTheRangeOfOperation(time);
        DatePolicy.validateIsDateHoliday(date);
        DatePolicy.validateIsDateWeekend(date);
        String attendanceStatus = AttendanceDiscriminator.judgeTimeLogForStatus(date, time);

        Crew foundCrew = findCrewByName(name);
        validateAlreadyAttendance(foundCrew, date);

        foundCrew.addNewTimeLog(date, time);
        return new CheckAttendanceResponse(time, attendanceStatus);
    }

    // 기능 2
    public ModifyAttendanceResponse modifyAttendance(String name, LocalDate date, LocalTime modifiedTime) {
        TimePolicy.validateTimeIsInTheRangeOfOperation(modifiedTime);
        DatePolicy.validateIsDateFuture(date);
        DatePolicy.validateIsDateHoliday(date);
        DatePolicy.validateIsDateWeekend(date);

        Crew foundCrew = findCrewByName(name);
        foundCrew.gratifyTimeLogs(); // 수정하려는 날짜가 기록이 없는 경우를 대비하여 빈 타임 로그 구현

        LocalTime previousTime = foundCrew.getTimeByDate(date);// 이전 시간 가져오기
        String previousStatus = AttendanceDiscriminator.judgeTimeLogForStatus(date, previousTime); // 변경 전 출결 현황

        foundCrew.addNewTimeLog(date, modifiedTime); // 시간 변경하기
        String modifiedStatus = AttendanceDiscriminator.judgeTimeLogForStatus(date, modifiedTime); // 변경 후 출결 현황
        return new ModifyAttendanceResponse(date, previousTime, modifiedTime, previousStatus, modifiedStatus);
    }

    // 기능 3
    public List<CheckAttendanceRecordResponse> checkAttendanceRecord(String name) {
        Crew foundCrew = findCrewByName(name);

        foundCrew.gratifyTimeLogs();
        return foundCrew.getTimeLogs().entrySet().stream()
                .sorted(Map.Entry.comparingByKey()) // 날짜 기준으로 정렬
                .map(entry -> new CheckAttendanceRecordResponse(
                        entry.getKey(), entry.getValue(),
                        AttendanceDiscriminator.judgeTimeLogForStatus(entry.getKey(), entry.getValue())))
                .collect(Collectors.toList());
    }

    // 기능 4
    public List<PenaltyCrewResponse> checkPenaltyCrew() {
        List<PenaltyCrewResponse> responses = new ArrayList<>();
        for (Crew crew : crews) {
            PenaltyResponse response = PenaltyDiscriminator.judgeCrewAttendanceRecord(
                    checkAttendanceRecord(crew.getName()));
            if (!response.penalty().isEmpty()) { // 패널티가 존재하는 경우
                responses.add(new PenaltyCrewResponse(crew.getName(), response.lateCount(), response.absentCount(),
                        response.penalty()));
            }
        }

        // 정렬 기준 적용
        responses.sort(Comparator
                .comparing(PenaltyCrewResponse::penalty).reversed() // 1. penalty 한글 내림차순 (제적 -> 면담 -> 경고 순)
                .thenComparing(p -> -(PenaltyPolicy.calculatePenaltyCount(p.absentCount(),
                        p.lateCount()))) // 2. 패널티 적용 숫자에 따라 내림차순
                .thenComparing(PenaltyCrewResponse::name)); // 3. name 기준 오름차순

        return responses;
    }

    public boolean checkCrewExisted(String name) {
        return crews.stream()
                .anyMatch(crew -> crew.isSameName(name));
    }

    public Crew findCrewByName(String name) {
        return crews.stream()
                .filter(crew -> crew.isSameName(name))
                .findAny()
                .orElseThrow(
                        () -> new IllegalArgumentException(ErrorMessage.NOTICE_NICKNAME_IS_NOT_REGISTERED.getFormat()));
    }
}