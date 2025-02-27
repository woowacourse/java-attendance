package model;

public class Nickname {

    private final String value;

    public Nickname(final String value) {
        validate(value);
        this.value = value;
    }

    private void validate(final String value) {
        final String prefix = "^[가-힣]{2,4}$";
        if (!value.matches(prefix)) {
            throw new IllegalArgumentException("닉네임은 한글만 가능합니다.");
        }
    }

    public String getValue() {
        return value;
    }
}
