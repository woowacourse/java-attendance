package view;

import domain.AllCrew;
import domain.Attendance;
import domain.Crew;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

public class OutputView {
    private final LocalDate today;

    public OutputView(LocalDate today) {
        this.today = today;
    }

    public void printDangerousCrew(AllCrew allCrew) {
        System.out.println("\n제적 위험자 조회 결과");
        System.out.println(allCrew.printAllCrewWarningInfo(today));
    }

    public void printAttendanceHistory(AllCrew allCrew, String name) {
        Crew crew = allCrew.getUpdatedCrew(name, today.minusDays(1));
        System.out.println(getAttendanceHistory(crew));
    }

    public String getAttendanceHistory(Crew crew) {
        return getFormatedAttendanceInfo(crew) + "\n"
                + getFormatedAttendanceStateInfo(crew) + "\n"
                + getFormatedWarningStatus(crew) + "\n";
    }

    public String getFormatedWarningStatus(Crew crew) {
        String warningStatus = crew.calculateWarningStatus(crew.getAbsentCount());
        if (warningStatus.isEmpty()) {
            return "";
        }
        return warningStatus + " 대상자입니다.";
    }


    public String getFormatedAttendanceStateInfo(Crew crew) {
        return "출석: " + crew.getAttendanceCount() + "회\n"
                + "지각: " + crew.getLateCount() + "회\n"
                + "결석: " + crew.getAbsentCount() + "회\n";
    }

    public String getFormatedAttendanceInfo(Crew crew) {
        crew.sortAttendanceInfo();
        StringBuilder formatedAttendanceInfo = new StringBuilder();
        for (Attendance attendance : crew.getAttendanceInfo()) {
            formatedAttendanceInfo.append(getFormattedAttended(attendance)).append("\n");
        }
        return formatedAttendanceInfo.toString();
    }

    public void printCheckedAttendance(Attendance attendance) {
        System.out.println(getFormattedAttended(attendance));
    }

    public String getFormattedAttended(Attendance attendance) {
        LocalDateTime dateAndTime = attendance.getDateAndTime();
        return dateAndTime.format(DateTimeFormatter.ofPattern("MM월 dd일 "))
                + dateAndTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN) + " "
                + getFormattedTimeAndState(attendance);
    }

    public String getFormattedTimeAndState(Attendance attendance) {
        String state = attendance.getState();
        if (state.equals("결석")) {
            return "--:-- " + "(" + state + ")";
        }
        return attendance.getDateAndTime().format(DateTimeFormatter.ofPattern("HH:mm ", Locale.KOREAN)) + "("
                + attendance.getState() + ")";
    }

    public void printModifyAttendance(AllCrew allCrew, String name, LocalDateTime dateTime) {
        List<Attendance> oldAndNew = allCrew.modifyCrewAttendanceByName(name, dateTime);
        Attendance oldAttendance = oldAndNew.get(0);
        Attendance newAttendance = oldAndNew.get(1);
        System.out.println("\n" + getFormatedModifiedAttendance(oldAttendance, newAttendance));
    }

    private String getFormatedModifiedAttendance(Attendance oldAttendance, Attendance newAttendance) {
        return getFormattedAttended(oldAttendance)
                + " -> "
                + getFormattedAttended(newAttendance)
                + " 수정 완료!";
    }
}

