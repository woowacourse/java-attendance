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
import util.Parser;

public class Crew {
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

        for (int day = 1; day <= 31; day++) {
            attendanceRecordDTOs.add(getAttendanceRecordDTO(LocalDate.of(2024, 12, day)));
        }

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
}
