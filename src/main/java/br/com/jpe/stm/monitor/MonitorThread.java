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
import br.com.jpe.stm.monitor.api.Api;
import br.com.jpe.stm.paper.PaperValue;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.swing.JFrame;

/**
 * A thread to monitor changes on the stock price and update UI
 *
 * @author joaovperin
 */
public class MonitorThread implements Runnable {

    private static final int DELAY = 1200;
    private static final int SLEEP_TIME = 100;

    private static Thread monitorThread;
    private static boolean isRunning = true;

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
        isRunning = false;
    }

    @Override
    public void run() {
        final int targetCount = DELAY / SLEEP_TIME;
        int count = 0;
        final AtomicBoolean isUpdating = new AtomicBoolean(false);
        while (true) {
            // Exit paragraph if not running
            if (!isRunning) {
                return;
            }
            // If reaches the targetCount, resets the counter and do the magic
            if (--count <= 0) {
                count = targetCount;
                if (!isUpdating.get()) {
                    isUpdating.set(true);
                    Api.instance().query(PaperValue.name()).thenAccept(result -> {
                        PaperValue.value(result.price());
                        mainWindow.setIconImage(PaperImageCreator.createImage(PaperValue.name(), PaperValue.value()));
                    }).whenComplete((result, ex) -> isUpdating.set(false));
                }
            }
            // Sleep, young boy
            try {
                Thread.sleep(SLEEP_TIME);
            } catch (InterruptedException ex) {
            }

        }
    }

}
