package view;

import java.util.Scanner;

public class InputView {
    public static Scanner scanner = new Scanner(System.in);

    public static FeatureType askFeature() {
        System.out.println("기능을 선택해 주세요.");
        for (FeatureType value : FeatureType.values()) {
            System.out.printf("%s. %s%n", value.getKey(), value.getName());
        }

        return FeatureType.from(scanner.next());
    }

    public static String askNickname(boolean isForEdit) {
        if (isForEdit) {
            System.out.println("출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
            return scanner.next();
        }

        System.out.println("닉네임을 입력해 주세요.");
        return scanner.next();
    }

    public static String askAttendanceTime(boolean isForEdit) {
        if (isForEdit) {
            System.out.println("언제로 변경하겠습니까?");
            return scanner.next();
        }

        System.out.println("등교 시간을 입력해 주세요.");
        return scanner.next();
    }

    public static String askDayForEdit() {
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
        return scanner.next();
    }
}
