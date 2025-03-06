package domain;

import domain.vo.PenaltyTarget;
import java.util.Comparator;

public class PenaltyTargetComparator implements Comparator<PenaltyTarget> {

    @Override
    public int compare(PenaltyTarget o1, PenaltyTarget o2) {
        int totalAbsences1 = CrewPenaltyPolicy.calculateTotalAbsence(o1.lateCount(), o1.absenceCount());
        int totalAbsences2 = CrewPenaltyPolicy.calculateTotalAbsence(o2.lateCount(), o2.absenceCount());

        int result = Integer.compare(totalAbsences2, totalAbsences1);

        if (result == 0) {
            return o1.nickName().compareTo(o2.nickName());
        }

        return result;
    }
}
