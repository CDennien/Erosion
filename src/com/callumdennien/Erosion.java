package com.callumdennien;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.WinDef.UINT_PTR;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.win32.W32APIFunctionMapper;
import com.sun.jna.win32.W32APITypeMapper;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Erosion {
    private static final SoupBowl soupBowl = new SoupBowl();
    private static final String path = "C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Roaming\\Erosion\\erosion_background.jpg";

    public static void main(String[] args) {
        final TrayIcon trayIcon;

        if (SystemTray.isSupported()) {
            SystemTray tray = SystemTray.getSystemTray();
            String logoPath = "C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Roaming\\Erosion\\logo.png";
            Image image = Toolkit.getDefaultToolkit().getImage(logoPath);

            ActionListener changeListener = e -> {
                try {
                    changeBackground();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            };

            ActionListener infoListener = e -> {
                JFrame frame = new JFrame();

                JLabel imageLabel = new JLabel();
                imageLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

                try {
                    BufferedImage img = ImageIO.read(new File(logoPath));
                    Image imageScale = img.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
                    ImageIcon icon = new ImageIcon(imageScale);
                    imageLabel.setIcon(icon);

                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

                JLabel versionLabel = new JLabel("Version: 1.0.1  ");
                JLabel redditLabel = new JLabel("Reddit: r/wallpaper");
                JLabel devLabel = new JLabel("Developer: Callum Dennien");

                JPanel textPanel = new JPanel();
                textPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
                textPanel.add(imageLabel);
                textPanel.add(versionLabel);
                textPanel.add(redditLabel);
                textPanel.add(devLabel);

                frame.add(textPanel, BorderLayout.CENTER);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setTitle("Erosion Backgrounds");
                frame.pack();
                frame.setSize(new Dimension(400, 420));
                frame.setResizable(false);
                frame.setVisible(true);
                frame.setLocationRelativeTo(null);

            };

            ActionListener exitListener = e -> System.exit(0);

            MenuItem exitItem = new MenuItem("Exit");
            exitItem.addActionListener(exitListener);
            MenuItem infoItem = new MenuItem("Info");
            infoItem.addActionListener(infoListener);
            MenuItem changeItem = new MenuItem("Change Wallpaper");
            changeItem.addActionListener(changeListener);

            PopupMenu popup = new PopupMenu();
            popup.add(changeItem);
            popup.add(infoItem);
            popup.add(exitItem);

            try {
                trayIcon = new TrayIcon(image, "Erosion Wallpapers", popup);
                trayIcon.setImageAutoSize(true);
                tray.add(trayIcon);

            } catch (AWTException e) {
                System.err.println("TrayIcon could not be added.");
            }

        }
    }

    private static void changeBackground() throws IOException {
        String postURL = soupBowl.getSoupTopping();
        soupBowl.spillSoup(postURL, path);

        SPI.INSTANCE.SystemParametersInfo(new UINT_PTR(SPI.SPI_SETDESKWALLPAPER), new UINT_PTR(0), path, new UINT_PTR(SPI.SPIF_UPDATEINIFILE | SPI.SPIF_SENDWININICHANGE));
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
