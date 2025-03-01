package domain;

import java.util.Map;
import java.util.Objects;
import service.dto.RiskCrewsResponse.RiskCrew;

public class Crew {

    private final String nickname;

    public Crew(String nickname) {
        validateNickname(nickname);
        this.nickname = nickname;
    }

    private void validateNickname(String nickname) {
        if (nickname.isBlank()) {
            throw new IllegalArgumentException("닉네임은 빈 값일 수 없습니다.");
        }
    }

    public RiskCrew convertToRiskCrew(Map<AttendanceStatus, Integer> statusCount) {
        return new RiskCrew(nickname,
                statusCount.getOrDefault(AttendanceStatus.LATE, 0),
                statusCount.getOrDefault(AttendanceStatus.ABSENT, 0),
                RiskRank.from(statusCount));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Crew crew = (Crew) o;
        return Objects.equals(nickname, crew.nickname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nickname);
    }

    public String getNickname() {
        return nickname;
    }
}
