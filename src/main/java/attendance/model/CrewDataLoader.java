package attendance.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class CrewDataLoader {

    private final AttendanceRegister register;

    public CrewDataLoader(AttendanceRegister register) {
        this.register = register;
    }

    public void load(String path) {
        register.attend(
                "1",
                new AttendanceDate(LocalDate.now()),
                LocalTime.now()
        );
        register.attend(
                "2",
                new AttendanceDate(LocalDate.now()),
                LocalTime.now()
        );
        register.attend(
                "3",
                new AttendanceDate(LocalDate.now()),
                LocalTime.now()
        );
        register.attend(
                "4",
                new AttendanceDate(LocalDate.now()),
                LocalTime.now()
        );
        register.attend(
                "5",
                new AttendanceDate(LocalDate.now()),
                LocalTime.now()
        );

    }
}
