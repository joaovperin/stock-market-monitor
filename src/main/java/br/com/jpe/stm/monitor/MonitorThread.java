/*
 * Copyright (C) 2020 Perin
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package br.com.jpe.stm.monitor;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class MonitorThread implements Runnable {

    private static final String IMAGES_DIR = System.getProperty("user.home") + "\\Pictures\\StockPaperPictures\\";

    private static Thread monitorThread;
    private final JFrame mainWindow;

    private MonitorThread(JFrame mainWindow) {
        this.mainWindow = mainWindow;
    }

    public static final void startMonitor(JFrame mainWindow) {
        if (monitorThread == null) {
            monitorThread = new Thread(new MonitorThread(mainWindow));
        }
        monitorThread.start();
    }

    public static final void stopMonitor() {
    }

    @Override
    public void run() {
        int num = 1;
        while (true) {
            final String img_1 = "paper-";
            mainWindow.setIconImage(new ImageIcon(IMAGES_DIR.concat(img_1 + num + ".png")).getImage());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
            }
            if (++num == 3) {
                num = 1;
            }
        }
    }

}
