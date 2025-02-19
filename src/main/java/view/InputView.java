package view;

import controller.dto.AttendanceTimeDto;
import java.util.Arrays;
import java.util.Scanner;

public class InputView {
    public static Function readOption() {
        // TODO: 날짜 출력
        System.out.println("기능을 선택해 주세요.");
        Arrays.stream(Function.values()).forEach(function -> System.out.println(function.toString()));
        return Function.getFunction(scan());
    }

    public static String readNickname() {
        System.out.println("닉네임을 입력해 주세요.");
        return scan();
    }

    public static AttendanceTimeDto readAttendanceTime() {
        System.out.println("등교 시간을 입력해 주세요.");
        return Parser.parseAttendanceTime(scan());
    }

    public static int readToday() {
        System.out.println("오늘 날짜를 입력해주세요.");
        return Parser.parseInteger(scan());
    }


    private static String scan() {
        Scanner scanner = new Scanner(System.in);
        return scanner.next();
    }
}
