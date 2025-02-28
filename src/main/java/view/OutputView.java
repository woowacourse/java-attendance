package view;

import domain.Attendance;
import domain.AttendanceStatus;
import domain.Attendances;
import domain.CrewStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

public class OutputView {
    public void printAlreadyCheckedGuide(String message) {
        System.out.println(message);
    }

    public void printException(String message) {
        System.out.println("[ERROR] : " + message);
    }

    public void printCheckAttendance(Attendance attendance) {
        System.out.println(makeAttendanceLog(attendance));
    }

    public void printChangeAttendance(Attendance originalAttendance, Attendance changeAttendance) {
        System.out.print(makeAttendanceLog(originalAttendance));
        System.out.print(makeChangeAttendanceLog(changeAttendance));
    }

    private String makeChangeAttendanceLog(Attendance attendance) {
        LocalDateTime dateTime = attendance.getDateTime();
        AttendanceStatus attendanceStatus = attendance.calculateAttendanceStatus();
        return String.format("-> %s (%s) 수정 완료!\n",
                getTimePrintForm(dateTime),
                attendanceStatus.getKorean());
    }

    public void printCrewAttendances(String nickname, Attendances attendances, LocalDate today) {
        CrewStatus crewStatus = attendances.calculateCrewStatus(today);
        Map<AttendanceStatus, Integer> attendanceStatuses = attendances.calculateAllAttendanceStatus(today);
        List<Attendance> list = attendances.getRecords()
                .stream()
                .filter(record -> record.isBefore(today))
                .sorted()
                .toList();

        System.out.printf("\n이번 달 %s의 출석 기록입니다.\n\n", nickname);
        for (Attendance attendance : list) {
            System.out.println(makeAttendanceLog(attendance));
        }
        System.out.println();
        for (Entry<AttendanceStatus, Integer> entry : attendanceStatuses.entrySet()) {
            System.out.printf("%s : %d회\n", entry.getKey().getKorean(), entry.getValue());
        }
        System.out.println();
        if (crewStatus != CrewStatus.NORMAL) {
            System.out.printf("%s 대상자입니다.\n", crewStatus.getKorean());
        }
    }

    private String makeAttendanceLog(Attendance attendance) {
        LocalDateTime dateTime = attendance.getDateTime();
        AttendanceStatus attendanceStatus = attendance.calculateAttendanceStatus();
        return String.format("12월 %02d일 %s %s (%s)\n", dateTime.getDayOfMonth(),
                dateTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN),
                getTimePrintForm(dateTime),
                attendanceStatus.getKorean());
    }

    private String getTimePrintForm(LocalDateTime dateTime) {
        if (dateTime.getHour() == 23 && dateTime.getMinute() == 59) {
            return "--:--";
        }
        return String.format("%02d:%02d", dateTime.getHour(), dateTime.getMinute());
    }

    public void printRiskOfExpelledCrews(Map<String, Attendances> riskOfExpelledCrews, LocalDate today) {
        System.out.println("제적 위험자 조회 결과\n");

        List<ExpelledCrewsInfo> expelledCrewsInfos = new ArrayList<>();
        for (Entry<String, Attendances> entry : riskOfExpelledCrews.entrySet()) {
            expelledCrewsInfos.add(new ExpelledCrewsInfo(entry.getKey(), entry.getValue(), today));
        }

        expelledCrewsInfos.stream().sorted()
                .forEach(ExpelledCrewsInfo::printInfo);
    }

    static class ExpelledCrewsInfo implements Comparable<ExpelledCrewsInfo> {
        String nickname;
        int absentCount;
        int lateCount;
        CrewStatus crewStatus;

        public ExpelledCrewsInfo(String nickname, Attendances attendances, LocalDate today) {
            Map<AttendanceStatus, Integer> attendanceStatus = attendances.calculateAllAttendanceStatus(today);
            this.nickname = nickname;
            this.absentCount = attendanceStatus.get(AttendanceStatus.ABSENT);
            this.lateCount = attendanceStatus.get(AttendanceStatus.LATE);
            this.crewStatus = attendances.calculateCrewStatus(today);
        }

        @Override
        public int compareTo(ExpelledCrewsInfo info) {
            if (crewStatus.getPriority() == info.crewStatus.getPriority()) {
                if (absentCount + lateCount == info.absentCount + info.lateCount) {
                    if (absentCount == info.absentCount) {
                        return nickname.compareTo(info.nickname);
                    }
                    return absentCount - info.absentCount;
                }
                return (info.lateCount + info.absentCount) - (lateCount + absentCount);
            }
            return crewStatus.getPriority() - info.crewStatus.getPriority();
        }

        public void printInfo() {
            System.out.printf("- %s: 결석 %d회, 지각 %d회 (%s)\n", nickname, absentCount, lateCount, crewStatus.getKorean());
        }
    }
}
