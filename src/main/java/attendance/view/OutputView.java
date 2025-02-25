package attendance.view;

import attendance.domain.AttendanceHistory;
import attendance.domain.DateInfo;
import attendance.domain.DateInfos;
import attendance.domain.constant.AttendanceStatus;
import attendance.exception.CustomException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

public class OutputView {

    public void writeAttendanceCheck(DateInfo dateInfo) {
        String month = formatWithLeadingZero(dateInfo.getMonth());
        String day = formatWithLeadingZero(dateInfo.getDay());
        String dayOfWeek = changeDayOfWeekToKorean(dateInfo.getDayOfWeek());
        String hour = formatWithLeadingZero(dateInfo.getCampusHour());
        String minute = formatWithLeadingZero(dateInfo.getCampusMinute());
        String status = dateInfo.getAttendanceStatus().getStatus();
        System.out.printf("%s월 %s일 %s %s:%s (%s)", month, day, dayOfWeek, hour, minute, status);
        System.out.println();
    }

    public void writeAttendanceModifyCheck(int hour, int minute, AttendanceStatus status, DateInfo dateInfo) {
        String month = formatWithLeadingZero(dateInfo.getMonth());
        String day = formatWithLeadingZero(dateInfo.getDay());
        String dayOfWeek = changeDayOfWeekToKorean(dateInfo.getDayOfWeek());

        String beforeHour = formatWithLeadingZero(hour);
        String beforeMinute = formatWithLeadingZero(minute);
        String beforeStatus = status.getStatus();

        String afterHour = formatWithLeadingZero(dateInfo.getCampusHour());
        String afterMinute = formatWithLeadingZero(dateInfo.getCampusMinute());
        String afterStatus = dateInfo.getAttendanceStatus().getStatus();
        System.out.printf("%s월 %s일 %s %s:%s (%s) -> %s:%s (%s) 수정 완료!",
                month, day, dayOfWeek, beforeHour, beforeMinute, beforeStatus, afterHour, afterMinute, afterStatus);
        System.out.println();
    }

    public void writeAttendanceModifyCheck(DateInfo dateInfo) {
        String month = formatWithLeadingZero(dateInfo.getMonth());
        String day = formatWithLeadingZero(dateInfo.getDay());
        String dayOfWeek = changeDayOfWeekToKorean(dateInfo.getDayOfWeek());

        String afterHour = formatWithLeadingZero(dateInfo.getCampusHour());
        String afterMinute = formatWithLeadingZero(dateInfo.getCampusMinute());
        String afterStatus = dateInfo.getAttendanceStatus().getStatus();
        System.out.printf("%s월 %s일 %s --:-- (결석) -> %s:%s (%s) 수정 완료!",
                month, day, dayOfWeek, afterHour, afterMinute, afterStatus);
        System.out.println();
    }


    public void writeAttendanceHistory(LocalDate now, DateInfos dateInfos, AttendanceHistory history) {
        System.out.printf("이번 달 %s의 출석 기록입니다.", history.getCrewName());
        System.out.println();

        for (int day = 1; day <= now.getDayOfMonth(); day++) {
            LocalDate currentDate = LocalDate.of(now.getYear(), now.getMonthValue(), day);
            if (isWeekend(currentDate)) {
                continue;
            }
            try {
                DateInfo dateInfo = dateInfos.findDateInfoByDay(day);
                writeAttendanceCheck(dateInfo);
            } catch (CustomException e) {
                writeAbsentAttendanceCheck(currentDate);
            }
        }

        System.out.println();
        System.out.println(String.format("출석: %d회", history.getAttendanceCount()));
        System.out.println(String.format("지각: %d회", history.getLateCount()));
        System.out.println(String.format("결석: %d회", history.getAbsenceCount()));
        System.out.println(String.format("%s 대상자입니다.", history.getCrewStatus().getName()));
        System.out.println();
    }

    public void writeWarningHistories(List<AttendanceHistory> warningAttendanceHistory) {
        System.out.println("제적 위험자 조회 결과");

        warningAttendanceHistory.sort(Comparator
                .comparing(AttendanceHistory::getCrewStatus)
                .thenComparing(history -> history.getLateCount() + history.getAbsenceCount(), Comparator.reverseOrder())
                .thenComparing(AttendanceHistory::getCrewName));

        for (AttendanceHistory history : warningAttendanceHistory) {
            System.out.println(
                    String.format("- %s: 결석 %d회, 지각 %d회 (%s)", history.getCrewName(), history.getAbsenceCount(),
                            history.getLateCount(), history.getCrewStatus().getName()));
        }
    }

    public void errorMessagePrint(String message) {
        System.out.println(message);
    }

    private void writeAbsentAttendanceCheck(LocalDate localDate) {
        String month = formatWithLeadingZero(localDate.getMonthValue());
        String day = formatWithLeadingZero(localDate.getDayOfMonth());
        String dayOfWeek = changeDayOfWeekToKorean(localDate.getDayOfWeek());
        System.out.printf("%s월 %s일 %s %s:%s (%s)", month, day, dayOfWeek, "--", "--", "결석");
        System.out.println();
    }

    private static boolean isWeekend(LocalDate currentDate) {
        return currentDate.getDayOfWeek() == DayOfWeek.SATURDAY || currentDate.getDayOfWeek() == DayOfWeek.SUNDAY;
    }

    private static String formatWithLeadingZero(int number) {
        String parsedNumber = String.valueOf(number);
        if (number < 10) {
            parsedNumber = "0" + number;
        }
        return parsedNumber;
    }

    private String changeDayOfWeekToKorean(final DayOfWeek dayOfWeek) {
        if (dayOfWeek == DayOfWeek.MONDAY) {
            return "월요일";
        }
        if (dayOfWeek == DayOfWeek.TUESDAY) {
            return "화요일";
        }
        if (dayOfWeek == DayOfWeek.WEDNESDAY) {
            return "수요일";
        }
        if (dayOfWeek == DayOfWeek.THURSDAY) {
            return "목요일";
        }
        if (dayOfWeek == DayOfWeek.FRIDAY) {
            return "금요일";
        }
        if (dayOfWeek == DayOfWeek.SATURDAY) {
            return "토요일";
        }
        return "일요일";
    }

}
