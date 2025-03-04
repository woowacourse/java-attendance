package view;

import domain.WarningStatus;
import java.util.Arrays;

public enum WarningStatusFormatter {
    PASS(WarningStatus.PASS, ""),
    WARNING(WarningStatus.WARNING, "경고"),
    INTERVIEW(WarningStatus.INTERVIEW, "면담"),
    EXPEL(WarningStatus.EXPEL, "제적");

    private final WarningStatus warningStatus;
    private final String text;

    WarningStatusFormatter(final WarningStatus warningStatus, final String text) {
        this.warningStatus = warningStatus;
        this.text = text;
    }

    public static String findStatusText(WarningStatus targetStatus) {
        return Arrays.stream(WarningStatusFormatter.values())
                .filter(formatter -> formatter.warningStatus == targetStatus)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("경고 대상자 formatting 실패"))
                .text;
    }
}
