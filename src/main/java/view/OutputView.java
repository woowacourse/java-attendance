package view;

import static domain.AttendanceStatus.ABSENT;
import static domain.AttendanceStatus.LATE;

import domain.AbsentPenalty;
import domain.Attendance;
import domain.Crew;
import java.time.LocalDate;
import java.util.List;

public class OutputView {


    public OutputView() {

    }

    public void printAllDangerousCrew(List<Crew> allAbsentPenaltyReceivedCrew) {
        System.out.println("\n제적 위험자 조회 결과");
        for (Crew crew : allAbsentPenaltyReceivedCrew) {
            System.out.print("- ");
            printAbsentPenaltyReceivedInfo(crew);
            System.out.println();
        }
    }

    public void printAbsentPenaltyReceivedInfo(Crew crew) {
        String nameAndCountFormat = crew.getName() + ": " + ABSENT.getStringValue() + " " + crew.getAbsentCount() + "회, " + LATE.getStringValue() + " " + crew.getLateCount() + "회 ";
        AbsentPenalty absentPenalty = crew.getAbsentPenalty();
        if (absentPenalty == AbsentPenalty.NONE) {
            System.out.println(nameAndCountFormat);
            return;
        }
        System.out.println(nameAndCountFormat + "(" + absentPenalty.getPenalty() + ")");
    }

    public void printAttendanceHistory(Crew crew) {
        System.out.println(crew.getFormatedAttendanceInfo() + "\n"
                + crew.getFormatedAttendanceStateInfo() + "\n"
                + crew.getFormatedWarningStatus() + "\n");
    }

    public void printCheckedAttendance(Attendance attendance) {
        System.out.println(attendance.getFormattedAttended());
    }


    public void printModifyAttendance(Attendance oldAttendance,Attendance newAttendance) {
        System.out.println("\n" + getFormatedModifiedAttendance(oldAttendance, newAttendance));
    }

    private String getFormatedModifiedAttendance(Attendance oldAttendance, Attendance newAttendance) {
        return oldAttendance.getFormattedAttended()
                + " -> "
                + newAttendance.getFormattedAttended()
                + " 수정 완료!";
    }
}

