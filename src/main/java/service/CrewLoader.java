package service;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_TIME;

import domain.Attendance;
import domain.Attendances;
import domain.CrewGroup;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import util.DayConverter;
import util.FileReader;

public class CrewLoader {
    private static final LocalTime ABSENT_TIME = LocalTime.of(23, 59);
    public static final String NAME_DELIMITER = ",";
    public static final int NAME_INDEX = 0;
    public static final int ATTENDANCE_INDEX = 1;
    public static final int DATE_INDEX = 0;
    public static final int TIME_INDEX = 1;

    public CrewGroup load(LocalDate today) {
        List<String> rawCrewsInformation = FileReader.readFile();
        CrewGroup crewGroup = new CrewGroup();
        Map<String, List<Attendance>> rawCrewGroup = makeCrewGroup(rawCrewsInformation);
        updateAbsent(today, rawCrewGroup, crewGroup);
        return crewGroup;
    }

    private void updateAbsent(LocalDate today, Map<String, List<Attendance>> rawCrewGroup, CrewGroup crewGroup) {
        for (String name : rawCrewGroup.keySet()) {
            List<LocalDate> presentDate = rawCrewGroup.get(name).stream()
                    .map(attendance -> attendance.getAttendanceTime().toLocalDate())
                    .distinct()
                    .toList();

            List<LocalDate> allDate = DayConverter.getUntilToday(today);
            Set<LocalDate> absentDates = allDate.stream()
                    .filter(date -> !presentDate.contains(date))
                    .collect(Collectors.toSet());

            List<Attendance> absentAttendances = absentDates.stream()
                    .map(absentDate -> new Attendance(LocalDateTime.of(absentDate, ABSENT_TIME)))
                    .toList();

            rawCrewGroup.get(name).addAll(absentAttendances);
            crewGroup.add(name, new Attendances(rawCrewGroup.get(name)));
        }
    }

    private Map<String, List<Attendance>> makeCrewGroup(List<String> rawCrewsInformation) {
        Map<String, List<Attendance>> rawCrewGroup = new HashMap<>();
        for (String rawCrewInformation : rawCrewsInformation) {
            List<String> splittedInfomation = splitTextByDelimiter(rawCrewInformation, NAME_DELIMITER);
            String name = splittedInfomation.get(NAME_INDEX);
            String rawAttendance = splittedInfomation.get(ATTENDANCE_INDEX);

            List<String> splittedAttendance = splitTextByDelimiter(rawAttendance, " ");
            String rawDate = splittedAttendance.get(DATE_INDEX);
            String rawTime = splittedAttendance.get(TIME_INDEX);

            LocalDate localDate = LocalDate.parse(rawDate, ISO_LOCAL_DATE);
            LocalTime localTime = LocalTime.parse(rawTime, ISO_LOCAL_TIME);

            Attendance attendance = new Attendance(LocalDateTime.of(localDate, localTime));
            rawCrewGroup.computeIfAbsent(name, k -> new ArrayList<>()).add(attendance);
        }
        return rawCrewGroup;
    }

    private List<String> splitTextByDelimiter(String text, String delimiter) {
        String[] splittedText = text.split(delimiter);
        return Arrays.stream(splittedText).toList();
    }
}
