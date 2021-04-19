package com.example.meks_enginering.utility;

import java.io.IOException;

public class urls {
    public static String meks() {
        return "https://meksnigerialtd.com/";
    }

    public static String securityKey() {
        return "MEKS_LIVE-iZ6QwzPFK62tx87CrQe4decmjzWk7TarMgLuIwov46ltaOE0CzzG";
    }

    public static boolean isConnected() throws InterruptedException, IOException {
        return Runtime.getRuntime().exec("ping -i 5 -c 1 google.com").waitFor() == 0;
    }
}
