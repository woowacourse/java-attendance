package view;

import domain.CheckInTime;

import java.time.LocalDateTime;

public class OutputView {

    public void printTodayCheckInTime(CheckInTime time) {
        LocalDateTime localDateTime = time.toLocalDateTime();

        System.out.println(localDateTime);
    }
}
