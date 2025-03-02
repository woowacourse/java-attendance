package view;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Scanner;

public class InputView {

    private final Scanner scanner = new Scanner(System.in);

    public String readOperationChoose(final LocalDate now) {
        String month = parseWithLeadingZero(now.getMonthValue());
        String day = parseWithLeadingZero(now.getDayOfMonth());
        String dayOfWeek = parseDayOfWeekToKorean(now.getDayOfWeek());

        System.out.printf("오늘은 %s월 %s일 %s입니다. 기능을 선택해 주세요.%n", month, day, dayOfWeek);
        System.out.println("1. 출석 확인");
        System.out.println("2. 출석 수정");
        System.out.println("3. 크루별 출석 기록 확인");
        System.out.println("4. 제적 위험자 확인");
        System.out.println("Q. 종료");

        return scanner.nextLine();
    }

    public String readCrewName() {
        System.out.println("닉네임을 입력해 주세요");
        return scanner.nextLine();
    }

    public String readAttendanceTime() {
        System.out.println("등교 시간을 입력해 주세요.");
        return scanner.nextLine();
    }

    public String readModifyCrewName() {
        System.out.println("출석을 수정하려는 크루의 닉네임을 입력해 주세요");
        return scanner.nextLine();
    }

    public String readModifyDay() {
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
        return scanner.nextLine();
    }

    public String readModifyTime() {
        System.out.println("언제로 변경하겠습니까?");
        return scanner.nextLine();
    }

    public void closeScanner() {
        scanner.close();
    }

    private static String parseWithLeadingZero(int number) {
        String parsedNumber = String.valueOf(number);
        if (number < 10) {
            parsedNumber = "0" + number;
        }
        return parsedNumber;
    }

    private String parseDayOfWeekToKorean(final DayOfWeek dayOfWeek) {
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
