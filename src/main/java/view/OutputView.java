package view;

import domain.AttendTime;
import domain.Crew;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

public class OutputView {

    public void printAttendanceResult(AttendTime attendTime) {
        LocalDate localDate = attendTime.getLocalDate();
        LocalTime localTime = attendTime.getLocalTime();
        System.out.println();
        System.out.printf("%02d월 %02d일 %s %02d:%02d (%s)", localDate.getMonthValue(), localDate.getDayOfMonth(), localDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN), localTime.getHour(), localTime.getMinute(), attendTime.getAttendanceStatus());
    }

    public void printAttendanceTimeLine(Crew crew) {
        System.out.println();
        System.out.println("이번 달 " + crew.getNickname() + "의 출석 기록입니다.");

        for (AttendTime attendTime : crew.getAttendTimeLine()) {
            LocalDate localDate = attendTime.getLocalDate();
            LocalTime localTime = attendTime.getLocalTime();
            System.out.printf("%02d월 %02d일 %s ", localDate.getMonthValue(), localDate.getDayOfMonth(), localDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN));
            if (localTime != null) {
                System.out.printf("%02d:%02d (%s)\n", localTime.getHour(), localTime.getMinute(), attendTime.getAttendanceStatus());
                continue;
            }
            System.out.printf("--:-- (결석)\n");

        }

        System.out.println();
        System.out.println("출석: " + crew.getCrewAttendedCount() + "회");
        System.out.println("지각: " + crew.getCrewLateCount() + "회");
        System.out.println("결석: " + crew.getCrewAbsentCount() + "회");
        System.out.println();

        if (crew.isDismissalCrew())
            System.out.println(crew.getDismissalStatus().getTarget() + " 대상자입니다.");
    }

    public void printDismissalCrews(List<Crew> dismissalCrewsByImportance) {
        System.out.println(" 제적 위험자 조회 결과");
        dismissalCrewsByImportance.stream().forEach(crew -> System.out.println("- "+crew.getNickname()+": 결석 "+crew.getCrewAbsentCount()+", 지각 "+crew.getCrewLateCount()+"회 ("+crew.getDismissalStatus().getTarget()+")"));
    }

    public void printBeforeChangedAttendance(Crew crew, int date) {
        AttendTime attendTime = crew.findAttendanceByDate(date);
        LocalDate localDate = attendTime.getLocalDate();
        LocalTime localTime = attendTime.getLocalTime();
        System.out.printf("%02d월 %02d일 %s %02d:%02d (%s) -> ", localDate.getMonthValue(), localDate.getDayOfMonth(), localDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN), localTime.getHour(), localTime.getMinute(), attendTime.getAttendanceStatus());
    }

    public void printAfterChangedAttendance(AttendTime attendTime) {
        System.out.printf("%02d:%02d (%s) 수정 완료!", attendTime.getLocalTime().getHour(), attendTime.getLocalTime().getMinute(), attendTime.getAttendanceStatus());
    }
}
