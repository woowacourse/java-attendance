package attendance.view;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Scanner;

public class InputView {
    private final Scanner scanner = new Scanner(System.in);

    private static final String CHOOSE_FUNCTION = "오늘은 %d월 %d일 %s입니다. 기능을 선택해 주세요.";
    private static final String ATTENDANCE_CHECK = "1. 출석 확인";
    private static final String ATTENDANCE_MODIFY = "2. 출석 수정";
    private static final String ATTENDANCE_HISTORY_EACH_CREW = "3. 크루별 출석 기록 확인";
    private static final String ATTENDANCE_WARNING_CREW_CHECK = "4. 제적 위험자 확인";
    private static final String QUIT = "Q. 종료";

    private static final String READ_CREW_NAME = "닉네임을 입력해 주세요";
    private static final String READ_ATTENDANCE_TIME = "등교 시간을 입력해 주세요.";
    private static final String READ_MODIFY_CREW_NAME = "출석을 수정하려는 크루의 닉네임을 입력해 주세요";
    private static final String READ_MODIFY_DAY = "수정하려는 날짜(일)를 입력해 주세요.";
    private static final String READ_MODIFY_TIME = "언제로 변경하겠습니까?";

    public String readFunctionChoose(final LocalDate now) {
        int month = now.getMonthValue();
        int day = now.getDayOfMonth();
        String dayOfWeek = changeDayOfWeekToKorean(now.getDayOfWeek());

        System.out.println(String.format(CHOOSE_FUNCTION, month, day, dayOfWeek));
        System.out.println(ATTENDANCE_CHECK);
        System.out.println(ATTENDANCE_MODIFY);
        System.out.println(ATTENDANCE_HISTORY_EACH_CREW);
        System.out.println(ATTENDANCE_WARNING_CREW_CHECK);
        System.out.println(QUIT);

        return scanner.nextLine();
    }

    public String readCrewName() {
        System.out.println(READ_CREW_NAME);
        return scanner.nextLine();
    }

    public String readAttendanceTime() {
        System.out.println(READ_ATTENDANCE_TIME);
        return scanner.nextLine();
    }

    public String readModifyCrewName() {
        System.out.println(READ_MODIFY_CREW_NAME);
        return scanner.nextLine();
    }

    public String readModifyDay() {
        System.out.println(READ_MODIFY_DAY);
        return scanner.nextLine();
    }

    public String readModifyTime() {
        System.out.println(READ_MODIFY_TIME);
        return scanner.nextLine();
    }

    public void closeScanner() {
        scanner.close();
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
