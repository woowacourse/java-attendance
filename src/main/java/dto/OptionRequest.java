package dto;

import java.util.List;

public record OptionRequest(
    String option
) {
    public OptionRequest {
        validateOption(option);
    }

    private void validateOption(String option) {
        List<String> options = List.of("1", "2", "3", "4", "q", "Q");
        if (!options.contains(option)) {
            throw new IllegalArgumentException("존재하지 않는 기능입니다.");
        }
    }
}
