package view;

import controller.dto.AttendanceTimeDto;
import java.util.Arrays;
import java.util.Scanner;

public class InputView {
    public static Function readOption() {
        System.out.println("기능을 선택해 주세요.");
        Arrays.stream(Function.values()).forEach(function -> System.out.println(function.toString()));
        return Function.getFunction(scan());
    }

    public static String readNickname() {
        System.out.println();
        System.out.println("닉네임을 입력해 주세요.");
        return scan();
    }

    public static String readNicknameWillEditHistory() {
        System.out.println();
        System.out.println("출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
        return scan();
    }

    public static AttendanceTimeDto readAttendanceTime() {
        System.out.println("등교 시간을 입력해 주세요.");
        return Parser.parseAttendanceTime(scan());
    }

    public static AttendanceTimeDto readAttendanceTimeWillEditHistory() {
        System.out.println("언제로 변경하겠습니까?");
        return Parser.parseAttendanceTime(scan());
    }

    public static int readToday() {
        System.out.println("오늘 날짜를 입력해주세요. (현재는 12월만 시범 운영중입니다. 따라서 일만 입력해주세요.)");
        return Parser.parseDay(scan());
    }

    public static int readDay() {
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
        return Parser.parseDay(scan());
    }


    private static String scan() {
        Scanner scanner = new Scanner(System.in);
        return scanner.next();
    }
}
