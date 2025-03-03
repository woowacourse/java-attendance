package domain;

import java.time.LocalDate;
import java.time.LocalTime;

public interface TimeProvider {
    LocalDate getNowDate();

    LocalTime getNowTime();
}
