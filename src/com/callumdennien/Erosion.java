package com.callumdennien;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.WinDef.UINT_PTR;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.win32.W32APIFunctionMapper;
import com.sun.jna.win32.W32APITypeMapper;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;

public class Erosion {
    private static final SoupBowl soupBowl = new SoupBowl();
    private static final String path = "C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\Erosion\\erosion_background.jpg";

    public static void main(String[] args) throws Throwable {
        String postURL = soupBowl.getSoupTopping();
        soupBowl.spillSoup(postURL, path);

        SPI.INSTANCE.SystemParametersInfo(new UINT_PTR(SPI.SPI_SETDESKWALLPAPER), new UINT_PTR(0), path, new UINT_PTR(SPI.SPIF_UPDATEINIFILE | SPI.SPIF_SENDWININICHANGE));

        final TrayIcon trayIcon;

        if (SystemTray.isSupported()) {
            SystemTray tray = SystemTray.getSystemTray();
            Image image = Toolkit.getDefaultToolkit().getImage("C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\Erosion\\logo.png");

            ActionListener exitListener = e -> {
                System.exit(0);
            };

            ActionListener changeListener = e -> {
                System.exit(0);
            };

            MenuItem defaultItem = new MenuItem("Exit");
            defaultItem.addActionListener(exitListener);
            MenuItem change = new MenuItem("Change");
            change.addActionListener(changeListener);

            PopupMenu popup = new PopupMenu();
            popup.add(change);
            popup.add(defaultItem);

            try {
                trayIcon = new TrayIcon(image, "Erosion", popup);
                trayIcon.setImageAutoSize(true);
                tray.add(trayIcon);

            } catch (AWTException e) {
                System.err.println("TrayIcon could not be added.");
            }

        }
    }

    public interface SPI extends StdCallLibrary {
        long SPI_SETDESKWALLPAPER = 20;
        long SPIF_UPDATEINIFILE = 0x01;
        long SPIF_SENDWININICHANGE = 0x02;

        SPI INSTANCE = Native.load("user32", SPI.class, new HashMap<>() {
            {
                put(OPTION_TYPE_MAPPER, W32APITypeMapper.UNICODE);
                put(OPTION_FUNCTION_MAPPER, W32APIFunctionMapper.UNICODE);
            }
        });

        void SystemParametersInfo(
                UINT_PTR uiAction,
                UINT_PTR uiParam,
                String pvParam,
                UINT_PTR fWinIni
        );
    }
}
