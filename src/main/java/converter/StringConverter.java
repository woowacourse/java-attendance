package converter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import model.Attendance;
import model.Attendances;
import model.Crew;
import model.Crews;

public class StringConverter {

    public Crews convertToCrews(List<String> rawAttendances) {
        List<Crew> crews = new ArrayList<>();
        for (String rawAttendance : rawAttendances) {
            String rawNickname = rawAttendance.split(",")[0];
            validateNullOrBlank(rawNickname);

            crews.add(Crew.of(rawNickname));
        }
        return Crews.of(crews);
    }

    public Attendances convertToAttendances(List<String> rawAttendances, Crews crews) {
        List<Attendance> attendances = new ArrayList<>();
        for (String rawAttendance : rawAttendances) {
            String[] attendanceInfo = rawAttendance.split(",");

            String rawNickname = attendanceInfo[0];
            validateNullOrBlank(rawNickname);
            Crew crew = crews.findByNickname(rawNickname)
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 크루입니다."));

            String rawCheckInDateTime = attendanceInfo[1];
            validateNullOrBlank(rawCheckInDateTime);
            validateLocalDateTimeFormat(rawCheckInDateTime);

            String rawCheckInDate = rawCheckInDateTime.split(" ")[0];
            String rawCheckInTime = rawCheckInDateTime.split(" ")[1] + ":00";

            LocalDateTime checkInTime = LocalDateTime.of(LocalDate.parse(rawCheckInDate),
                    LocalTime.parse(rawCheckInTime));

            attendances.add(Attendance.of(crew, checkInTime));
        }

        return Attendances.of(attendances);
    }

    public Attendance convertToAttendance(String rawNickname, String rawCheckInTime) {
        validateNullOrBlank(rawNickname);
        Crew crew = Crew.of(rawNickname);

        validateNullOrBlank(rawCheckInTime);
        validateTimeFormat(rawCheckInTime);
        LocalDateTime checkInTime = LocalDateTime.of(LocalDate.now(), LocalTime.parse(rawCheckInTime));

        return Attendance.of(crew, checkInTime);
    }

    public Crew convertToNickname(String rawNickname) {
        validateNullOrBlank(rawNickname);

        return Crew.of(rawNickname);
    }

    private void validateLocalDateTimeFormat(String dateTime) {
        String regExpression = "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}$";
        if (!dateTime.matches(regExpression)) {
            throw new IllegalArgumentException("시간 형식이 올바르지 않습니다.");
        }
    }

    private void validateTimeFormat(String time) {
        String regExpression = "^\\d{2}:\\d{2}$";
        if (!time.matches(regExpression)) {
            throw new IllegalArgumentException("시간 형식이 올바르지 않습니다.");
        }
    }

    private void validateNullOrBlank(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException("입력은 null이거나 공백일 수 없습니다.");
        }
    }
}
