package view.parser;

public class DayParser {

    private static final int MIN_DAY = 1;
    private static final int MAX_DAY = 31;

    public static int validateDayFormat(String inputDay) {
        int day = validateInteger(inputDay);
        if (day < MIN_DAY || day > MAX_DAY) {
            throw new IllegalArgumentException("[ERROR] 범위에 맞게 입력해주세요.");
        }
        return day;
    }

    private static int validateInteger(String input) {
        try{
            return Integer.parseInt(input);
        }catch (NumberFormatException e){
            throw new IllegalArgumentException("[ERROR] 정수로 입력해 주세요");
        }
    }
}
