package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
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

    public String getName() {
        return name;
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

    public String printAttendanceInfo(LocalDate lastDate) {
        updateUntil(lastDate);
        attendanceInfo.sort(Comparator.comparing(Attendance::getDayOfMonth));
        String str ="";
        for (Attendance attendance : attendanceInfo) {
            str += attendance.printAttendance() + "\n";
        }
        return str;
    }

    public String printAttendanceStateInfo(LocalDate lastDate) {
        updateUntil(lastDate);
        String str = "";
        str += "출석: " + getAttendanceCount()+"회\n";
        str += "지각: " + getLateCount()+"회\n";
        str += "결석: " + getAbsentCount()+"회\n";
        return str;
    }

    private int getAttendanceCount() {
        int count = 0;
        for (Attendance attendance : attendanceInfo) {
            if(attendance.getState().equals("(출석)")) {
                count++;
            }
        }
        return count;
    }

    private int getLateCount() {
        int count = 0;
        for (Attendance attendance : attendanceInfo) {
            if(attendance.getState().equals("(지각)")) {
                count++;
            }
        }
        return count;
    }
    private int getAbsentCount() {
        int count = 0;
        for (Attendance attendance : attendanceInfo) {
            if(attendance.getState().equals("(결석)")) {
                count++;
            }
        }
        return count;
    }

    public void updateUntil(LocalDate lastDate) {
        for(int i=1;i <= lastDate.getDayOfMonth();i++){
            DayOfWeek todayDayOfWeek = LocalDate.of(2024, 12, i).getDayOfWeek();
            if (todayDayOfWeek == DayOfWeek.SATURDAY || todayDayOfWeek == DayOfWeek.SUNDAY || i == 25) {
                continue;
            }
            if(!containsDayOfMonth(i)){
                attendanceInfo.add(new Attendance(LocalDateTime.of(2024,12,i,15,0)));
            }
        }
    }

    private boolean containsDayOfMonth(int i) {
        for (Attendance attendance : attendanceInfo) {
            if (attendance.getDayOfMonth() == i) {
                return true;
            }
        }
        return false;
    }


    public String printWarningInfo(LocalDate lastDate) {
        updateUntil(lastDate);
        int lateCount = getLateCount();
        int originalAbsentCount = getAbsentCount();
        int absentCount = originalAbsentCount + (lateCount / 3);
        String str = name + ": 결석 " + originalAbsentCount +"회, 지각 " + lateCount + "회 ";
        if(absentCount > 5) { // 5회 초과, 3회 이상, 2회 이상
            return str + "(제적)";
        }
        if(absentCount >= 3) {
            return str + "(면담)";
        }
        if(absentCount >= 2) {
            return str + "(경고)";
        }
        return str;
    }
}
