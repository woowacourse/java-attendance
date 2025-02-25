package view;

import static domain.AttendanceStatus.ABSENT;
import static domain.AttendanceStatus.LATE;

import constant.AbsentPenalty;
import domain.AllCrew;
import domain.Attendance;
import domain.AttendanceUpdateResult;
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


    public void printAllDangerousCrew(AllCrew allCrew) {
        System.out.println("\n제적 위험자 조회 결과");
        allCrew.sortAllCrewOrderByWarningInfo();
        List<Crew> allWarningCrew = allCrew.getAllWarningCrew();
        for (Crew crew : allWarningCrew) {
            System.out.print("- ");
            printWarningInfo(crew);
            System.out.println();
        }
    }

    public void printWarningInfo(Crew crew) {
        String nameAndCountFormat = crew.getName() + ": " + ABSENT.getStringValue() + " " + crew.getAbsentCount() + "회, " + LATE.getStringValue() + " " + crew.getLateCount() + "회 ";
        AbsentPenalty absentPenalty = crew.getAbsentPenalty();
        if (absentPenalty == AbsentPenalty.NONE) {
            System.out.println(nameAndCountFormat);
            return;
        }
        System.out.println(nameAndCountFormat + "(" + absentPenalty.getPenalty() + ")");
    }

    public void printAttendanceHistory(AllCrew allCrew, String name) {
        Crew crew = allCrew.findCrewByName(name);
        System.out.println(getAttendanceHistory(crew));
    }

    public String getAttendanceHistory(Crew crew) {
        return getFormatedAttendanceInfo(crew) + "\n"
                + getFormatedAttendanceStateInfo(crew) + "\n"
                + getFormatedWarningStatus(crew) + "\n";
    }

    public String getFormatedWarningStatus(Crew crew) {
        AbsentPenalty absentPenalty = crew.getAbsentPenalty();
        if (absentPenalty == AbsentPenalty.NONE) {
            return "";
        }
        return absentPenalty.getPenalty() + " 대상자입니다.";
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
        String state = attendance.getStatusValue();
        if (state.equals("결석")) {
            return "--:-- " + "(" + state + ")";
        }
        return attendance.getDateAndTime().format(DateTimeFormatter.ofPattern("HH:mm ", Locale.KOREAN)) + "("
                + attendance.getStatus() + ")";
    }

    public void printModifyAttendance(AllCrew allCrew, String name, LocalDateTime dateTime) {
        AttendanceUpdateResult attendanceUpdateResult = allCrew.modifyCrewAttendanceByName(name, dateTime);
        Attendance oldAttendance = attendanceUpdateResult.getOldAttendance();
        Attendance newAttendance = attendanceUpdateResult.getNewAttendance();
        System.out.println("\n" + getFormatedModifiedAttendance(oldAttendance, newAttendance));
    }

    private String getFormatedModifiedAttendance(Attendance oldAttendance, Attendance newAttendance) {
        return getFormattedAttended(oldAttendance)
                + " -> "
                + getFormattedAttended(newAttendance)
                + " 수정 완료!";
    }
}

