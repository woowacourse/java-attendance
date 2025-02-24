package util.inputProvider;

import java.util.Scanner;

public class DefaultInputProvider implements InputProvider {
    
    @Override
    public String get() {
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }
}
