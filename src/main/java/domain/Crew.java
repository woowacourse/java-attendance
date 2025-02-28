package domain;

import java.util.Objects;

public class Crew implements Comparable<Crew> {
    private final String nickName;

    private Crew(String nickName) {
        validate(nickName);
        this.nickName = nickName;
    }

    public static Crew of(String nickName) {
        return new Crew(nickName);
    }

    private void validate(String nickName) {
        if (nickName.length() < 2 || nickName.length() > 4) {
            throw new IllegalArgumentException("닉네임은 2글자 이상 4글자 이하여야 합니다.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Crew crew)) {
            return false;
        }
        return Objects.equals(nickName, crew.nickName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(nickName);
    }

    @Override
    public int compareTo(Crew o) {
        return this.nickName.compareTo(o.nickName);
    }
}
