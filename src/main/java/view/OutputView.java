package view;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import model.AbsentPenalty;
import model.Attendance;
import model.CrewAttendances;

public class OutputView {
    public void printAddInformation(Attendance attendance) {
        System.out.print(System.lineSeparator());
        System.out.printf("%02d월 %02d일 %s %02d:%02d (%s)%n",
                attendance.getMonth(), attendance.getDay(), getKoreanWeek(attendance.getDayOfWeek()),
                attendance.getHour(), attendance.getMinute(), getAttendStatus(attendance));
        System.out.print(System.lineSeparator());
    }

    public void printUpdateInformation(Attendance attendance, Attendance updateAttendance) {
        System.out.print(System.lineSeparator());
        System.out.printf("%02d월 %02d일 %s %02d:%02d (%s) -> ",
                attendance.getMonth(), attendance.getDay(), getKoreanWeek(attendance.getDayOfWeek()),
                attendance.getHour(), attendance.getMinute(), getAttendStatus(attendance));
        System.out.printf("%02d:%02d (%s) 수정 완료!%n",
                updateAttendance.getHour(), updateAttendance.getMinute(), getAttendStatus(updateAttendance));
        System.out.print(System.lineSeparator());
    }

    public void printCrewAttendanceRecords(CrewAttendances crewAttendances, LocalDate today) {
        System.out.print(System.lineSeparator());
        System.out.printf("이번 달 %s의 출석 기록입니다.%n", crewAttendances.getNickname());
        System.out.print(System.lineSeparator());

        for (int day = 1; day < today.getDayOfMonth(); day++) {
            LocalDate date = today.withDayOfMonth(day);
            if (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY
                    || date.isEqual(LocalDate.of(2024, 12, 25))) continue;

            System.out.printf("%02d월 %02d일 %s ", date.getMonth().getValue(), day, getKoreanWeek(date.getDayOfWeek()));
            if (crewAttendances.isAlreadyAttend(date)) {
                Attendance attendance = crewAttendances.findAttendance(date);
                System.out.printf("%02d:%02d (%s)%n", attendance.getHour(), attendance.getMinute(), getAttendStatus(attendance));
                continue;
            }

            System.out.printf("--:-- (결석)%n");
        }
        System.out.print(System.lineSeparator());

        System.out.printf("출석: %d회%n", crewAttendances.calculateAttendCountUntilDate(today));
        System.out.printf("지각: %d회%n", crewAttendances.calculateLateCountUntilDate(today));
        System.out.printf("결석: %d회%n", crewAttendances.calculateAbsentCountUntilDate(today));
        System.out.print(System.lineSeparator());
        AbsentPenalty absentPenalty = crewAttendances.determineAttendPenalty(today);
        if (absentPenalty != AbsentPenalty.NONE) {
            System.out.printf("%s 대상자입니다.%n", getAbsentPenalty(absentPenalty));
        }
        System.out.print(System.lineSeparator());
    }

    private String getKoreanWeek(DayOfWeek dayOfWeek) {
        return dayOfWeek.getDisplayName(TextStyle.FULL, Locale.KOREAN);
    }

    private String getAbsentPenalty(AbsentPenalty absentPenalty) {
        if (absentPenalty == AbsentPenalty.DISMISSAL) {
            return "제적";
        }

        if (absentPenalty == AbsentPenalty.INTERVIEW) {
            return "면담";
        }

        if (absentPenalty == AbsentPenalty.WARNING) {
            return "경고";
        }

        return "";
    }

    private String getAttendStatus(Attendance attendance) {
        if (attendance.isAbsent()) {
            return "결석";
        }

        if (attendance.isLate()) {
            return "지각";
        }

        return "출석";
    }

    public void printRiskOfDismissal(List<CrewAttendances> riskCrews, LocalDate today) {
        System.out.print(System.lineSeparator());
        System.out.println("제적 위험자 조회 결과");
        for (CrewAttendances crew : riskCrews) {
            System.out.printf("- %s: 결석 %d회, 지각 %d회 (%s)%n",
                    crew.getNickname(), crew.calculateAbsentCountUntilDate(today), crew.calculateLateCountUntilDate(today),
                    getAbsentPenalty(crew.determineAttendPenalty(today)));
        }
        System.out.print(System.lineSeparator());
    }
}
