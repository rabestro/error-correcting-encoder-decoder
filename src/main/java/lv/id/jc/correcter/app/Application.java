package lv.id.jc.correcter.app;

import java.util.Map;
import java.util.Scanner;

public record Application(Map<String, Runnable> commands) implements Runnable {

    @Override
    @SuppressWarnings("squid:S106")
    public void run() {
        System.out.print("Write a mode: ");
        var mode = new Scanner(System.in).nextLine();
        commands().get(mode).run();
    }
}
