package view;

import controller.Command;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import static controller.Command.*;
import static util.DateTimeUtils.*;

public class InputView {
    private static final Scanner scanner = new Scanner(System.in);

    public static Command getCommand() {
        System.out.println("1. 출석 확인");
        System.out.println("2. 출석 수정");
        System.out.println("3. 크루별 출석 기록 확인");
        System.out.println("4. 제적 위험자 확인");
        System.out.println("Q. 종료");

        String commandLine = scanner.nextLine();
        return convertToCommand(commandLine);
    }

    public static String getCrewName(){
        System.out.println("닉네임을 입력해 주세요.");

        return scanner.nextLine();
    }

    public static LocalDateTime getAttendTime(){
        String input = scanner.nextLine();
        try{
            System.out.println("등교 시간을 입력해 주세요.");
            LocalDate today = LocalDate.now();
            LocalTime time = LocalTime.parse(input, dateTimeformatter);
            return LocalDateTime.of(today,time);
        } catch (DateTimeParseException e){
            throw new IllegalArgumentException("[ERROR] 입력 시간의 형식이 옳바르지 않습니다.");
        }
    }
}
