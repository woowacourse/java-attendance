package attendance.view;

import attendance.dto.CheckExpulsionResultDto;

import java.util.List;

public class CheckAllExpulsionCrewView {

    public void printTitle() {
        System.out.println("제적 위험자 조회 결과");
    }

    public void printCrewExpulsions(final List<CheckExpulsionResultDto> expulsionResults) {
        sortExpulsionResults(expulsionResults);
        ExpulsionStatusTextMaker expulsionStatusTextMaker = new ExpulsionStatusTextMaker();
        for (CheckExpulsionResultDto expulsionResult : expulsionResults) {
            System.out.println("- %s: 결석 %d회, 지각 %d회 (%s)".formatted(expulsionResult.nickname(),
                    expulsionResult.absentCount(), expulsionResult.lateCount(),
                    expulsionStatusTextMaker.make(expulsionResult.expulsionStatus())));
        }
    }

    private static void sortExpulsionResults(final List<CheckExpulsionResultDto> expulsionResults) {
        expulsionResults.sort((o1, o2) -> {
            long firstAllAbsentCount = o1.allAbsents();
            long secondAllAbsentCount = o2.allAbsents();
            if (firstAllAbsentCount == secondAllAbsentCount) {
                return o1.nickname().compareTo(o2.nickname());
            }
            return Math.toIntExact(secondAllAbsentCount - firstAllAbsentCount);
        });
    }
}
