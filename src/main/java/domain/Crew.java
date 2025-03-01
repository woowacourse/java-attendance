package domain;

public class Crew {
    private final String nickname;

    public Crew(String nickname) {
        this.nickname = nickname;
    }

    public boolean equals(String name) {
        return name.equals(this.nickname);
    }
}
