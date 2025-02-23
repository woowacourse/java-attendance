package domain;

import static domain.AttendanceStatus.OPERATION_HOUR_END;
import static domain.AttendanceStatus.OPERATION_HOUR_START;

import dto.AttendanceRecordResponse;
import dto.CrewPenaltyResponse;
import dto.ModifyAttendanceResponse;
import dto.TotalRecordsResponse;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import util.Parser;
import util.Parser.NameParsedData;

public class AttendanceBook {
    private static final int LATE_TO_ABSENT_COUNT_UNIT = 3;
    private final List<Crew> crews;

    public AttendanceBook() {
        this.crews = new ArrayList<>();
    }

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

    public boolean checkCrewAlreadyExists(String name) {
        boolean result = false;

        for (Crew crew : crews) {
            if (crew.hasName(name)) {
                result = true;
            }
        }

        return result;
    }

    public void validateDateAlreadyExistsByCrewName(String name, LocalDate date) {
        Crew foundCrew = getCrewByName(name);
        foundCrew.validateDateAlreadyExists(date);
    }

    public void addNewCrew(Crew newCrew) {
        crews.add(newCrew);
    }

    public void addDailyAttendanceByName(String name, Map<LocalDate, LocalTime> dateAndTime) {
        Crew suitableCrew = getCrewByName(name);
        suitableCrew.addDailyAttendance(dateAndTime);
    }

    public Crew getCrewByName(String name) {
        return crews.stream()
                .filter(crew -> crew.hasName(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.NICKNAME_NOT_FOUND.getMessage()));
    }

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

    public int getPenaltyCount(TotalRecordsResponse totalRecords) {
        return totalRecords.absentCount() + (totalRecords.lateCount() / LATE_TO_ABSENT_COUNT_UNIT);
    }

    public void validateIsInOperationHour(LocalTime time) {
        if (!time.isAfter(OPERATION_HOUR_START) || !time.isBefore(OPERATION_HOUR_END)) {
            throw new IllegalArgumentException(ErrorCode.TIME_NOT_IN_OPERATION_HOUR.getMessage());
        }
    }

    public void validateNameAlreadyExists(String name) {
        if (!checkCrewAlreadyExists(name)) {
            throw new IllegalArgumentException(ErrorCode.NICKNAME_NOT_FOUND.getMessage());
        }
    }

    public void initializeAttendanceBook(List<NameParsedData> seperatedData) {
        for (NameParsedData data : seperatedData) {
            String name = data.namePart();
            Map<LocalDate, LocalTime> dateAndTime = Parser.parseDate(data.dateTimePart());
            initializeCrew(name, dateAndTime);
        }
    }

    private void initializeCrew(String name, Map<LocalDate, LocalTime> dateAndTime) {
        if (!checkCrewAlreadyExists(name)) {
            addNewCrew(Crew.createByName(name));
        }
        addDailyAttendanceByName(name, dateAndTime);
    }
}
