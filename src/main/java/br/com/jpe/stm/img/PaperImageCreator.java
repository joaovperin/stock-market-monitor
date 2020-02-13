/*
 * Copyright (C) 2020 Joaov
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
package br.com.jpe.stm.img;

import br.jpe.ipl.core.ImageFactory;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Create the image of a paper with it's current value
 *
 * @author joaovperin
 */
public class PaperImageCreator {

    private static final int ICON_SIZE = 64;
    private static final Font PAPER_VALUE_FONT = new Font(Font.MONOSPACED, Font.BOLD, 19);
    private static final Font PAPER_NAME_FONT = new Font(Font.MONOSPACED, Font.BOLD, 26);

    private static BufferedImage lastImage;
    private static String lastName;
    private static BigDecimal lastValue;

    private static BufferedImage imgCache;
    private static Graphics2D graphicsCache;

    public static BufferedImage createImage(String name, BigDecimal value) {
        // Cache control
        if (isSamePaperAndValue(value, name)) {
            return lastImage;
        }

        // Creates a new image
        BufferedImage image = ImageFactory.empty(ICON_SIZE, ICON_SIZE).toBufferedImage();
        Graphics2D g = image.createGraphics();
        g.clearRect(0, 0, ICON_SIZE, ICON_SIZE);
        g.setBackground(Color.white);

        g.setFont(PAPER_NAME_FONT);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
                RenderingHints.VALUE_FRACTIONALMETRICS_ON);

        g.drawString(name, 0, 16);
        g.fillRect(0, 16, ICON_SIZE, 3);

        g.setFont(PAPER_VALUE_FONT);
        String fmtInteger = fmtInteger(value), fmtDecimal = fmtDecimal(value);
        g.drawString(value.signum() == -1 ? "-" : "" + fmtInteger, 1 + 7 * (5 - fmtInteger.length()), 34); //3=15, 2=22, 1=29
        g.drawString(fmtDecimal, 12, 56);

        // Saves a memory cache
        lastImage = image;
        lastName = name;
        lastValue = value;
        return image;
    }

    public static BufferedImage createAnimatedImage(int size) {
        // Creates a new image
        BufferedImage image = ImageFactory.empty(ICON_SIZE, ICON_SIZE).toBufferedImage();
        final Graphics2D g = image.createGraphics();
        g.clearRect(0, 0, ICON_SIZE, ICON_SIZE);
        g.setBackground(Color.white);
        g.setColor(Color.red);

        CompletableFuture.runAsync(() -> {
            int cnt = ICON_SIZE - size;
            while (cnt > 0) {
                cnt--;
                try {
                    Thread.sleep(500);
                    final var position = ICON_SIZE / 2 - size / 2;
                    g.fillOval(position, position, size, size);
                } catch (InterruptedException ex) {
                }
            }

        });

        return image;
    }

    public static CompletableFuture createAndSetInitialImage(javax.swing.JFrame mainWindow) {

        BufferedImage image = ImageFactory.empty(ICON_SIZE, ICON_SIZE).toBufferedImage();
        Graphics2D g = image.createGraphics();
        g.clearRect(0, 0, ICON_SIZE, ICON_SIZE);
        g.setBackground(Color.white);
        g.setColor(Color.red);

        return createAndSetInitialImageRecursive(mainWindow, image, g, new AtomicInteger(ICON_SIZE / 20));
    }

    private static CompletableFuture<Void> createAndSetInitialImageRecursive(javax.swing.JFrame mainWindow, BufferedImage image, Graphics2D g, final AtomicInteger aSize) {
        final int size = aSize.getAndIncrement();
        if (size >= ICON_SIZE) {
            return CompletableFuture.completedFuture(null);
        }
        try {
            final var position = ICON_SIZE / 2 - size / 2;
            g.fillOval(position, position, size, size);
            mainWindow.setIconImage(image);
            Thread.sleep(10);
        } catch (InterruptedException ex) {
        }
        return createAndSetInitialImageRecursive(mainWindow, image, g, aSize);
    }

    public static BufferedImage createInitialImage(int size) {
        BufferedImage image = imgCache;
        Graphics2D g = graphicsCache;

        // Creates a new image just if needed
        if (image == null || g == null) {
            image = ImageFactory.empty(ICON_SIZE, ICON_SIZE).toBufferedImage();
            g = image.createGraphics();
            g.clearRect(0, 0, ICON_SIZE, ICON_SIZE);
            g.setBackground(Color.white);
            g.setColor(Color.red);
        }

        final var position = ICON_SIZE / 2 - size / 2;
        g.fillOval(position, position, size, size);

        // Saves cache
        imgCache = image;
        graphicsCache = g;

        return image;
    }

    private static boolean isSamePaperAndValue(BigDecimal value, String name) {
        return lastValue != null && lastValue.equals(value) && lastName != null && lastName.equals(name);
    }

    private static String fmtDecimal(BigDecimal value) {
        return NumberFormat.getInstance().format(value.remainder(BigDecimal.ONE).setScale(2, RoundingMode.DOWN)).substring(1);
    }

    private static String fmtInteger(BigDecimal value) {
        return NumberFormat.getInstance().format(value.toBigInteger());
    }

}
