package view;

import domain.AttendanceStatus;
import domain.RiskStatus;
import dto.CrewResponse;
import global.util.DateUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import static view.ViewUtil.getAttendanceStatusMessage;
import static view.ViewUtil.getRiskStatusMessage;

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

        System.out.printf("\n%s: %d회\n", getAttendanceStatusMessage(AttendanceStatus.ATTENDANCE), crewResponse.attendanceCount());
        System.out.printf("%s: %d회\n", getAttendanceStatusMessage(AttendanceStatus.TARDY), crewResponse.tardyCount());
        System.out.printf("%s: %d회\n\n", getAttendanceStatusMessage(AttendanceStatus.ABSENCE), crewResponse.absenceCount());

        RiskStatus riskStatus = crewResponse.riskStatus();

        if (!riskStatus.equals(RiskStatus.NONE)) {
            System.out.println(getRiskStatusMessage(riskStatus) + " 대상자입니다.");
        }
    }

    public void printErrorMessage(Exception e) {
        System.out.println("[ERROR] " + e.getMessage());
    }

    public void printRiskCrews(List<CrewResponse> crewResponseWithRisk) {
        System.out.println("제적 위험자 조회 결과");
        crewResponseWithRisk.stream()
                .sorted((o1, o2) -> {
                    int totalCount1 = o1.absenceCount() + o1.tardyCount() / 3;
                    int totalCount2 = o2.absenceCount() + o2.tardyCount() / 3;

                    if (totalCount1 == totalCount2) {
                        return o1.name().compareTo(o2.name());
                    }

                    return totalCount1 - totalCount2;
                })
                .forEach(e -> System.out.printf("- %s: 결석 %d회, 지각 %d회 (%s)\n", e.name(), e.absenceCount(), e.tardyCount(), getRiskStatusMessage(e.riskStatus())));
    }

    public void printAttendDateAttendanceMessage(LocalDate currentDate, Map<LocalDate, LocalTime> map) {
        System.out.println(getEachDateAttendanceMessage(currentDate, map));
    }

    public void printAttendEditMessage(LocalDate date, AttendanceStatus beforeAttendanceStatus, LocalTime beforeTime, AttendanceStatus afterAttendanceStatus, LocalTime afterTime) {
        System.out.printf("%s %s (%s) -> %s (%s) 수정 완료!\n",
                DateTimeFormatter.ofPattern("MM월 dd일 E요일").format(date),
                DateTimeFormatter.ofPattern("hh:mm").format(beforeTime),
                ViewUtil.getAttendanceStatusMessage(beforeAttendanceStatus),
                DateTimeFormatter.ofPattern("hh:mm").format(afterTime),
                ViewUtil.getAttendanceStatusMessage(afterAttendanceStatus));
    }

    private String getEachDateAttendanceMessage(LocalDate currentDate, Map<LocalDate, LocalTime> map) {
        if (!map.containsKey(currentDate)) {
            return String.format("%d월 %02d일 %s %s (%s)", currentDate.getMonthValue(), currentDate.getDayOfMonth(),
                    ViewUtil.getDayOfWeekToMessage(currentDate.getDayOfWeek()), ViewUtil.getNoneAttendanceMessage(), getAttendanceStatusMessage(AttendanceStatus.ABSENCE));
        }
        LocalTime attendTime = map.get(currentDate);
        AttendanceStatus attendanceStatus = AttendanceStatus.attend(DateUtil.assembleDateAndTime(currentDate, attendTime));
        return String.format("%d월 %02d일 %s %s (%s)", currentDate.getMonthValue(), currentDate.getDayOfMonth(),
                ViewUtil.getDayOfWeekToMessage(currentDate.getDayOfWeek()), attendTime, getAttendanceStatusMessage(attendanceStatus));
    }
}
