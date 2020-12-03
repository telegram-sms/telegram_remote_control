package com.qwe7002.root_kit;

import android.util.Log;

public class nadb {
    public static boolean set_nadb(int port) {
        final String TAG = "nadb";
        if (port > 65535) {
            Log.i(TAG, "The port number is greater than 65535.");
            return false;
        }
        return shell.run_shell_command("setprop service.adb.tcp.port " + port + "\nstop adbd\nstart adbd");
    }

    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static boolean get_adb_auth(String key) {
        return shell.run_shell_command("stop adbd\necho \"" + key + "\" >> /data/misc/adb/adb_keys\nstart adbd");
    }
}
