package domain;

public class Crew {
    private final String nickname;

    private Crew(String nickname) {
        this.nickname = nickname;
    }

    public static Crew from(String nickname) {
        return new Crew(nickname);
    }
}
