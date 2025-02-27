package domain;

import dto.AttendanceRecordDTO;
import dto.CheckAttendanceResponse;
import dto.GetAttendanceRecordsResponse;
import dto.ModifyAttendanceResponse;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import util.Parser;

public class Crew {
    public static final int SYSTEM_YEAR = 2024;
    public static final int SYSTEM_MONTH = 12;
    public static final int DECEMBER_DAYS_END = 31;
    public static final int DECEMBER_DAYS_START = 1;

    private final String name;
    private final Map<LocalDate, LocalTime> attendanceRecords = new HashMap<>();

    public Crew(String name) {
        this.name = name;
    }

    public CheckAttendanceResponse checkAttendance(LocalDate date, LocalTime time) {
        validateNoDuplicateAttendance(date);
        attendanceRecords.put(date, time);

        return new CheckAttendanceResponse(
                Parser.parseDateInKorean(date), Parser.parseTimeToString(time),
                AttendanceStatus.findMessageByAttendDateAndTime(date, time)
        );
    }

    private void validateNoDuplicateAttendance(LocalDate input) {
        if (attendanceRecords.containsKey(input)) {
            throw new IllegalArgumentException(ErrorCode.ATTENDANCE_DATE_DUPLICATED.getMessage());
        }
    }

    public ModifyAttendanceResponse modifyAttendance(LocalDate date, LocalTime modifiedTime) {
        LocalTime originalTime = attendanceRecords.get(date);
        attendanceRecords.put(date, modifiedTime);

        return new ModifyAttendanceResponse(
                Parser.parseDateInKorean(date),
                Parser.parseTimeToString(originalTime),
                Parser.parseTimeToString(modifiedTime),
                AttendanceStatus.findMessageByAttendDateAndTime(date, originalTime),
                AttendanceStatus.findMessageByAttendDateAndTime(date, modifiedTime)
        );
    }

    public GetAttendanceRecordsResponse getAttendanceRecords() {
        List<AttendanceRecordDTO> attendanceRecordDTOs = new ArrayList<>();

        IntStream.rangeClosed(DECEMBER_DAYS_START, DECEMBER_DAYS_END)
                .forEach(day -> attendanceRecordDTOs.add(
                        getAttendanceRecordDTO(LocalDate.of(SYSTEM_YEAR, SYSTEM_MONTH, day))
                ));

        return new GetAttendanceRecordsResponse(attendanceRecordDTOs);
    }

    private AttendanceRecordDTO getAttendanceRecordDTO(LocalDate date) {
        String time = null;
        String status = AttendanceStatus.NONE.getMessage();

        if (attendanceRecords.containsKey(date)) {
            LocalTime localTime = attendanceRecords.get(date);
            time = Parser.parseTimeToString(localTime);
            status = AttendanceStatus.findMessageByAttendDateAndTime(date, localTime);
        }

        return new AttendanceRecordDTO(
                Parser.parseDateInKorean(date),
                time,
                status
        );
    }

    public int countAttendanceStatus(AttendanceStatus targetStatus) {
        return (int) IntStream.rangeClosed(DECEMBER_DAYS_START, DECEMBER_DAYS_END)
                .mapToObj(this::getAttendanceStatusByDay)
                .filter(status -> status == targetStatus)
                .count();
    }

    private AttendanceStatus getAttendanceStatusByDay(int day) {
        LocalDate date = LocalDate.of(SYSTEM_YEAR, SYSTEM_MONTH, day);
        if (attendanceRecords.containsKey(date)) {
            return AttendanceStatus.findByAttendDateAndTime(date, attendanceRecords.get(date));
        }
        return AttendanceStatus.ABSENT;
    }

    public boolean isName(String input) {
        return name.equals(input);
    }
}
