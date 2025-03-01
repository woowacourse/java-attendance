package domain;

import java.util.regex.Pattern;

public class Crew {

    private static final String KOREAN_WORDS_REGEX = "^[가-힣]+$";

    private final String name;

    private Crew(String name) {
        validateCrewNameIsKoreanWords(name);
        this.name = name;
    }

    public static Crew fromName(String name) {
        return new Crew(name);
    }

    private void validateCrewNameIsKoreanWords(String name) {
        Pattern pattern = Pattern.compile(KOREAN_WORDS_REGEX);
        if (!pattern.matcher(name).matches()) {
            throw new IllegalArgumentException("[ERROR] 크루 이름을 정상적으로 입력해 주세요.");
        }
    }

    public String getName() {
        return name;
    }
}
