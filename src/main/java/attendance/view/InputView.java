package attendance.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class InputView {
    private static final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    public static String readNickName() throws IOException {
        System.out.println("닉네임을 입력해 주세요.");
        return bufferedReader.readLine();
    }

    public static LocalTime readAttendanceTime() throws IOException {
        System.out.println("등교 시간을 입력해 주세요.");
        try {
            return LocalTime.parse(bufferedReader.readLine());
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("잘못된 시간 형식입니다. HH:mm 형식으로 입력해주세요.");
        }
    }

    public static String readModifyNickName() throws IOException {
        System.out.println("출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
        return bufferedReader.readLine();
    }

    public static int readModifyDay() throws IOException {
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
        try{
            return Integer.parseInt(bufferedReader.readLine());
        } catch (NumberFormatException e){
            throw new IllegalArgumentException("[ERROR] 날짜를 숫자로 입력해주세요.");
        }
    }

    public static LocalTime readModifyTime() throws IOException {
        System.out.println("언제로 변경하겠습니까?");
        try{
            return LocalTime.parse(bufferedReader.readLine());
        } catch (DateTimeParseException e){
            throw new IllegalArgumentException("[ERROR] 시간을 HH:mm 형식으로 입력해주세요.");
        }
    }
}
