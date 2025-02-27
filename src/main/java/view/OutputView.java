package view;

import constant.Constants;
import domain.Attendance;
import domain.AttendanceDate;
import domain.Attendances;
import domain.Crew;
import domain.CrewStatus;
import java.time.LocalDate;
import java.util.List;
import util.DayOfWeekConvertor;
import view.sortingMachine.CrewSortingMachine;

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

    public void printAttendances(Crew crew) {
        Attendances attendances = crew.getAttendances();
        System.out.println(System.lineSeparator() + String.format("이번 달 %s의 출석 기록입니다.", crew.getName()) + System.lineSeparator());

        List<LocalDate> pastEducationDates = AttendanceDate.findPastEducationDates(Constants.NOW_DATE);
        for (LocalDate educationDate : pastEducationDates) {
            System.out.println(generateCrewAttendanceMessage(attendances, educationDate));
        }
        printCrewStatusMessage(attendances);
    }

    public void printExpelledCrews(List<Crew> crews, CrewSortingMachine crewSortingMachine) {
        List<Crew> sortedCrews = crewSortingMachine.sortCrews(crews);
        System.out.println("제적 위험자 조회 결과");
        for (Crew crew : sortedCrews) {
            System.out.println(generateExpelledCrewMessage(crew));
        }
        System.out.println();
    }

    private String generateExpelledCrewMessage(Crew crew) {
        return String.format("- %s: 결석 %d회, 지각 %d회 (%s)",
                crew.getName(),
                crew.getAbsentCount(Constants.NOW_DATE),
                crew.getLateCount(),
                crew.findCrewStatus(Constants.NOW_DATE).getStatus());
    }

    private String generateCrewAttendanceMessage(Attendances attendances, LocalDate educationDate) {
        AttendanceDate attendanceDate = new AttendanceDate(educationDate);
        if (attendances.checkAlreadyAttend(attendanceDate)) {
            Attendance attendance = attendances.findAttendanceByDate(attendanceDate);
            return generateAttendanceMessage(attendance);
        }
        return generateAbsentMessage(educationDate);
    }

    private String generateAttendanceMessage(Attendance attendance) {
        return String.format("%02d월 %02d일 %s요일 %02d:%02d (%s)",
                attendance.getAttendanceDate().getMonthValue(),
                attendance.getAttendanceDate().getDayOfMonth(),
                DayOfWeekConvertor.convertToKorean(attendance.getAttendanceDate().getDayOfWeek()),
                attendance.getAttendanceTime().getHour(),
                attendance.getAttendanceTime().getMinute(),
                attendance.getAttendanceStatus().getStatus());
    }

    private String generateAbsentMessage(LocalDate date) {
        return String.format("%02d월 %02d일 %s요일 --:-- (결석)",
                date.getMonthValue(),
                date.getDayOfMonth(),
                DayOfWeekConvertor.convertToKorean(date.getDayOfWeek()));
    }

    private void printCrewStatusMessage(Attendances attendances) {
        System.out.println(System.lineSeparator() + String.format("출석: %d회", attendances.countAttendance()) +
                System.lineSeparator() + String.format("지각: %d회", attendances.countLate()) +
                System.lineSeparator() + String.format("결석: %d회", attendances.countUnattended(Constants.NOW_DATE)) + System.lineSeparator());

        CrewStatus crewStatus = CrewStatus.checkCrewStatus(attendances.countLate(), attendances.countUnattended(Constants.NOW_DATE));

        if (!crewStatus.equals(CrewStatus.NORMAL)) {
            System.out.println(String.format("%s입니다.", crewStatus.getStatus()) + System.lineSeparator());
        }
    }
}
