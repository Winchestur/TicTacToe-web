package com.game.tictactoe;

import com.cyecize.javache.embedded.JavacheEmbedded;
import com.cyecize.summer.DispatcherSolet;
import com.cyecize.summer.SummerBootApplication;

public class StartUp extends DispatcherSolet {
    public StartUp() {
        SummerBootApplication.run(this);
    }

    public static void main(String[] args) {
        JavacheEmbedded.startServer(8000, StartUp.class);
    }
}
