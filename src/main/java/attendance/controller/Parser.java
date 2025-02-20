package attendance.controller;

import attendance.model.Crew;

public class Parser {

    public static Crew parseCrew(String name) {
        return new Crew(name);
    }

    public static int parseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("숫자가 아닙니다.");
        }
    }
}
