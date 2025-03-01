package attendance.view;

import attendance.dto.CheckExpulsionResultDto;

import java.util.List;

public class CheckAllExpulsionCrewView {

    public void printTitle() {
        System.out.println("제적 위험자 조회 결과");
    }

    public void printCrewExpulsions(List<CheckExpulsionResultDto> expulsionResults) {
        ExpulsionStatusTextMaker expulsionStatusTextMaker = new ExpulsionStatusTextMaker();
        expulsionResults.sort((o1, o2) -> {
            final long firstAllAbsents = o1.absentCount() + (o1.lateCount() / 3);
            final long secondAllAbsents = o2.absentCount() + (o2.lateCount() / 3);
            if (o1 == o2) {
                return o1.nickname().compareTo(o2.nickname());
            }
            return Math.toIntExact(secondAllAbsents - firstAllAbsents);
        });
        for (CheckExpulsionResultDto expulsionResult : expulsionResults) {
            System.out.println("- %s: 결석 %d회, 지각 %d회 (%s)".formatted(expulsionResult.nickname(),
                    expulsionResult.absentCount(), expulsionResult.lateCount(),
                    expulsionStatusTextMaker.make(expulsionResult.expulsionStatus())));
        }
    }
}
