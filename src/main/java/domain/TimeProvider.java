package domain;

import java.time.LocalDate;
import java.time.LocalTime;

public interface TimeProvider {
    public LocalDate getNowDate();

    public LocalTime getNowTime();
}
