package controller;

import domain.Attend;
import domain.AttendReader;
import domain.AttendStatus;
import domain.AttendanceBook;
import domain.AttendanceResults;
import domain.Current;
import domain.WarningCrew;
import java.util.List;
import util.DateUtil;
import view.InputView;
import view.OutputView;

public class AttendanceController {

    private final InputView inputView;
    private final OutputView outputView;

    public AttendanceController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        while (true) {
        AttendanceBook attendanceBook = loadAttendanceBook();
            try {

                String command = inputView.inputCommand(Current.TODAY.getLocalDate());
                if (command.equals("Q")) {
                    break;
                }
                if (command.equals("1")) {
                    String nickName = inputView.inputNickName();
                    String time = inputView.inputTime();
                    Attend attend = Attend.of(time);
                    attendanceBook.attend(nickName, attend);
                    AttendStatus attendStatus = attendanceBook.checkAttendance(attend);
                    outputView.printAttendResult(attend, attendStatus);
                    continue;
                }
                if (command.equals("2")) {
                    String nickName = inputView.inputEditNickName();
                    String date = inputView.inputDate();
                    String time = inputView.inputEditTime();
                    Attend after = Attend.of(date, time);
                    int parsedDate = Integer.parseInt(date);
                    Attend before = attendanceBook.findByNameAndDay(nickName, parsedDate);
                    attendanceBook.edit(nickName, after);
                    AttendStatus beforeStatus = attendanceBook.checkAttendance(before);
                    AttendStatus afterStatus = attendanceBook.checkAttendance(after);
                    outputView.printEditResult(before, after, beforeStatus, afterStatus);
                    continue;
                }
                if (command.equals("3")) {
                    String nickName = inputView.inputNickName();
                    AttendanceResults attendResult = attendanceBook.checkAttendance(nickName,
                            DateUtil.getAttendUntilDay(Current.TODAY.getYesterday()));
                    outputView.printAttendanceResult(nickName, attendResult);
                    continue;
                }
                if (command.equals("4")) {
                    List<WarningCrew> warningCrews = attendanceBook.checkWarningCrews(
                            DateUtil.getAttendUntilDay(Current.TODAY.getYesterday()));
                    outputView.printWarningCrews(warningCrews);
                }

            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private AttendanceBook loadAttendanceBook() {
        AttendReader attendReader = new AttendReader();
        return attendReader.loadAttendanceBook();
    }

    }

    }

    }

    }
}
