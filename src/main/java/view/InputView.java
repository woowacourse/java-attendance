package view;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import model.AttendanceDate;
import model.AttendanceTime;
import model.Student;
import model.Students;
import util.AttendanceDateAttendanceTimeFormatter;

public class InputView {
    private static final String ATTENDANCE_CHECK_MENU = "1. 출석 확인";
    private static final String ATTENDANCE_MODIFY_MENU = "2. 출석 수정";
    private static final String RECORD_PRINT_FOR_EACH_CREW_MENU = "3. 크루별 출석 기록 확인";
    private static final String DISMISSAL_CREW_CHECK_MENU = "4. 제적 위험자 확인";
    private static final String QUIT = "Q";
    private static final String PROMPT_STUDENT_NAME_INPUT_TO_MODIFY = "출석을 수정하려는 크루의 닉네임을 입력해 주세요.";
    private static final String PROMPT_TIME_INPUT_TO_MODIFY = "언제로 변경하겠습니까?";
    private static final String PROMPT_DAY_INPUT_TO_MODIFY = "수정하려는 날짜(일)를 입력해 주세요.";
    private static final String PROMPT_STUDENT_NAME_INPUT = "닉네임을 입력해 주세요.";
    private static final String PROMPT_START_TIME_INPUT = "등교 시간을 입력해 주세요.";
    private static final String MENU_OPTION = "[1-4]|Q";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private static final Scanner scanner = new Scanner(System.in);

    public static String getUserWantMenuUntilValidate(AttendanceDate todayAttendanceDate) {
        try {
            printSpace();
            System.out.println(AttendanceDateAttendanceTimeFormatter.createTodayInformation(todayAttendanceDate));
            printMenu();
            String userMenu = userInput();
            if (!userMenu.matches(MENU_OPTION)) {
                throw new IllegalArgumentException("[ERROR] 메뉴는 1~4번, Q 만 존재합니다. 메뉴에 있는 선택지에서 골라주세요.");
            }
            return userMenu;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return getUserWantMenuUntilValidate(todayAttendanceDate);
        }
    }

    public static AttendanceTime getUserAttendanceTimeUntilValidateToAttendance() {
        printStartTime();
        return getUserAttendanceTimeUntilValidate();
    }

    public static AttendanceTime getUserAttendanceTimeUntilValidateToModify() {
        System.out.println(PROMPT_TIME_INPUT_TO_MODIFY);
        return getUserAttendanceTimeUntilValidate();
    }

    public static String getStudentNameUntilValidateToAttendance(Students students) {
        printSpace();
        System.out.println(PROMPT_STUDENT_NAME_INPUT);
        return getStudentNameUntilValidate(students);
    }

    public static String getStudentNameUntilValidateToModify(Students students) {
        printSpace();
        System.out.println(PROMPT_STUDENT_NAME_INPUT_TO_MODIFY);
        return getStudentNameUntilValidate(students);
    }

    public static AttendanceDate getUserAttendanceDateUntilValidate(Student student) {
        try {
            System.out.println(PROMPT_DAY_INPUT_TO_MODIFY);
            String date = userInput();
            if (!date.matches("^(0?[1-9]|[12][0-9]|3[01])$")) {
                throw new IllegalArgumentException("[ERROR] 1 ~ 31 사이의 숫자만 입력해 주세요.");
            }
            AttendanceDate attendanceDate = new AttendanceDate(LocalDate.of(2024, 12, Integer.parseInt(date)));
            student.validateAttendanceBeforeModification(attendanceDate);
            return attendanceDate;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return getUserAttendanceDateUntilValidate(student);
        }
    }

    private static void printStartTime() {
        System.out.println(PROMPT_START_TIME_INPUT);
    }

    private static AttendanceTime getUserAttendanceTimeUntilValidate() {
        try {
            String userTime = userInput();
            AttendanceTime attendanceTime = new AttendanceTime(LocalTime.parse(userTime, DATE_TIME_FORMATTER));
            attendanceTime.isNotOpeningTime();
            return attendanceTime;
        } catch (DateTimeException e) {
            System.out.println("[ERROR] 시간 형식에 맞지 않습니다. HH:MM 형식으로 입력해 주세요");
            return getUserAttendanceTimeUntilValidate();
        }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return getUserAttendanceTimeUntilValidate();
        }
    }

    private static String getStudentNameUntilValidate(Students students) {
        try {
            String studentName = userInput();
            if(!students.isExistStudent(studentName)) {
                throw new IllegalArgumentException("[ERROR] 존재하지 않는 학생입니다. 다시 입력해 주세요.");
            }
            return studentName;
        }catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return getStudentNameUntilValidate(students);
        }
    }

    private static void printMenu() {
        System.out.println(ATTENDANCE_CHECK_MENU);
        System.out.println(ATTENDANCE_MODIFY_MENU);
        System.out.println(RECORD_PRINT_FOR_EACH_CREW_MENU);
        System.out.println(DISMISSAL_CREW_CHECK_MENU);
        System.out.println(QUIT);
    }

    private static String userInput(){
        return scanner.nextLine();
    }

    private static void printSpace() {
        System.out.println();
    }
}
