package domain;

import java.util.HashMap;
import java.util.Map;

public class AttendanceBook {

    private final Map<String, AttendResult> attendBook;

    public AttendanceBook() {
        this.attendBook = new HashMap<>();
    }

    public void addAttend(final String name, final Attend attend) {
        checkContainsName(name);
        AttendResult attendResult = attendBook.get(name);
        attendResult.addAttend(attend);
    }

    private void checkContainsName(final String name) {
        if (!attendBook.containsKey(name)) {
            throw new IllegalArgumentException("존재하지 않는 닉네임입니다.");
        }
    }

    public void register(final String name) {
        attendBook.putIfAbsent(name, new AttendResult());
    }
    }
}
