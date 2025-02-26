package view;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import model.Students;
import model.TodayDate;

public class InputView {
    private static final String ATTENDANCE_CHECK_MENU = "1. 출석 확인";
    private static final String ATTENDANCE_MODIFY_MENU = "2. 출석 수정";
    private static final String RECORD_PRINT_FOR_EACH_CREW_MENU = "3. 크루별 출석 기록 확인";
    private static final String DISMISSAL_CREW_CHECK_MENU = "4. 제적 위험자 확인";
    private static final String QUICK = "Q";
    private static final String PROMPT_TIME_INPUT_TO_MODIFY = "언제로 변경하겠습니까?";
    private static final String PROMPT_DAY_INPUT_TO_MODIFY = "수정하려는 날짜(일)를 입력해 주세요.";
    private static final String PROMPT_STUDENT_NAME_INPUT_TO_MODIFY = "출석을 수정하려는 크루의 닉네임을 입력해 주세요.";
    private static final String PROMPT_STUDENT_NAME_INPUT = "닉네임을 입력해 주세요.";
    private static final String PROMPT_START_TIME_INPUT = "등교 시간을 입력해 주세요.";
    private static final String MENU_OPTION = "[1-4]|Q";
    private static final String PRINT_TODAY_FORMAT = "오늘은 %d월 %d일 %s입니다. 기능을 선택해 주세요.\n";
    private static final DateTimeFormatter dateTimeFormatterForHourMin = DateTimeFormatter.ofPattern("HH:mm");
    private static final LocalTime START_TIME = LocalTime.of(8, 0);
    private static final LocalTime END_TIME = LocalTime.of(23, 0);
    private static final int DECEMBER_START_DATE = 1;
    private static final int DECEMBER_ENT_DATE = 31;
    private static Scanner scanner = new Scanner(System.in);

    public static void setScanner(Scanner newScanner) {
        scanner = newScanner;
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

    public static String getUserWantMenu(TodayDate todayDate){
        System.out.printf(PRINT_TODAY_FORMAT, todayDate.getTodayDate().getMonth().getValue(), todayDate.getTodayDate().getDayOfMonth()
                , todayDate.getTodayDay());
        printMenu();
        String input = userInput();
        try{
            return isMenuOption(input);
        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
            return getUserWantMenu(todayDate);
        }
    }

    public static String isMenuOption(String input){
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
            if(date < DECEMBER_START_DATE || date > DECEMBER_ENT_DATE){
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
        if (localDateTime.toLocalTime().isBefore(START_TIME) || localDateTime.toLocalTime().isAfter(END_TIME)) {
            throw new IllegalArgumentException("[ERROR] 캠퍼스 운영 시간이 아닙니다.");
        }
    }

    public static LocalDateTime getLocalDateTimeToModify() {
        int modifyDate = InputView.inputDateForModify();
        InputView.printTimeForModify();
        LocalDate localDate = LocalDate.of(2024,  12,  modifyDate);
        return getTimeUntilValidate(localDate);
    }

    public static String getStudentNameForModifyUntilValidate(Students studentRepository) {
        try {
            InputView.printInputNicName();
            return getStudentNameUntilExist(studentRepository);
        } catch (IllegalArgumentException e) {
            return getStudentNameForModifyUntilValidate(studentRepository);
        }
    }

    public static LocalDateTime getLocalDateTimeUntilValidate(TodayDate todayDate) {
        try {
            InputView.printStartTime();
            return getTimeUntilValidate(todayDate.getTodayDate());
        } catch (IllegalArgumentException e) {
            return getLocalDateTimeUntilValidate(todayDate);
        }
    }

    public static String getStudentForAttendanceCheckUntilExist(Students studentRepository) {
        InputView.printInputNicName();
        try {
            return getStudentNameUntilExist(studentRepository);
        }
        catch (IllegalArgumentException e) {
            return getStudentForAttendanceCheckUntilExist(studentRepository);
        }
    }

    public static String getStudentNameUntilExist(Students studentRepository) {
        String userName = InputView.userInput();
        try{
            if(!studentRepository.isExistStudent(userName)) {
                throw new IllegalArgumentException("[ERROR] 존재하지 않는 학생입니다.");
            }
            return userName;
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
            throw new IllegalArgumentException();
        }
    }

    public static LocalDateTime getTimeUntilValidate(LocalDate localDate) {
        try{
            LocalDateTime localDateTimeToAttendanceCheck = InputView.makeLocalDateToLocalDateTime(localDate);
            InputView.isNotOpeningHour(localDateTimeToAttendanceCheck);
            return localDateTimeToAttendanceCheck;
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
            throw new IllegalArgumentException();
        }
    }
}
