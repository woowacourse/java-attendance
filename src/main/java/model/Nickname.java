package model;

public class Nickname {

    private static String value;

    public Nickname(final String value) {
        validateLanguage(value);
        validateLength(value);
        this.value = value;
    }

    private void validateLanguage(final String value) {
        final String prefix = "^[가-힣]";
        if (!value.matches(prefix)) {
            throw new IllegalArgumentException();
        }
    }

    private static void validateLength(final String value) {
        if (value.length() < 2 || value.length() > 4) {
            throw new IllegalArgumentException("닉네임은 2부터 4사이의 글자여야 합니다.");
        }
    }

    public String getValue() {
        return value;
    }
}
