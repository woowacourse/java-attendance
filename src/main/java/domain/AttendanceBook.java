package domain;

import static util.parser.DateTimeParser.parseStringToDateTime;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class AttendanceBook {

    private static final String NOT_REGISTERED_CREW_ERROR_MESSAGE = "등록되지 않은 크루입니다.";
    private static final String ALREADY_ATTENDED_ERROR_MESSAGE = "이미 출석 기록이 있으므로 수정만 가능합니다.";
    private static final String NOT_ATTENDED_ERROR_MESSAGE = "수정전 먼저 출석을 확인을 해야합니다.";

    private final Map<String, Crew> crewRecords;

    public AttendanceBook() {
        this.crewRecords = new HashMap<>();
    }

    public int countCrew() {
        return crewRecords.size();
    }

    public Crew findCrewByName(String name) {
        // TODO: 등록된 닉네임인지 확인 추가
        return crewRecords.get(name);
    }

    public DailyRecord saveAttendanceRecord(String name, LocalDateTime dateTime) {
        validateRegisteredCrew(name);
        validateAlreadyAttended(name, dateTime);

        Crew crew = crewRecords.get(name);
        return crew.addDailyRecord(dateTime);
    }

    public DailyRecord editAttendanceRecord(String name, LocalDateTime editedDateTime) {
        validateRegisteredCrew(name);
        validateAttendedDate(name, editedDateTime);

        Crew crew = crewRecords.get(name);
        return crew.updateDailyRecord(editedDateTime);
    }

    public void initializeCrewRecords(Scanner scanner) {
        Map<String, List<LocalDateTime>> result = new HashMap<>();
        while (scanner.hasNextLine()) {
            String[] attributes = scanner.nextLine().split(",");
            String name = attributes[0];
            LocalDateTime dateTime = parseStringToDateTime(attributes[1]);

            result.putIfAbsent(name, new ArrayList<>());
            result.get(name).add(dateTime);
        }
        createCrews(result);
    }

    private void createCrews(Map<String, List<LocalDateTime>> result) {
        for (String name : result.keySet()) {
            List<LocalDateTime> dailyRecords = result.get(name);
            crewRecords.put(name, new Crew());
            crewRecords.get(name).initializeDailyRecords(dailyRecords);
        }
    }

    private void validateRegisteredCrew(String name) {
        if (!crewRecords.containsKey(name)) {
            throw new IllegalArgumentException(NOT_REGISTERED_CREW_ERROR_MESSAGE);
        }
    }

    private void validateAlreadyAttended(String name, LocalDateTime dateTime) {
        Crew crew = crewRecords.get(name);
        if (crew.hasDate(dateTime.toLocalDate())) {
            throw new IllegalArgumentException(ALREADY_ATTENDED_ERROR_MESSAGE);
        }
    }

    private void validateAttendedDate(String name, LocalDateTime editedDateTime) {
        Crew crew = crewRecords.get(name);
        if (!crew.hasDate(editedDateTime.toLocalDate())) {
            throw new IllegalArgumentException(NOT_ATTENDED_ERROR_MESSAGE);
        }
    }
}