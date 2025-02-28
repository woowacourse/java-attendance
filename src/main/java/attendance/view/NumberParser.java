package attendance.view;

public class NumberParser {

    public int parse(final String numberText) {
        try {
            return Integer.parseInt(numberText);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("올바른 숫자의 문자열을 입력해 주세요");
        }
    }
}
