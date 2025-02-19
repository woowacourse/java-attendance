package domain;

import constants.DateConstants;
import exception.DuplicateAttendanceException;
import service.dto.AttendanceHistoryResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class AttendanceBook {
    private final Map<Integer, Attendance> attendances; // key: 몇 일, value: 출석 시간

    public AttendanceBook() {
        this.attendances = new HashMap<>();
    }

//    public String getStatusAt(int date) {
//        Attendance attendance = attendances.get(date);
//        return attendance.getStatus();
//    }

    public Attendance create(int date, int hour, int minute) {
        if (attendances.containsKey(date)) {
            throw new DuplicateAttendanceException();
        }
        Attendance attendance = new Attendance(
                LocalDateTime.of(DateConstants.YEAR, DateConstants.MONTH.getValue(), date, hour, minute)
        );
        attendances.put(date, attendance);
        return attendance;
    }

    public Optional<Attendance> findAttendanceByDate(int date) {
        if (attendances.containsKey(date)) {
            return Optional.of(attendances.get(date));
        }
        return Optional.empty();
    }

    public void replace(Attendance beforeAttendance, Attendance afterAttendance) {
        int date = beforeAttendance.getTime().getDayOfMonth();
        attendances.replace(date, beforeAttendance, afterAttendance);
    }

    public List<AttendanceHistoryResponse> getAllAttendance(LocalDate limitDate) {
        List<AttendanceHistoryResponse> histories = new ArrayList<>();
        for (int date = 1; date < limitDate.getDayOfMonth(); date++) {
            if (DateConstants.MONTH.isHoliday(date)) {
                continue;
            }
            if (attendances.containsKey(date)) {
                Attendance attendance = attendances.get(date);
                histories.add(new AttendanceHistoryResponse(
                        attendance.getTime().toLocalDate(),
                        Optional.of(attendance.getTime().toLocalTime()),
                        attendance.getStatus())
                );
            }
            else {
                histories.add(new AttendanceHistoryResponse(
                        LocalDate.of(DateConstants.YEAR, DateConstants.MONTH.getValue(), date),
                        Optional.empty(),
                        "결석")
                );
            }
        }
        return histories;
    }

    public Map<String, Integer> calculateAttendanceResult(LocalDate limitDate) {
        Map<String, Integer> result = new HashMap<>();
        result.put("출석", 0);
        result.put("지각", 0);
        result.put("결석", 0);
        for (int date = 1; date < limitDate.getDayOfMonth(); date++) {
            if (DateConstants.MONTH.isHoliday(date)) {
                continue;
            }
            if (attendances.containsKey(date)) {
                Attendance attendance = attendances.get(date);
                String status = attendance.getStatus();
                result.replace(status, result.get(status) + 1);
            }
            else {
                result.replace("결석", result.get("결석") + 1);
            }
        }
        return result;
    }
}
