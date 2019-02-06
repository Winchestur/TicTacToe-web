package com.game.tictactoe;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

import org.cef.CefApp;
import org.cef.CefApp.CefAppState;
import org.cef.CefClient;
import org.cef.CefSettings;
import org.cef.browser.CefBrowser;
import org.cef.handler.CefAppHandlerAdapter;

public class MainFrame extends JFrame {

    private MainFrame(String startURL, boolean useOSR, boolean isTransparent) {
        CefApp.addAppHandler(new CefAppHandlerAdapter(null) {
            @Override
            public void stateHasChanged(CefAppState state) {
                if (state == CefAppState.TERMINATED) System.exit(0);
            }
        });

        final CefSettings settings = new CefSettings();
        settings.windowless_rendering_enabled = useOSR;

        final CefApp cefApp = CefApp.getInstance(settings);

        final CefClient client = cefApp.createClient();

        final CefBrowser browser = client.createBrowser(startURL, useOSR, isTransparent);
        final Component browserUI = browser.getUIComponent();

        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("../../../favicon.ico")));
        this.setTitle("TicTacToe");

        this.getContentPane().add(browserUI, BorderLayout.CENTER);
        this.pack();
        this.setSize(1000, 600);
        this.setVisible(true);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                CefApp.getInstance().dispose();
                dispose();
            }
        });
    }

    public static void run(int port) {
        if (!CefApp.startup()) {
            System.out.println("Startup initialization failed!");
            return;
        }

        boolean useOsr = false;
        new MainFrame("http://localhost:" + port, useOsr, false);
    }
}