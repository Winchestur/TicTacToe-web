package com.game.tictactoe;

import com.cyecize.javache.embedded.JavacheEmbedded;
import com.cyecize.summer.DispatcherSolet;
import com.cyecize.summer.SummerBootApplication;


public class StartUp extends DispatcherSolet {

    static int port = 8000;

    public StartUp() {
        SummerBootApplication.run(this);
        MainFrame.run(port);
    }

    public static void main(String[] args) {
        JavacheEmbedded.startServer(port, StartUp.class);
    }
}
