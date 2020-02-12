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
package br.com.jpe.stm.monitor;

import br.com.jpe.stm.Main;
import br.com.jpe.stm.monitor.api.AlphaVantageApi;
import br.com.jpe.stm.paper.PaperValue;
import java.math.BigDecimal;
import java.util.Random;

/**
 * Updates the value of the current paper
 *
 * @author joaovperin
 */
public class MonitorValueUpdater {

    public static void updateValue() {
        PaperValue.updateValue(getNewValue());
    }

    private static BigDecimal getNewValue() {
        // Random stuff to make tests more agile
        if (Main.debugging()) {
            final Random r = new Random();
            BigDecimal nextNumber = new BigDecimal(r.nextFloat());
            return (r.nextBoolean() || r.nextBoolean()) // 66% true chance
                    ? PaperValue.value().add(nextNumber) // true goes up
                    : PaperValue.value().subtract(nextNumber); // false goes down
        }
        // Value not found
        return AlphaVantageApi.query(PaperValue.name()).getPrice();
    }

}
