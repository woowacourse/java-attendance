package view;

import domain.Attendance;
import domain.Attendances;
import domain.CustomDayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class OutputView {
    public void printAttendanceDetail(Attendance attendance) {
        LocalDate todayDate = attendance.getDay().getDate();
        int month = todayDate.getMonthValue();
        int dayOfMonth = todayDate.getDayOfMonth();
        String dayOfWeek = CustomDayOfWeek.getInstance(todayDate).getName();
        String attendanceStatus = "출석";
        if (attendance.isAbsent()) {
            attendanceStatus = "결석";
        }
        if (attendance.isLate()) {
            attendanceStatus = "지각";
        }
        String attendanceTime = attendance.getTime().toString();
        System.out.printf("%d월 %d일 %s %s (%s)\n", month, dayOfMonth, dayOfWeek, attendanceTime, attendanceStatus);
    }

    public void printModifiedAttendanceDetail(Attendance originAttendance, Attendance modifiedAttendance) {
        LocalDate date = modifiedAttendance.getDay().getDate();
        int month = date.getMonthValue();
        int dayOfMonth = date.getDayOfMonth();
        String dayOfWeek = CustomDayOfWeek.getInstance(date).getName();

        String originalAttendanceTime = originAttendance.getTime().toString();
        String originalAttendanceStatus = "출석";
        if (originAttendance.isAbsent()) {
            originalAttendanceStatus = "결석";
        }
        if (originAttendance.isLate()) {
            originalAttendanceStatus = "지각";
        }

        String modifiedAttendanceTime = modifiedAttendance.getTime().toString();

        String modifiedAttendanceStatus = "출석";
        if (modifiedAttendance.isAbsent()) {
            modifiedAttendanceStatus = "결석";
        }
        if (modifiedAttendance.isLate()) {
            modifiedAttendanceStatus = "지각";
        }

        //        12월 03일 화요일 10:07 (지각) -> 09:58 (출석) 수정 완료!
        System.out.printf("%d월 %d일 %s %s (%s) -> %s (%s) 수정 완료!\n", month, dayOfMonth, dayOfWeek,
                originalAttendanceTime, originalAttendanceStatus, modifiedAttendanceTime, modifiedAttendanceStatus);

    }

    //이번 달 빙티의 출석 기록입니다.
//
//12월 02일 월요일 13:00 (출석)
//12월 03일 화요일 10:07 (지각)
//12월 04일 수요일 10:02 (출석)
//12월 05일 목요일 10:06 (지각)
//12월 06일 금요일 10:01 (출석)
//12월 09일 월요일 --:-- (결석)
//12월 10일 화요일 10:03 (출석)
//12월 11일 수요일 --:-- (결석)
//12월 12일 목요일 --:-- (결석)
//12월 13일 금요일 10:02 (출석)
//
//출석: 5회
//지각: 2회
//결석: 3회
//
//면담 대상자입니다.
    public void printAttendanceHistory(String nickname, Attendances attendances) {
        System.out.printf("이번 달 %s의 출석 기록입니다.\n\n", nickname);
        List<Attendance> attendanceList = attendances.getAttendances();
        attendanceList.sort(Comparator.comparing(attendance -> attendance.getDay().getDate()));

        for (Attendance attendance : attendanceList) {
            int month = attendance.getDay().getDate().getMonthValue();
            int dayOfMonth = attendance.getDay().getDate().getDayOfMonth();
            String dayOfWeek = CustomDayOfWeek.getInstance(attendance.getDay().getDate()).getName();
            String attendanceTime = "--:--";
            if (attendance.getTime() != null) {
                attendanceTime = attendance.getTime().toString();
            }

            String attendanceStatus = "출석";
            if (attendance.isAbsent()) {
                attendanceStatus = "결석";
            }
            if (attendance.isLate()) {
                attendanceStatus = "지각";
            }
            System.out.printf("%d월 %02d일 %s %s (%s)\n", month, dayOfMonth, dayOfWeek, attendanceTime, attendanceStatus);
        }
        int lateCount = attendances.getLateCount();
        int absentCount = attendances.getAbsentCount();
        int attendanceCount = attendances.getTotalCount() - lateCount - absentCount;
        System.out.println();
        System.out.printf("출석: %d회\n", attendanceCount);
        System.out.printf("지각: %d회\n", lateCount);
        System.out.printf("결석: %d회\n", absentCount);
        System.out.println();
        System.out.printf("%s 대상자입니다.\n", attendances.getPenaltyStatus().getName());
        System.out.println();
    }


    //대상 항목별 정렬 순서는 지각을 결석으로 간주하여 내림차순한다. 출석 상태가 같으면 닉네임으로 오름차순 정렬한다.
    //제적 위험자 조회 결과
    //- 빙티: 결석 3회, 지각 4회 (면담)
    //- 이든: 결석 2회, 지각 5회 (면담)
    //- 빙봉: 결석 1회, 지각 6회 (면담)
    //- 쿠키: 결석 2회, 지각 3회 (면담)
    //- 짱수: 결석 0회, 지각 6회 (경고)
    public void printPenaltyCrews(Map<String, Attendances> penaltyCrews) {
        List<String> nicknames = new ArrayList<>(penaltyCrews.keySet());
        nicknames.sort(
                Comparator.comparing((String nickname) -> penaltyCrews.get(nickname).getPenaltyStatus().getPoint(),
                                Comparator.reverseOrder())
                        .thenComparing(
                                (String nickname) -> penaltyCrews.get(nickname).getLateCount() + penaltyCrews.get(
                                        nickname).getAbsentCount(),
                                Comparator.reverseOrder())
                        .thenComparing((String nickname) -> nickname));

        for (String nickname : nicknames) {
            int absentCount = penaltyCrews.get(nickname).getAbsentCount();
            int lateCount = penaltyCrews.get(nickname).getLateCount();
            String penaltyName = penaltyCrews.get(nickname).getPenaltyStatus().getName();
            System.out.printf("- %s: 결석 %d회, 지각 %d회 (%s)\n", nickname, absentCount, lateCount, penaltyName);
        }
    }
}
