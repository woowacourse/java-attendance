package view;

import java.time.LocalDateTime;

import dto.AttendanceResult;
import dto.Formatter;
import dto.ModifiedResult;

public class OutputView {
    public static void printAttendanceResult(AttendanceResult result) {
        System.out.println(LocalDateTime.of(result.date(), result.time()).format(Formatter.DATETIME_FORMATTER));
    }

    public static void printModifiedResult(ModifiedResult modifiedResult) {
        StringBuilder sb = new StringBuilder();
        sb.append(
            LocalDateTime.of(modifiedResult.date(), modifiedResult.before()).format(Formatter.DATETIME_FORMATTER));
        sb.append(String.format(" (%s) -> ", modifiedResult.beforeStatus().getDescription()));
        sb.append(modifiedResult.after().format(Formatter.TIME_FORMATTER));
        sb.append(String.format(" (%s)", modifiedResult.afterStatus().getDescription()));
        sb.append(" 수정 완료!");

        System.out.println(sb.toString());
    }
}
