package view;

import domain.AttendanceStatus;
import domain.RiskStatus;
import dto.CrewAttendanceStatusResponse;
import dto.CrewResponse;
import global.util.DateUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

public class OutputView {
    public void printCrewAttendanceRecord(CrewResponse crewResponse) {
        System.out.printf("이번 달 %s의 출석 기록입니다.\n", crewResponse.name());
        Map<LocalDate, LocalTime> map = crewResponse.attendanceBook();
        LocalDate currentDate = DateUtil.getFirstDateOfMonth();

        while (!currentDate.isAfter(DateUtil.TODAY.toLocalDate())) {
            if (DateUtil.isWeekday(currentDate)) {
                System.out.println(getEachDateAttendanceMessage(currentDate, map));
            }
            currentDate = currentDate.plusDays(1);
        }

        System.out.printf("\n%s: %d회\n", AttendanceStatus.ATTENDANCE.getStatus(), crewResponse.attendanceCount());
        System.out.printf("%s: %d회\n", AttendanceStatus.TARDY.getStatus(), crewResponse.tardyCount());
        System.out.printf("%s: %d회\n\n", AttendanceStatus.ABSENCE.getStatus(), crewResponse.absenceCount());

        RiskStatus riskStatus = crewResponse.riskStatus();

        if (!riskStatus.equals(RiskStatus.NONE)) {
            System.out.println(riskStatus.getStatus() + " 대상자입니다.");
        }
    }

    private static String getEachDateAttendanceMessage(LocalDate currentDate, Map<LocalDate, LocalTime> map) {
        if (!map.containsKey(currentDate)) {
            return String.format("%d월 %02d일 %s %s (%s)", currentDate.getMonthValue(), currentDate.getDayOfMonth(),
                    ViewUtil.getDayOfWeekToMessage(currentDate.getDayOfWeek()), ViewUtil.getNoneAttendanceMessage(), AttendanceStatus.ABSENCE.getStatus());
        }
        LocalTime attendTime = map.get(currentDate);
        return String.format("%d월 %02d일 %s %s (%s)", currentDate.getMonthValue(), currentDate.getDayOfMonth(),
                ViewUtil.getDayOfWeekToMessage(currentDate.getDayOfWeek()), attendTime, AttendanceStatus.attend(DateUtil.assembleDateAndTime(currentDate, attendTime)));
    }

    public void printErrorMessage(Exception e) {
        System.out.println("[ERROR] " + e.getMessage());
    }
}
