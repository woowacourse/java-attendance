package domain;

import static constants.NumberConstants.END_DAY_OF_DECEMBER;
import static constants.NumberConstants.LATE_TO_ABSENCE_CONVERSION_CRITERIA;
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
import utils.TimeUtils;
import view.ErrorCode;

public class AttendanceBook {
    private final List<Crew> crews;

    public AttendanceBook() {
        this.crews = new ArrayList<>();
    }

    public void addNewCrew(Crew newCrew) {
        crews.add(newCrew);
    }

    // 데이터 유효성 검사
    public void validateNameAlreadyExists(String name) {
        if (!checkCrewAlreadyExists(name)) {
            throw new IllegalArgumentException(ErrorCode.NICKNAME_NOT_FOUND.getFormat());
        }
    }

    public void validateDateAlreadyExistsByCrewName(String name, LocalDate date) {
        Crew foundCrew = findCrewByName(name);
        foundCrew.validateDateAlreadyExists(date);
    }

    public void validateIsInOperationHour(LocalTime time) {
        if (!time.isAfter(OPERATION_TIME_START) || !time.isBefore(OPERATION_TIME_END)) {
            throw new IllegalArgumentException(ErrorCode.TIME_NOT_IN_OPERATION_HOUR.getFormat());
        }
    }

    // 출석부 초기화: csv 파일 데이터를 적용
    public void initialize(String name, Map<LocalDate, LocalTime> dateAndTime) {
        if (!checkCrewAlreadyExists(name)) {
            addNewCrew(Crew.createByName(name));
        }
        Crew crew = findCrewByName(name);
        crew.addDailyAttendance(dateAndTime);
    }

    public boolean checkCrewAlreadyExists(String name) {
        return crews.stream()
                .anyMatch(crew -> crew.matchesName(name));
    }

    // 기능 1. 출석 확인
    public AttendanceRecordResponse checkAttendance(String name, Map<LocalDate, LocalTime> dateAndTime) {
        LocalDate date = TimeUtils.getDateFromDateAndTime(dateAndTime);
        Calendar.validateIsWorkingDay(date.getDayOfMonth());

        LocalTime time = TimeUtils.getTimeFromDateAndTime(dateAndTime);
        validateIsInOperationHour(time);

        findCrewByName(name).addDailyAttendance(dateAndTime);
        return new AttendanceRecordResponse(date, time, AttendanceStatus.judgeStatus(date, time));
    }

    // 기능 2. 출석 수정
    public ModifyAttendanceResponse modifyAttendance(String name, Map<LocalDate, LocalTime> dateAndTimeToModify) {
        validateNameAlreadyExists(name);

        LocalDate date = TimeUtils.getDateFromDateAndTime(dateAndTimeToModify);
        validateDateAlreadyExistsByCrewName(name, date);

        LocalTime modifiedTime = TimeUtils.getTimeFromDateAndTime(dateAndTimeToModify);
        validateIsInOperationHour(modifiedTime);

        Crew foundCrew = findCrewByName(name);
        LocalTime originalTime = foundCrew.getTimeByDate(date);
        foundCrew.modifyDailyAttendance(dateAndTimeToModify);

        return new ModifyAttendanceResponse(
                date, originalTime, modifiedTime,
                AttendanceStatus.judgeStatus(date, originalTime),
                AttendanceStatus.judgeStatus(date, modifiedTime)
        );
    }

    // 기능 3. 크루별 출석 기록 확인
    public List<AttendanceRecordResponse> checkAttendanceHistoryByCrew(String name) {
        return findCrewByName(name).getAttendanceRecords();
    }

    public TotalRecordsResponse checkAttendanceCountByCrew(List<AttendanceRecordResponse> records) {
        List<AttendanceStatus> statuses = records.stream().map(AttendanceRecordResponse::attendanceStatus).toList();
        int attendanceCount = 0;
        int lateCount = 0;
        int absentCount = 0;

        for (AttendanceStatus status : statuses) {
            if (status == AttendanceStatus.ATTEND) {
                attendanceCount++;
            }
            if (status == AttendanceStatus.LATE) {
                lateCount++;
            }
            absentCount = END_DAY_OF_DECEMBER - lateCount - attendanceCount;
        }

        return new TotalRecordsResponse(attendanceCount, lateCount, absentCount);
    }

    // 기능 4. 제적 위험자 확인
    public List<CrewPenaltyResponse> checkPenaltyCrew() {
        List<CrewPenaltyResponse> crewPenaltyResponses = new ArrayList<>();

        for (Crew crew : crews) {
            List<AttendanceRecordResponse> attendanceRecords = crew.getAttendanceRecords();
            TotalRecordsResponse totalRecords = checkAttendanceCountByCrew(attendanceRecords);

            int penaltyCount = calculatePenaltyCount(totalRecords);

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

    public int calculatePenaltyCount(TotalRecordsResponse totalRecords) {
        return totalRecords.absentCount() + (totalRecords.lateCount() / LATE_TO_ABSENCE_CONVERSION_CRITERIA);
    }

    // 보조 메서
    private Crew findCrewByName(String name) {
        return crews.stream()
                .filter(crew -> crew.matchesName(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.NICKNAME_NOT_FOUND.getFormat()));
    }
}