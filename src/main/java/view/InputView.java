package view;

import domain.Feature;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Scanner;

public class InputView {

    private static final String FEATURE_SELECT_FORMAT = "%s. %s%n";
    private static final String TODAY_INFO_FORMAT = "오늘은 %d월 %d일 %s입니다. 기능을 선택해 주세요.%n";
    private static final String READ_ATTENDED_NAME_MESSAGE = "닉네임을 입력해 주세요.";
    private static final String READ_ATTENDED_TIME_MESSAGE = "등교 시간을 입력해 주세요.";
    private static final String EMPTY_ERROR_MESSAGE = "입력값이 없습니다.";

    public String readFeature(LocalDate localDate) {
        Scanner scanner = new Scanner(System.in);
        System.out.printf(TODAY_INFO_FORMAT, localDate.getMonthValue(), localDate.getDayOfMonth()
            , localDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN));
        for (Feature feature : Feature.values()) {
            System.out.printf(FEATURE_SELECT_FORMAT, feature.getNumber(), feature.getName());
        }
        String input = scanner.nextLine();

        validateEmptyString(input);
        return input;
    }

    public String readAttendedName() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(READ_ATTENDED_NAME_MESSAGE);
        String input = scanner.nextLine();

        validateEmptyString(input);
        return input;
    }

    public String readAttendedTime() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(READ_ATTENDED_TIME_MESSAGE);
        String input = scanner.nextLine();

        validateEmptyString(input);
        return input;
    }

    private void validateEmptyString(String input) {
        if (input.isBlank()) {
            throw new IllegalArgumentException(EMPTY_ERROR_MESSAGE);
        }
    }
}