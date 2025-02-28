package domain;

import domain.attendance.Attendances;

public class Crew {
    private final String nickname;
    private final Attendances attendances;

    public Crew(String nickname) {
        this.nickname = nickname;
        this.attendances = new Attendances();
    }

    public boolean equals(String name) {
        return name.equals(this.nickname);
    }
}
