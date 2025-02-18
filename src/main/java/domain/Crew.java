package domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Crew {
    // 이름 - 출석 정보
    private final String name;
    private final List<Attendance> attendanceInfo;

    public Crew(String name) {
        this.name = name;
        this.attendanceInfo = new ArrayList<>();
    }

    public Attendance addAttendance(LocalDateTime localDateTime) {
        Attendance isAlreadyExisted = getAlreadyExistAttendance(localDateTime);
        if (isAlreadyExisted != null) {
            throw new IllegalArgumentException("이미 출첵 완");
        }
        Attendance attendance = new Attendance(localDateTime);
        attendanceInfo.add(attendance);
        return attendance;
    }

    private Attendance getAlreadyExistAttendance(LocalDateTime localDateTime) {
        Optional<Attendance> sameDateAttendance = attendanceInfo.stream().filter(attendance ->
                attendance.isEqualDate(localDateTime)
        ).findFirst();
        if (sameDateAttendance.isPresent()) {
            return sameDateAttendance.get();
        }
        return null;
    }

    public String update(LocalDateTime newDateAndTime) {
        Attendance existAttendance = getAlreadyExistAttendance(newDateAndTime);
        if (existAttendance == null) {
            throw new IllegalArgumentException("출석 기록 없음");
        }
        attendanceInfo.remove(existAttendance);
        Attendance attendance = new Attendance(newDateAndTime);
        attendanceInfo.add(attendance);
        String str = "";
        str += existAttendance.printAttendance();
        str += " -> ";
        str += attendance.getFormattedTimeAndState();
        str += " 수정 완료!";
        return str;
    }

}
