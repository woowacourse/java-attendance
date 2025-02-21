package domain;

import static constants.TimeConstants.OPERATION_TIME_END;
import static constants.TimeConstants.OPERATION_TIME_START;

import dto.AttendanceRecordResponse;
import dto.CrewPenaltyResponse;
import dto.ModifyAttendanceResponse;
import dto.TotalRecordsResponse;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AttendanceBook {
    private final List<Crew> crews;

    public AttendanceBook() {
        this.crews = new ArrayList<>();
    }

    public Crew getCrewByName(String name) {
        return crews.stream()
                .filter(crew -> crew.hasName(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.NICKNAME_NOT_FOUND.getMessage()));
    }

    // 데이터 유효성 검사
    public void validateNameAlreadyExists(String name) {
        if (!checkCrewAlreadyExists(name)) {
            throw new IllegalArgumentException(ErrorCode.NICKNAME_NOT_FOUND.getMessage());
        }
    }

    public void validateDateAlreadyExistsByCrewName(String name, LocalDate date) {
        Crew foundCrew = getCrewByName(name);
        foundCrew.validateDateAlreadyExists(date);
    }

    public void validateIsInOperationHour(LocalTime time) {
        if (!time.isAfter(OPERATION_TIME_START) || !time.isBefore(OPERATION_TIME_END)) {
            throw new IllegalArgumentException(ErrorCode.TIME_NOT_IN_OPERATION_HOUR.getMessage());
        }
    }

    // 출석부 초기화: csv 파일 데이터를 적용
    public void initialize(String name, Map<LocalDate, LocalTime> dateAndTime) {
        if (!checkCrewAlreadyExists(name)) {
            addNewCrew(Crew.createByName(name));
        }
        Crew crew = getCrewByName(name);
        crew.addDailyAttendance(dateAndTime);
    }

    public boolean checkCrewAlreadyExists(String name) {
        return crews.stream()
                .anyMatch(crew -> crew.hasName(name));
    }

    public void addNewCrew(Crew newCrew) {
        crews.add(newCrew);
    }

    // 기능 1. 출석 확인
    public AttendanceRecordResponse checkAttendance(String name, Map<LocalDate, LocalTime> dateAndTime) {
        Crew foundCrew = getCrewByName(name);

        LocalDate date = dateAndTime.keySet().stream()
                .findAny()
                .orElseThrow();

        Calendar.validateIsWorkingDay(date.getDayOfMonth());

        LocalTime time = dateAndTime.values().stream()
                .findAny()
                .orElseThrow();

        validateIsInOperationHour(time);

        foundCrew.addDailyAttendance(dateAndTime);
        return new AttendanceRecordResponse(date, time, AttendanceStatus.judgeStatus(date, time));
    }

    // 기능 2. 출석 수정
    public ModifyAttendanceResponse modifyAttendance(String name, Map<LocalDate, LocalTime> dateAndTimeToModify) {

        Crew foundCrew = getCrewByName(name);

        LocalDate date = dateAndTimeToModify.keySet().stream()
                .findAny()
                .orElseThrow();

        LocalTime originalTime = foundCrew.getTimeByDate(date);

        LocalTime modifiedTime = dateAndTimeToModify.values().stream()
                .findAny()
                .orElseThrow();

        validateIsInOperationHour(modifiedTime);

        foundCrew.modifyDailyAttendance(dateAndTimeToModify);

        return new ModifyAttendanceResponse(
                date,
                originalTime,
                modifiedTime,
                AttendanceStatus.judgeStatus(date, originalTime),
                AttendanceStatus.judgeStatus(date, modifiedTime)
        );
    }

    // 기능 4. 제적 위험자 확인
    public List<CrewPenaltyResponse> checkPenaltyCrew() {
        List<CrewPenaltyResponse> crewPenaltyResponses = new ArrayList<>();

        for (Crew crew : crews) {
            List<AttendanceRecordResponse> attendanceRecords = crew.getAttendanceRecords();
            TotalRecordsResponse totalRecords = TotalRecordsResponse.fromAttendanceRecords(attendanceRecords);

            int penaltyCount = getPenaltyCount(totalRecords);

            crewPenaltyResponses.add(
                    new CrewPenaltyResponse(
                            crew.getName(),
                            totalRecords.absentCount(),
                            totalRecords.lateCount(),
                            PenaltyStatus.getByPenaltyCount(penaltyCount)
                    )
            );
        }

        return crewPenaltyResponses;
    }

    public int getPenaltyCount(TotalRecordsResponse totalRecords) {
        return totalRecords.absentCount() + (totalRecords.lateCount() / 3);
    }
}