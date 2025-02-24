package attendance.domain;

import attendance.dto.*;

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
    public static final String attendanceFileDelimiter = ",";
    private final Map<Crew, Attendances> crewAttendances;

    public CrewAttendances() {
        crewAttendances = new HashMap<>();
    }

    public void initializeCrewAttendances(List<String> previousAttendanceLines) {
        Map<Crew, List<LocalDateTime>> crewAttendanceDateTimes = createAttendanceDateTimes(previousAttendanceLines);
        createCrewAttendances(crewAttendanceDateTimes);
    }

    private Map<Crew, List<LocalDateTime>> createAttendanceDateTimes(final List<String> previousAttendanceLines) {
        Map<Crew, List<LocalDateTime>> crewAttendanceDateTimes = new HashMap<>();
        for (String line : previousAttendanceLines) {
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

    public Map<String, AttendanceHistoryDto> calculateAllCrewAttendanceHistories() {
        Map<String, AttendanceHistoryDto> attendanceHistories = new HashMap<>();
        for (Map.Entry<Crew, Attendances> entry : crewAttendances.entrySet()) {
            Attendances attendances = entry.getValue();
            Map<String, Integer> attendanceStatusCounts = attendances.calculateStatusCount();
            ExpulsionStatus expulsionStatus = attendances.calculateExpulsionStatus();
            AttendanceHistoryDto attendanceHistoryDto = new AttendanceHistoryDto(
                    attendanceStatusCounts.get(AttendanceStatus.ABSENT.getText()),
                    attendanceStatusCounts.get(AttendanceStatus.LATE.getText()), expulsionStatus.getText());
            attendanceHistories.put(entry.getKey().getNickname(), attendanceHistoryDto);
        }
        return attendanceHistories;
    }

    public Crew findRegisteredCrew(final String nickname) {
        Crew crew = new Crew(nickname);
        if (!crewAttendances.containsKey(crew)) {
            throw new IllegalArgumentException("등록되지 않은 닉네임입니다.");
        }
        return crew;
    }
}
