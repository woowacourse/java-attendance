package view;

import controller.dto.AttendanceTimeDto;
import java.util.Arrays;
import java.util.Scanner;

public class InputView {

    private static final Scanner SCANNER = new Scanner(System.in);

    public static FeatureOption readOption() {
        System.out.println("기능을 선택해 주세요.");
        Arrays.stream(FeatureOption.values()).forEach(featureOption -> System.out.println(featureOption.toString()));
        return FeatureOption.getFunction(SCANNER.next());
    }

    public static String readNickname() {
        System.out.println("닉네임을 입력해 주세요.");
        return SCANNER.next();
    }

    public static AttendanceTimeDto readAttendanceTime() {
        System.out.println("등교 시간을 입력해 주세요.");
        return Parser.parseAttendanceTime(SCANNER.next());
    }

    public static int readToday() {
        System.out.println("오늘 날짜를 입력해주세요.");
        return Parser.parseInteger(SCANNER.next());
    }
}
