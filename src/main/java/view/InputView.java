package view;

import domain.menu.Menu;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;
import util.converter.DayConverter;
import util.converter.DayOfWeekConverter;
import util.converter.TimeConverter;

public class InputView {

    private static final Scanner scanner = new Scanner(System.in);

    public static Menu readAttendanceMenu(LocalDate runDate) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(generateAttendanceMenuHeader(runDate));
        stringBuilder.append(generateAttendanceMenuOptions());
        String response = prompt(stringBuilder.toString());
        return Menu.of(response);
    }

    public static String readAttendanceRegisterCrewName() {
        return prompt("\n닉네임을 입력해 주세요.");
    }

    public static LocalTime readAttendanceRegisterAttendTime() {
        String response = prompt("등교 시간을 입력해 주세요.");
        return TimeConverter.convertStringToTime(response);
    }

    private static String generateAttendanceMenuHeader(LocalDate runDate) {
        return String.format("오늘은 %d월 %d일 %s요일입니다. 기능을 선택해 주세요.", runDate.getMonthValue(), runDate.getDayOfMonth(),
                DayOfWeekConverter.convertDayOfWeekToKorean(runDate.getDayOfWeek()));
    }

    public static String readAttendanceEditCrewName() {
        return prompt("\n출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
    }

    public static int readAttendanceEditAttendDay() {
        String response = prompt("수정하려는 날짜(일)를 입력해 주세요.");
        return DayConverter.convertDayToNumber(response);
    }

    public static LocalTime readAttendanceEditAttendTime() {
        String response = prompt("언제로 변경하겠습니까?");
        return TimeConverter.convertStringToTime(response);
    }

    public static String readCrewAttendanceCrewName() {
        return prompt("\n닉네임을 입력해 주세요.");
    }

    private static String generateAttendanceMenuOptions() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Menu menu : Menu.values()) {
            stringBuilder.append(System.lineSeparator())
                    .append(String.format("%s. %s", menu.getCode(), menu.getDescription()));
        }
        return stringBuilder.toString();
    }

    private static String prompt(String message) {
        System.out.println(message);
        return scanner.nextLine();
    }
}
