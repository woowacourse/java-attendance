import java.util.Arrays;

public enum FunctionOption {
    REGISTER_ATTENDANCE("1"),
    UPDATE_ATTENDANCE("2"),
    CHECK_ATTENDANCE_HISTORY_OF_CREW("3"),
    CHECK_EXPULSION_CANDIDATES("4"),
    QUIT("Q");

    private final String sign;

    FunctionOption(String sign) {
        this.sign = sign;
    }

    public static FunctionOption findBySign(String comparedSign) {
        return Arrays.stream(values())
                .filter(option -> option.sign.equals(comparedSign))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 기능 선택지입니다."));
    }
}
