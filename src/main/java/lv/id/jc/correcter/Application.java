package lv.id.jc.correcter;

import java.util.Map;
import java.util.Scanner;

public record Application(Map<String, Runnable> commands) implements Runnable {

    @Override
    public void run() {
        System.out.print("Write a mode: ");
        var mode = new Scanner(System.in).nextLine();
        commands().get(mode).run();
    }
}
