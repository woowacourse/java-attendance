package view;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
            System.out.println("\n출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
            return scanner.next();
        }

        System.out.println("\n닉네임을 입력해 주세요.");
        return scanner.next();
    }

    public static LocalTime askAttendanceTime(boolean isForEdit) {
        if (isForEdit) {
            System.out.println("언제로 변경하겠습니까?");
            return LocalTime.parse(scanner.next(), DateTimeFormatter.ofPattern("HH:mm"));
        }

        System.out.println("등교 시간을 입력해 주세요.");
        return LocalTime.parse(scanner.next(), DateTimeFormatter.ofPattern("HH:mm"));
    }

    public static LocalDate askDayForEdit() {
        System.out.println("수정하려는 날짜(일)를 입력해 주세요. 현재는 12월 시범중이기에 월은 12월로 고정됩니다.");
        int day = Integer.parseInt(scanner.next());
        return LocalDate.of(2024, 12, day);
    }
}
