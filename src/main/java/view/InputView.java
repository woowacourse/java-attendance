package view;

import static util.LocalDateTimePrintFormatter.dateTimeFormatterForHourMin;

import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import model.AttendanceCalculatorByDay;

public class InputView {
    private final static String ATTENDANCE_CHECK_MENU = "1. 출석 확인";
    private final static String ATTENDANCE_MODIFY_MENU = "2. 출석 수정";
    private final static String RECORD_PRINT_FOR_EACH_CREW_MENU = "3. 크루별 출석 기록 확인";
    private final static String DISMISSAL_CREW_CHECK_MENU = "4. 제적 위험자 확인";
    private final static String QUICK = "Q";
    private final static String PROMPT_TIME_INPUT_TO_MODIFY = "언제로 변경하겠습니까?";
    private final static String PROMPT_DAY_INPUT_TO_MODIFY = "수정하려는 날짜(일)를 입력해 주세요.";
    private final static String PROMPT_STUDENT_NAME_INPUT_TO_MODIFY = "출석을 수정하려는 크루의 닉네임을 입력해 주세요.";
    private final static String PROMPT_STUDENT_NAME_INPUT = "닉네임을 입력해 주세요.";
    private final static String PROMPT_START_TIME_INPUT = "등교 시간을 입력해 주세요.";
    private final static String MENU_OPTION = "[1-4]|Q";
    private final static String PRINT_TODAY_FORMAT = "오늘은 %d월 %d일 %s입니다. 기능을 선택해 주세요.\n";
    private final static Scanner scanner = new Scanner(System.in);

    public static void printTodayAndSelectFunction(LocalDate localDate) {
        int month = localDate.getMonthValue();
        int date = localDate.getDayOfMonth();
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        String day = AttendanceCalculatorByDay.findDayByDayOfWeekValue(dayOfWeek.getValue());
        System.out.printf(String.format(PRINT_TODAY_FORMAT,month,date,day));
    }

    private static void printMenu() {
        System.out.println(ATTENDANCE_CHECK_MENU);
        System.out.println(ATTENDANCE_MODIFY_MENU);
        System.out.println(RECORD_PRINT_FOR_EACH_CREW_MENU);
        System.out.println(DISMISSAL_CREW_CHECK_MENU);
        System.out.println(QUICK);
    }

    public static String userInput(){
        return scanner.nextLine();
    }
    public static String getUserInputString(){
        printMenu();
        String input = userInput();
        try{
            return isQOrOneOrTwoOrThreeOrFour(input);
        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
            return getUserInputString();
        }
    }

    private static String isQOrOneOrTwoOrThreeOrFour(String input){
        if (!input.matches(MENU_OPTION)) {
            throw new IllegalArgumentException("[ERROR] 메뉴에 없는 선택지 입니다.");
        }
        return input;
    }

    public static void printInputNicName(){
        System.out.println(PROMPT_STUDENT_NAME_INPUT);
    }

    public static void printStartTime(){
        System.out.println(PROMPT_START_TIME_INPUT);
    }

    public static void printStudentNameForModify(){
        System.out.println(PROMPT_STUDENT_NAME_INPUT_TO_MODIFY);
    }

    public static int inputDateForModify(){
        System.out.println(PROMPT_DAY_INPUT_TO_MODIFY);
        try{
            int date = Integer.parseInt(userInput());
            if(date < 1 || date > 31){
                throw new IllegalArgumentException("[ERROR] 1~31 사이의 숫자만 입력해주세요");
            }
            return date;
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
            return inputDateForModify();
        }
    }

    public static void printTimeForModify(){
        System.out.println(PROMPT_TIME_INPUT_TO_MODIFY);
    }

    public static LocalDateTime makeLocalDateToLocalDateTime(LocalDate localDate) {
        String time = userInput();
        try {
            LocalTime localTime = LocalTime.parse(time,dateTimeFormatterForHourMin);
            return localDate.atTime(localTime);
        } catch (DateTimeException e) {
            throw new IllegalArgumentException("[ERROR] 시간 형식에 맞지 않습니다.");
        }
    }

    public static void isNotOpeningHour(LocalDateTime localDateTime) {
        if (localDateTime.getHour() < 8 || localDateTime.getHour() >= 23) {
            throw new IllegalArgumentException("[ERROR] 캠퍼스 운영 시간이 아닙니다.");
        }
    }

}
