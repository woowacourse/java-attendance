package domain;

import static util.Constants.ERROR_HEADER;

public record CrewName(String name) implements Comparable<CrewName> {
    private static final String NAME_FORMAT_ERROR = "닉네임은 2자 이상 4자 이하로 입력해주세요.";
    private static final int MIN_NAME_LENGTH = 2;
    private static final int MAX_NAME_LENGTH = 4;

    public CrewName {
        validate(name);
    }

    private void validate(String name) {
        if (name.length() < MIN_NAME_LENGTH || name.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException(ERROR_HEADER + NAME_FORMAT_ERROR);
        }
    }

    @Override
    public int compareTo(CrewName other) {
        return this.name.compareTo(other.name);
    }
}
