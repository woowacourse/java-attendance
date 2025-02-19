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
}
