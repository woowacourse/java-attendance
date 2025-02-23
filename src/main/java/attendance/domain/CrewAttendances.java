package attendance.domain;

import attendance.dto.*;
import attendance.view.FileLineReader;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CrewAttendances {

    private static final DateTimeFormatter FILE_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public static final String attendanceFilePath = "src/main/resources/";
    public static final String attendanceFileName = "attendances.csv";
    public static final String attendanceFileDelimiter = ",";
    private final Map<Crew, Attendances> crewAttendances;

    public CrewAttendances() {
        crewAttendances = new HashMap<>();
        initializeCrewAttendances();
    }

    private void initializeCrewAttendances() {
        List<String> firstSkippedLines = readAttendanceFileLinesWithoutFirstLine();
        Map<Crew, List<LocalDateTime>> crewAttendanceDateTimes = createAttendanceDateTimes(firstSkippedLines);
        createCrewAttendances(crewAttendanceDateTimes);
    }

    private List<String> readAttendanceFileLinesWithoutFirstLine() {
        FileLineReader fileLineReader = new FileLineReader();
        List<String> lines = fileLineReader.readAllLines(attendanceFilePath, attendanceFileName);
        return lines.stream()
                .skip(1L)
                .toList();
    }

    private Map<Crew, List<LocalDateTime>> createAttendanceDateTimes(final List<String> firstSkippedLines) {
        Map<Crew, List<LocalDateTime>> crewAttendanceDateTimes = new HashMap<>();
        for (String line : firstSkippedLines) {
            String[] tokens = line.split(attendanceFileDelimiter);
            Crew crew = new Crew(tokens[0]);
            LocalDateTime attendanceDateTime = LocalDateTime.parse(tokens[1], FILE_DATE_TIME_FORMATTER);
            crewAttendanceDateTimes.computeIfAbsent(crew, value -> new ArrayList<>()).add(attendanceDateTime);
        }
        return crewAttendanceDateTimes;
    }

    private void createCrewAttendances(final Map<Crew, List<LocalDateTime>> crewAttendanceDateTimes) {
        for (Crew crew : crewAttendanceDateTimes.keySet()) {
            Attendances attendances = new Attendances(crewAttendanceDateTimes.get(crew), LocalDateTime.now());
            crewAttendances.put(crew, attendances);
        }
    }

    public ConfirmAttendanceDto saveTodayAttendance(final Crew crew, final LocalTime time) {
        LocalDate today = LocalDate.now();
        Attendances attendances = crewAttendances.get(crew);
        validateDuplicateAttendance(attendances, today);
        AttendanceDate attendanceDate = new AttendanceDate(today);
        AttendanceTime attendanceTime = new AttendanceTime(time);
        Attendance attendance = new Attendance(attendanceDate, attendanceTime);
        attendances.addAttendance(attendance);
        return new ConfirmAttendanceDto(attendance);
    }

    private static void validateDuplicateAttendance(Attendances attendances, LocalDate today) {
        if (attendances.existsByLocalDate(today)) {
            throw new IllegalStateException("이미 해당 날짜에 출석했습니다.");
        }
    }

    public ChangeAttendanceDto changeAttendanceTime(Crew crew, LocalDate changeDate, LocalTime changeTime) {
        Attendances attendances = crewAttendances.get(crew);
        Attendance originAttendance = attendances.findAttendanceByLocalDate(changeDate);
        Attendance newAttendance = originAttendance.changeAttendanceTime(changeTime);
        attendances.remove(originAttendance);
        attendances.addAttendance(newAttendance);
        return new ChangeAttendanceDto(originAttendance, newAttendance);
    }

    public CheckCrewAttendanceRecordsDto checkCrewAttendanceRecords(Crew crew) {
        List<LocalDateTime> attendanceDateTimes = crewAttendances.get(crew).getAttendances().stream()
                .map(Attendance::getAttendanceDateTime)
                .toList();
        List<AttendanceStatus> attendanceStatuses = attendanceDateTimes.stream()
                .map(dateTime -> AttendanceStatus.findByAttendanceDateAndTime(new AttendanceDate(dateTime.toLocalDate()),
                        new AttendanceTime(dateTime.toLocalTime())))
                .toList();
        return new CheckCrewAttendanceRecordsDto(attendanceDateTimes, attendanceStatuses);
    }

    public CheckAttendanceStatusDto checkAttendanceStatus(Crew crew) {
        Map<String, Integer> statusCount = crewAttendances.get(crew).calculateStatusCount();
        return new CheckAttendanceStatusDto(statusCount);
    }

    public CheckExpulsionStatusDto checkExpulsionStatus(Crew crew) {
        ExpulsionStatus expulsionStatus = crewAttendances.get(crew).calculateExpulsionStatus();
        return new CheckExpulsionStatusDto(expulsionStatus);
    }
}
