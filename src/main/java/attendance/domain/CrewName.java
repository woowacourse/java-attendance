package attendance.domain;

import attendance.exception.AttendanceArgumentException;
import java.util.Objects;

public class CrewName {

    private static final String CANNOT_BE_EMPTY_NICKNAME = "닉네임은 공백일 수 없습니다.";

    private final String name;

    private CrewName(String name) {
        validateNickname(name);
        this.name = name;
    }

    public static CrewName from(String name) {
        return new CrewName(name);
    }

    private void validateNickname(String name) {
        if (name == null || name.isEmpty() || name.isBlank()) {
            throw new AttendanceArgumentException(CANNOT_BE_EMPTY_NICKNAME);
        }
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CrewName crewName = (CrewName) o;
        return Objects.equals(name, crewName.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
