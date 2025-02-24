package view;

import controller.dto.AttendanceRecodeDto;
import controller.dto.AttendanceResultDto;
import controller.dto.PenaltyCrewDto;
import domain.Date;
import domain.DateTime;
import domain.Penalty;
import domain.Time;
import domain.WorkDay;
import java.util.List;
import java.util.Objects;

public class OutputView {
    public void printArriveResult(DateTime dateTime, String attendanceStatusName) {
        Date date = dateTime.getDate();
        WorkDay workDay = date.getWorkDay();
        Time time = dateTime.getTime();

        System.out.printf("%s월 %s일 %s요일 %s:%s (%s)\n", date.getMonthValue(),
                date.getDayValue(), workDay.getDayOfWeekKorean(),
                convertTime(time.getHour()), convertTime(time.getMinute()), attendanceStatusName);
    }

    public void printUpdateResult(DateTime beforeDateTime, String beforeAttendanceStatusName,
                                  DateTime afterDateTime, String afterAttendanceStatusName) {
        Date date = beforeDateTime.getDate();
        WorkDay workDay = date.getWorkDay();
        Time beforeTime = beforeDateTime.getTime();
        Time afterTime = afterDateTime.getTime();
        System.out.printf("%s월 %s일 %s요일 %s:%s (%s) -> %s:%s (%s) 수정 완료!\n",
                date.getMonthValue(), date.getDayValue(), workDay.getDayOfWeekKorean(),
                convertTime(beforeTime.getHour()),
                convertTime(beforeTime.getMinute()), beforeAttendanceStatusName,
                convertTime(afterTime.getHour()),
                convertTime(afterTime.getMinute()), afterAttendanceStatusName);
    }

    public void printTotalAttendanceStatus(List<AttendanceRecodeDto> attendanceRecodeDto,
                                           AttendanceResultDto attendanceResultDto) {
        System.out.printf("이번 달 %s의 출석 기록입니다.\n", attendanceResultDto.name());
        attendanceRecodeDto.forEach(attendanceRecode -> {
            Date date = attendanceRecode.dateTime().getDate();
            WorkDay workDay = date.getWorkDay();
            Time time = attendanceRecode.dateTime().getTime();

            System.out.printf("%s월 %s일 %s요일 %s:%s (%s)\n", date.getMonthValue(),
                    date.getDayValue(), workDay.getDayOfWeekKorean(),
                    convertTime(time.getHour()), convertTime(time.getMinute()),
                    attendanceRecode.attendanceStatusName());
        });

        System.out.println();
        System.out.printf("출석: %d회\n", attendanceResultDto.attendanceCount());
        System.out.printf("지각: %d회\n", attendanceResultDto.perceptionCount());
        System.out.printf("결석: %d회\n", attendanceResultDto.absenceCount());

        if (!Objects.equals(attendanceResultDto.penaltyName(), Penalty.NONE.getName())) {
            System.out.printf("%s 대상자입니다.\n", attendanceResultDto.penaltyName());
        }
    }

    private String convertTime(Integer time) {
        if (time == null) {
            return "--";
        }

        String before = String.valueOf(time);

        if (before.length() < 2) {
            return "0" + before;
        }

        return before;
    }

    public void printPenaltyCrews(List<PenaltyCrewDto> penaltyCrewDtos) {
        System.out.println("제적 위험자 조회 결과");
        penaltyCrewDtos.forEach(penaltyCrewDto -> {
            System.out.printf("- %s: 결석 %d회, 지각 %d회 (%s)\n",
                    penaltyCrewDto.name(), penaltyCrewDto.absenceCount(),
                    penaltyCrewDto.perceptionCount(), penaltyCrewDto.penaltyName());
        });
    }

    public void printMessage(String message) {
        System.out.println(message);
    }
}
