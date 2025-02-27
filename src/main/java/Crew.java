public class Crew {

    private final String name;

    private Crew(final String name) {

        if (name.isBlank()) {
            throw new IllegalArgumentException("닉네임은 필수 입니다.");
        }

        this.name = name;
    }

    public static Crew of(String name) {
        return new Crew(name);
    }

    public String getName() {
        return name;
    }
}

