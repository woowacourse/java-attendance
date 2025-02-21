import config.AppConfig;
import controller.facade.MainController;

public class Application {
    public static void main(String[] args) {
        AppConfig config = new AppConfig();
        MainController controller = config.getMainController();
        controller.run();
    }
}
