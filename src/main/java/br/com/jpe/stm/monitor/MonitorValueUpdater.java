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

    // TODO (10/02/2020): unrandomize and get from AlphaVantage WS
    // TODO (10/02/2020): unrandomize and get from AlphaVantage WS
    // TODO (10/02/2020): unrandomize and get from AlphaVantage WS
    private static BigDecimal getNewValue() {
        final Random r = new Random();
        BigDecimal nextNumber = new BigDecimal(r.nextFloat());
        return (r.nextBoolean() || r.nextBoolean())
                ? PaperValue.value().add(nextNumber)
                : PaperValue.value().subtract(nextNumber);
    }

}
