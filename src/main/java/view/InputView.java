package view;

import util.Console;

public class InputView {
    public static String inputFeature(int month, int day, String dayOfWeek) {
        System.out.printf("오늘은 %d월 %02d일 %s입니다. 기능을 선택해 주세요.\n" + "1. 출석 확인\n" + "2. 출석 수정\n" + "3. 크루별 출석 기록 확인\n"
                + "4. 제적 위험자 확인\n" + "Q. 종료\n", month, day, dayOfWeek);
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

    public static String inputNickName() {
        System.out.println("닉네임을 입력해 주세요.");
        String input = Console.readLine();
        validateNullOrEmpty(input);
        return input;
    }

    public static String inputSchoolStartTime() {
        System.out.println("등교 시간을 입력해 주세요.");
        String input = Console.readLine();
        validateNullOrEmpty(input);
        return input;
    }

    private static void validateNullOrEmpty(final String nickName) {
        if (nickName.isBlank()) {
            throw new IllegalArgumentException("빈 값은 입력할 수 없습니다.");
        }
    }

    public static String inputUpdateNickName() {
        System.out.println("출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
        String input = Console.readLine();
        validateNullOrEmpty(input);
        return input;
    }

    public static String inputUpdateDate() {
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
        String input = Console.readLine();
        validateInteger(input);
        return input;
    }

    private static void validateInteger(final String input) {
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("0이상의 숫자만 입력해 주세요.");
        }
    }

    public static String inputUpdateTime() {
        System.out.println("언제로 변경하겠습니까?");
        String input = Console.readLine();
        validateNullOrEmpty(input);
        return input;
    }
}
