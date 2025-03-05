package model.policy;

import static model.AttendanceType.ABSENT;
import static model.AttendanceType.LATE;

import dto.DismissalCrewDto;
import java.util.Comparator;

public enum DismissalCrewSortPolicy {
    BY_DISMISSAL_TYPE(Comparator.comparingInt(crewInfo -> crewInfo.dismissalType().getPriorityOrder())),
    BY_LATE_COUNT(Comparator.comparing(crewInfo ->
                    crewInfo.typeCountResult().get(ABSENT) * 3 + crewInfo.typeCountResult().get(LATE),
            Comparator.reverseOrder())),
    BY_NICKNAME(Comparator.comparing(DismissalCrewDto::nickname));

    private final Comparator<DismissalCrewDto> comparator;

    DismissalCrewSortPolicy(final Comparator<DismissalCrewDto> comparator) {
        this.comparator = comparator;
    }

    public static Comparator<DismissalCrewDto> getComparator() {
        return BY_DISMISSAL_TYPE.comparator.thenComparing(
                BY_LATE_COUNT.comparator.thenComparing(BY_NICKNAME.comparator));
    }
}
