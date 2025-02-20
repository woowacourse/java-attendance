package view;

import domain.AttendanceStatus;
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

        // TODO: 출석, 지각 결석 및 제적 위험자 상태 출력
//        System.out.printf("\n%s: %d회\n", AttendanceStatus.ATTENDANCE.getStatus());
    }

    private static String getEachDateAttendanceMessage(LocalDate currentDate, Map<LocalDate, LocalTime> map) {
        if (!map.containsKey(currentDate)) {
            return String.format("%d월 %2d일 %s %s (%s)\n", currentDate.getMonthValue(), currentDate.getDayOfMonth(),
                    ViewUtil.getDayOfWeekToMessage(currentDate.getDayOfWeek()), ViewUtil.getNoneAttendanceMessage(), AttendanceStatus.ABSENCE.getStatus());
        }
        LocalTime attendTime = map.get(currentDate);
        return String.format("%d월 %2d일 %s %s (%s)\n", currentDate.getMonthValue(), currentDate.getDayOfMonth(),
                ViewUtil.getDayOfWeekToMessage(currentDate.getDayOfWeek()), attendTime, AttendanceStatus.attend(DateUtil.assembleDateAndTime(currentDate, attendTime)));
    }

    public void printErrorMessage(Exception e) {
        System.out.println("[ERROR] " + e.getMessage());
    }
}
