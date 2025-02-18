package attendance.domain;

import java.util.Set;

public class AttendanceBook {

    private final Set<String> names;

    public AttendanceBook(Set<String> names) {
        this.names = names;
    }

    public void checkName(String name) {
        if (!names.contains(name)) {
            throw new IllegalArgumentException("[ERROR] 출석부에 없는 크루원입니다.");
        }
    }
}
