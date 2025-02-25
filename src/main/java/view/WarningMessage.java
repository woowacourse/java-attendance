package view;

import domain.WarningStatus;
import java.util.Arrays;

public enum WarningMessage {
    CLEAR(WarningStatus.CLEAR, "", ""),
    WARNING(WarningStatus.WARNING, "경고", "경고 대상자 입니다."),
    INTERVIEW(WarningStatus.INTERVIEW, "면담", "면담 대상자 입니다."),
    EXPEL(WarningStatus.EXPEL, "제적", "제적 대상자 입니다.");

    private final WarningStatus warningStatus;
    private final String message;
    private final String longMessage;

    WarningMessage(WarningStatus warningStatus, String message, String longMessage) {
        this.warningStatus = warningStatus;
        this.message = message;
        this.longMessage = longMessage;
    }

    public static String formatWarningStatusShort(WarningStatus warningStatus) {
        return Arrays.stream(WarningMessage.values())
                .filter(warningMessage -> warningMessage.warningStatus == warningStatus)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("경고 판정에 실패함"))
                .message;
    }

    public static String formatWarningStatus(WarningStatus warningStatus) {
        return Arrays.stream(WarningMessage.values())
                .filter(warningMessage -> warningMessage.warningStatus == warningStatus)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("경고 대상자 판정에 실패함"))
                .longMessage;
    }
}
