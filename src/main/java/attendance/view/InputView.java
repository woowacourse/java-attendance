package attendance.view;

import attendance.domain.OperationCommand;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import static attendance.view.ViewConstants.*;

public class InputView {

    private Scanner scanner = new Scanner(System.in);

    public OperationCommand readOperationCommand() {
        String commandText = scanner.nextLine();
        return OperationCommand.from(commandText);
    }

    public String readCrewNickname() {
        System.out.println("닉네임을 입력해 주세요.");
        return scanner.nextLine();
    }

    public LocalTime readAttendanceTime() {
        System.out.println("등교 시간을 입력해 주세요.");
        return parseLocalTime();
    }

    private LocalTime parseLocalTime() {
        try {
            String input = scanner.nextLine();
            return LocalTime.parse(input, TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("올바른 시간 입력 형식이 아닙니다.");
        }
    }

    public String readModificationCrewNickname() {
        System.out.println("출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
        return scanner.nextLine();
    }

    public LocalDate readModificationDay(LocalDate today) {
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
        try {
            int day = Integer.parseInt(scanner.nextLine());
            LocalDate modificationDate = LocalDate.of(today.getYear(), today.getMonth(), day);
            validateFutureDate(today, modificationDate);
            return modificationDate;
        } catch (NumberFormatException numberFormatException) {
            throw new IllegalArgumentException("올바른 숫자를 입력해 주세요.");
        } catch (DateTimeException exception) {
            throw new IllegalArgumentException("잘못된 날짜입니다.");
        }
    }

    private static void validateFutureDate(LocalDate today, LocalDate modificationDate) {
        if (modificationDate.isAfter(today)) {
            throw new IllegalArgumentException("미래의 날짜는 입력할 수 없습니다.");
        }
    }

    public LocalTime readModificationTime() {
        System.out.println("언제로 변경하겠습니까?");
        return parseLocalTime();
    }
}
