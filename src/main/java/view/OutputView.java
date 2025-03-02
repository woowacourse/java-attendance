package view;

import domain.attendance.AttendanceBook;
import domain.attendance.AttendanceCounts;
import domain.attendance.AttendanceInfo;
import domain.attendance.AttendanceInfos;
import domain.attendance.constant.AttendanceRiskLevel;
import domain.datetime.CampusDate;
import domain.Crew;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;

public class OutputView {

    public void writeAttendanceCheck(AttendanceInfo attendanceInfo) {
        String month = parseWithLeadingZero(attendanceInfo.getMonth());
        String day = parseWithLeadingZero(attendanceInfo.getDay());
        String dayOfWeek = parseDayOfWeekToKorean(attendanceInfo.getDayOfWeek());
        String hour = parseWithLeadingZero(attendanceInfo.getHour());
        String minute = parseWithLeadingZero(attendanceInfo.getMinute());
        String status = attendanceInfo.getAttendanceStatus().getValue();

        System.out.println(String.format("%s월 %s일 %s %s:%s (%s)", month, day, dayOfWeek, hour, minute, status));
    }

    public void writeModifiedAttendanceCheck(AttendanceInfo beforeInfo, AttendanceInfo afterInfo) {
        String month = parseWithLeadingZero(beforeInfo.getMonth());
        String day = parseWithLeadingZero(beforeInfo.getDay());
        String dayOfWeek = parseDayOfWeekToKorean(beforeInfo.getDayOfWeek());

        String beforeHour = parseWithLeadingZero(beforeInfo.getHour());
        String beforeMinute = parseWithLeadingZero(beforeInfo.getMinute());
        String beforeStatus = parseDayOfWeekToKorean(beforeInfo.getDayOfWeek());

        String afterHour = parseWithLeadingZero(afterInfo.getHour());
        String afterMinute = parseWithLeadingZero(afterInfo.getMinute());
        String afterStatus = parseDayOfWeekToKorean(afterInfo.getDayOfWeek());
        System.out.println(String.format("%s월 %s일 %s %s:%s (%s) -> %s:%s (%s) 수정 완료!", month, day, dayOfWeek,
                beforeHour, beforeMinute, beforeStatus,
                afterHour, afterMinute, afterStatus));
    }

    public void writeCreatedAttendanceCheck(AttendanceInfo afterInfo) {
        String month = parseWithLeadingZero(afterInfo.getMonth());
        String day = parseWithLeadingZero(afterInfo.getDay());
        String dayOfWeek = parseDayOfWeekToKorean(afterInfo.getDayOfWeek());

        String afterHour = parseWithLeadingZero(afterInfo.getHour());
        String afterMinute = parseWithLeadingZero(afterInfo.getMinute());
        String afterStatus = parseDayOfWeekToKorean(afterInfo.getDayOfWeek());
        System.out.println(String.format("%s월 %s일 %s --:-- (결석) -> %s:%s (%s) 수정 완료!", month, day, dayOfWeek,
                afterHour, afterMinute, afterStatus));
    }

    public void writeCrewHistory(LocalDate date, AttendanceInfos infos, AttendanceCounts counts,
                                 AttendanceRiskLevel riskLevel) {

        for (int day = 1; day <= date.getDayOfMonth(); day++) {
            if (isWeekend(date, day)) {
                continue;
            }
            CampusDate currentDate = CampusDate.ofDateWithDay(date, day);
            if (infos.hasInfoByDate(currentDate)) {
                writeAttendanceCheck(infos.findInfoByDate(currentDate));
                continue;
            }
            writeAbsenceAttendanceCheck(infos.findInfoByDate(currentDate));
        }
    }

    public void writeRiskCrewHistories(LocalDate date, AttendanceBook riskCrewBook) {
        List<Entry<Crew, AttendanceInfos>> sortedCrews = riskCrewBook.getBook().entrySet().stream()
                .sorted(Comparator.comparingInt(e -> -e.getValue().countsByDate(date).getTardinessAndAbsenceCount()))
                .toList();
        System.out.println("제적 위험자 조회 결과");
        for (Entry<Crew, AttendanceInfos> sortedCrew : sortedCrews) {
            String name = sortedCrew.getKey().getName();
            int absenceCount = sortedCrew.getValue().countsByDate(date).getAbsenceCount();
            int tardinessCount = sortedCrew.getValue().countsByDate(date).getTardinessCount();
            String status = sortedCrew.getValue().countsByDate(date).calculateAttendanceRiskLevel().getStatus();
            System.out.println(String.format("- %s: 결석 %d회, 지각 %d회, (%s)", name, absenceCount, tardinessCount, status));
        }
    }

    public void writeErrorMessage(Exception e) {
        System.out.println(e.getMessage());
    }

    private void writeAbsenceAttendanceCheck(AttendanceInfo attendanceInfo) {
        String month = parseWithLeadingZero(attendanceInfo.getMonth());
        String day = parseWithLeadingZero(attendanceInfo.getDay());
        String dayOfWeek = parseDayOfWeekToKorean(attendanceInfo.getDayOfWeek());

        System.out.println(String.format("%s월 %s일 %s --:-- (결석)", month, day, dayOfWeek));
    }

    private static boolean isWeekend(LocalDate date, int day) {
        return date.withDayOfMonth(day).getDayOfWeek() == DayOfWeek.SATURDAY
                || date.withDayOfMonth(day).getDayOfWeek() == DayOfWeek.SUNDAY;
    }

    private String parseWithLeadingZero(int number) {
        String parsedNumber = String.valueOf(number);
        if (number < 10) {
            parsedNumber = "0" + number;
        }
        return parsedNumber;
    }

    private String parseDayOfWeekToKorean(DayOfWeek dayOfWeek) {
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
