package view;

import java.util.regex.Pattern;
import util.Console;

public class InputView {
    public static String inputFeatureNumber() {
        System.out.println("오늘은 12월 16일 월요일입니다. 기능을 선택해 주세요.\n"
                + "1. 출석 확인\n"
                + "2. 출석 수정\n"
                + "3. 크루별 출석 기록 확인\n"
                + "4. 제적 위험자 확인\n"
                + "Q. 종료");
        String featureNumber = Console.readLine();
        validateFeatureNumber(featureNumber);
        return featureNumber;
    }

    private static void validateFeatureNumber(final String featureNumber) {
        if (!featureNumber.equals("1") && !featureNumber.equals("2") && !featureNumber.equals("3")
                && !featureNumber.equals("4") && !featureNumber.equals("Q")) {
            throw new IllegalArgumentException("잘못된 기능 번호입니다.");
        }
    }

    public static String inputNickname() {
        System.out.println("닉네임을 입력해 주세요.");
        return Console.readLine();
    }

    public static String inputTime() {
        System.out.println("등교 시간을 입력해 주세요.");
        String time = Console.readLine();
        validateTimeFormat(time);
        return time;
    }

    private static void validateTimeFormat(final String input) {
        if (!isCorrectFormat(input)) {
            throw new IllegalArgumentException("시간은 24시간 형식만 사용합니다.");
        }
    }

    private static boolean isCorrectFormat(final String input) {
        String datePattern = "2[0-3]|[01][0-9]:[0-5][0-9]";
        Pattern correctPattern = Pattern.compile(datePattern);

        return correctPattern.matcher(input).find();
    }
}
