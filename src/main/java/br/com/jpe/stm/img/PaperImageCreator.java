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

import br.com.jpe.stm.paper.PaperValue;
import br.jpe.ipl.core.ImageFactory;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;

/**
 * Create the image of a paper with it's current value
 *
 * @author joaovperin
 */
public class PaperImageCreator {

    private static BufferedImage lastImage;
    private static BigDecimal lastValue;

    public static BufferedImage createImage() throws IOException {
        // Cache controll
        if (lastValue != null && lastValue.equals(PaperValue.value())) {
            return lastImage;
        }

        // Creates a new image
        BufferedImage image = ImageFactory.empty(64, 64).toBufferedImage();
        Graphics2D g = image.createGraphics();
        g.clearRect(0, 0, 64, 64);
        g.setBackground(Color.white);

        g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 26));
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
                RenderingHints.VALUE_FRACTIONALMETRICS_ON);

        g.drawString(PaperValue.name(), 0, 16);
        g.fillRect(0, 16, 64, 3);

        BigDecimal value = PaperValue.value();
        g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 19));
        g.drawString(value.signum() == -1 ? "-" : "" + NumberFormat.getInstance().format(value.toBigInteger()) + ",", 22, 34);
        g.drawString(NumberFormat.getInstance().format(value.remainder(BigDecimal.ONE)), 5, 56);

        // Saves a memory cache
        lastImage = image;
        lastValue = PaperValue.value();
        return image;
    }

}
