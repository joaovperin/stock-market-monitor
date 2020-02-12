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
package br.com.jpe.stm;

import br.com.jpe.stm.gui.MainWindow;
import br.com.jpe.stm.img.PaperImageCreator;
import br.com.jpe.stm.monitor.MonitorThread;
import br.com.jpe.stm.paper.PaperValue;
import java.awt.Frame;
import java.io.IOException;
import javax.swing.WindowConstants;

/**
 * Main class
 *
 * @author joaovperin
 */
public class Main {

    public static final boolean debugging() {
        return false;
    }

    public static void main(String[] args) {
        // First argument is the paper name, if provided
        if (args != null && args.length > 0) {
            PaperValue.name(args[0]);
        }
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            MainWindow mainWindow = new MainWindow();
            mainWindow.setState(Frame.ICONIFIED);
            mainWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            mainWindow.setVisible(true);
            try {
                mainWindow.setIconImage(PaperImageCreator.createImage());
            } catch (IOException ex) {
            }
            // Start the monitor thread
            MonitorThread.startMonitor(mainWindow);
        });
    }

}
