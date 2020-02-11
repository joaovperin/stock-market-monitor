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

import br.com.jpe.stm.img.PaperImageCreator;
import javax.swing.JFrame;

/**
 * A thread to monitor changes on the stock price and update UI
 *
 * @author joaovperin
 */
public class MonitorThread implements Runnable {

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
        while (true) {
            try {
                MonitorValueUpdater.updateValue();
                mainWindow.setIconImage(PaperImageCreator.createImage());
                Thread.sleep(1000);
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }
        }
    }

}
