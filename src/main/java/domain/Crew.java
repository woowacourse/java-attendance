package domain;

import exception.AppException;

public class Crew implements Comparable<Crew> {
    private final String name;

    private Crew(String name) {
        validateName(name);
        this.name = name;
    }

    public static Crew of(String name) {
        return new Crew(name);
    }

    private void validateName(String name) {
        validateNonNull(name);
        validateNameLength(name);
        validateNameLanguage(name);
    }

    private void validateNonNull(String name) {
        if (name == null) {
            throw new AppException("이름은 NULL 이 될 수 없습니다.");
        }
    }

    private void validateNameLength(String name) {
        if (name.length() < 2 || name.length() > 4) {
            throw new AppException("이름은 2글자에서 4글자 사이여야 합니다.");
        }
    }

    private void validateNameLanguage(String name) {
        if (!name.matches("^[가-힣]+$")) {
            throw new AppException("이름은 한글이어야 합니다.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Crew crew = (Crew) o;
        return name.equals(crew.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public int compareTo(Crew crew) {
        return name.compareTo(crew.name);
    }
}
