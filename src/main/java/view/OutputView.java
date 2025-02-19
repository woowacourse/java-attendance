package view;

import domain.*;

public class OutputView {
    public void printCrewAttendance(Crew crew) {
        for(int date:December.getWeekDays()){
            AttendTime attendTime = crew.findAttendanceByDate(date);
            if(attendTime != null){
                System.out.println(attendTime.checkTime());
                continue;
            }

            System.out.printf("12월 %d일 %s --:-- (결석)",date,December.getDayByDate(date));
            System.out.println();
        }

        AttendanceHistory attendanceHistory = crew.getAttendanceHistory();
        System.out.printf("출석: %d회",attendanceHistory.calculateOnTime());
        System.out.println();
        System.out.printf("지각: %d회",attendanceHistory.calculateLate());
        System.out.println();
        System.out.printf("결석: %d회",attendanceHistory.calculateAbsent());

        AttendanceStatus attendanceStatus = attendanceHistory.getAttendanceStatus();
        System.out.printf("%s 대상자입니다.",attendanceStatus.getStatus());
    }
}
