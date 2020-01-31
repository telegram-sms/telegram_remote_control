package com.qwe7002.root_kit;


public class network {
    private static void set_airplane_mode(boolean enable) {
        int set = 0;
        String state = "true";
        if (enable) {
            set = 1;
            state = "false";
        }
        shell.run_shell_command("settings put global airplane_mode_on " + set + " \nam broadcast -a android.intent.action.AIRPLANE_MODE --ez state " + state);
    }

    public static boolean wifi_set_enable(boolean enable) {
        String state = "disable";
        if (enable) {
            state = "enable";
        }
        return shell.run_shell_command("svc wifi " + state);
    }

    public static void data_set_enable(boolean enable) {
        String state = "disable";
        if (enable) {
            state = "enable";
        }
        shell.run_shell_command("svc data " + state);
    }

    public static void restart_network() {
        com.qwe7002.root_kit.network.set_airplane_mode(true);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        com.qwe7002.root_kit.network.set_airplane_mode(false);
    }
}
