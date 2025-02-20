import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AttendanceBook {
    private final List<Crew> crews;
    public List<CrewPenaltyResponse> crewPenaltyResponses;

    public AttendanceBook() {
        this.crews = new ArrayList<>();
    }

    public List<CrewPenaltyResponse> checkPenaltyCrew() {
        List<CrewPenaltyResponse> crewPenaltyResponses = new ArrayList<>();

        for (Crew crew : crews) {
            List<AttendanceRecordsResponse> attendanceRecords = crew.getAttendanceRecords();
            TotalRecordsResponse totalRecords = TotalRecordsResponse.fromAttendanceRecords(attendanceRecords);

            int penaltyCount = getPenaltyCount(totalRecords);
            String penalty = "";

            if (penaltyCount > 5) {
                penalty = "(제적)";
            }
            if (penaltyCount >= 3) {
                penalty = "(면담)";
            }
            if (penaltyCount >= 2) {
                penalty = "(경고)";
            }
            crewPenaltyResponses.add(new CrewPenaltyResponse(
                    crew.getName(),
                    totalRecords.absentCount(),
                    totalRecords.lateCount(),
                    penalty)
            );
        }

        return crewPenaltyResponses;
    }

    public boolean checkAlreadyExists(String name) {
        boolean result = false;

        for (Crew crew : crews) {
            if (crew.hasName(name)) {
                result = true;
            }
        }

        return result;
    }

    public void initialize(String name, Map<LocalDate, LocalTime> dateAndTime) {
        if (!checkAlreadyExists(name)) {
            addNewCrew(Crew.createByName(name));
        }
        addDailyAttendanceByName(name, dateAndTime);
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
                .orElseThrow();
    }

    public void checkAttendance(String name, Map<LocalDate, LocalTime> dateAndTime) {
        Crew foundCrew = crews.stream()
                .filter(crew -> crew.hasName(name))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 등록되지 않은 닉네임입니다."));

        LocalDate date = dateAndTime.keySet().stream()
                .findAny()
                .orElseThrow();

        Calendar.validateIsWorkingDay(date.getDayOfMonth());

        LocalTime time = dateAndTime.values().stream()
                .findAny()
                .orElseThrow();

        validateIsInOperationHour(time);

        foundCrew.addDailyAttendance(dateAndTime);
    }

    public void modifyAttendance(String name, Map<LocalDate, LocalTime> dateAndTime) {
        Crew foundCrew = crews.stream()
                .filter(crew -> crew.hasName(name))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 등록되지 않은 닉네임입니다."));

        LocalTime time = dateAndTime.values().stream()
                .findAny()
                .orElseThrow();

        validateIsInOperationHour(time);

        foundCrew.modifyDailyAttendance(dateAndTime);
    }

    private int getPenaltyCount(TotalRecordsResponse totalRecords) {
        return totalRecords.absentCount() + (totalRecords.lateCount() / 3);
    }

    public void validateIsInOperationHour(LocalTime time) {
        if (!time.isAfter(LocalTime.of(8, 0)) || !time.isBefore(LocalTime.of(23, 0))) {
            throw new IllegalArgumentException("[ERROR] 캠퍼스 운영 시간은 08:00~23:00 입니다.");
        }
    }
}