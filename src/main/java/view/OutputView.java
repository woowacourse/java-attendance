package view;

import constant.Constants;
import domain.Attendance;
import domain.AttendanceDate;
import domain.AttendanceStatus;
import domain.AttendanceTime;
import domain.Attendances;
import domain.Crew;
import domain.CrewStatus;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import util.DayOfWeekConvertor;

public class OutputView {

    public void printWelcomeMessage() {
        System.out.println(String.format("오늘은 %02d월 %02d일 %s요일입니다. 기능을 선택해 주세요.",
                Constants.NOW_DATE.getMonthValue(),
                Constants.NOW_DATE.getDayOfMonth(),
                DayOfWeekConvertor.convertToKorean(Constants.NOW_DATE.getDayOfWeek())));
    }

    public void printErrorMessage(String errorMessage) {
        System.out.println(errorMessage);
    }

    public void printAttendanceMessage(Attendance attendance) {
        System.out.println(this.generateAttendanceMessage(attendance) + System.lineSeparator());
    }

    public void printEditMessage(Attendance oldAttendance, Attendance newAttendance) {
        System.out.print(System.lineSeparator() + generateAttendanceMessage(oldAttendance));

        System.out.println(String.format(" -> %02d:%02d (%s) 수정 완료!",
                newAttendance.getAttendanceTime().getHour(),
                newAttendance.getAttendanceTime().getMinute(),
                newAttendance.getAttendanceStatus().getStatus()));
    }

    public void printAttendances(Crew crew, Attendances attendances) {
        System.out.println(System.lineSeparator() + String.format("이번 달 %s의 출석 기록입니다.", crew.getName()) + System.lineSeparator());

        List<LocalDate> pastEducationDates = AttendanceDate.getPastEducationDates(Constants.NOW_DATE);
        for (LocalDate educationDate : pastEducationDates) {
            System.out.println(generateCrewAttendanceMessage(attendances, educationDate));
        }
        printCrewStatusMessage(attendances);
    }

    public void printExpelledCrews(List<Crew> crews) {
        List<Crew> sortedCrews = new ArrayList<>(crews);
        sortedCrews.sort(Comparator.comparing(Crew::getCrewStatusSequence)
                .thenComparing(crew -> crew.getExpelledAbsentCount(Constants.NOW_DATE))
                .reversed()
                .thenComparing(Crew::getName));

        System.out.println("제적 위험자 조회 결과");
        for (Crew crew : sortedCrews) {
            System.out.println(generateExpelledCrewMessage(crew));
        }
        System.out.println();
    }

    private String generateExpelledCrewMessage(Crew crew) {
//        - 빙티: 결석 3회, 지각 4회 (면담)
        return String.format("- %s: 결석 %d회, 지각 %d회 (%s)",
                crew.getName(),
                crew.getAbsentCount(Constants.NOW_DATE),
                crew.getLateCount(),
                crew.getCrewStatus(Constants.NOW_DATE).getStatus());
    }

    private String generateCrewAttendanceMessage(Attendances attendances, LocalDate educationDate) {
        if (attendances.checkAlreadyAttend(new AttendanceDate(educationDate))) {
            Attendance attendance = attendances.findAttendanceByDate(educationDate);
            return generateAttendanceMessage(attendance);
        }
        return generateAbsentMessage(educationDate);
    }

    private String generateAttendanceMessage(Attendance attendance) {
        AttendanceDate attendanceDate = attendance.getAttendanceDate();
        AttendanceTime attendanceTime = attendance.getAttendanceTime();
        AttendanceStatus attendanceStatus = attendance.getAttendanceStatus();

        return String.format("%02d월 %02d일 %s요일 %02d:%02d (%s)",
                attendanceDate.getMonthValue(),
                attendanceDate.getDayOfMonth(),
                DayOfWeekConvertor.convertToKorean(attendanceDate.getDayOfWeek()),
                attendanceTime.getHour(),
                attendanceTime.getMinute(),
                attendanceStatus.getStatus());
    }

    private String generateAbsentMessage(LocalDate date) {
        return String.format("%02d월 %02d일 %s요일 --:-- (결석)",
                date.getMonthValue(),
                date.getDayOfMonth(),
                DayOfWeekConvertor.convertToKorean(date.getDayOfWeek()));
    }

    private void printCrewStatusMessage(Attendances attendances) {
        int attendanceCount = attendances.countAttendance();
        int lateCount = attendances.countLate();
        int absentCount = attendances.countUnattended(Constants.NOW_DATE);

        System.out.println(System.lineSeparator() + String.format("출석: %d회", attendanceCount) +
                System.lineSeparator() + String.format("지각: %d회", lateCount) +
                System.lineSeparator() + String.format("결석: %d회", absentCount) + System.lineSeparator());

        CrewStatus crewStatus = CrewStatus.checkCrewStatus(lateCount, absentCount);

        if (!crewStatus.equals(CrewStatus.NORMAL)) {
            System.out.println(String.format("%s입니다.", crewStatus.getStatus()) + System.lineSeparator());
        }
    }
}
