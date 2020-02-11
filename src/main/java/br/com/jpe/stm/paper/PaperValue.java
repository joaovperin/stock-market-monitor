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
package br.com.jpe.stm.paper;

import java.math.BigDecimal;

/**
 * A Singleton class to hold a paper value
 *
 * @author joaovperin
 */
public class PaperValue {

    private static PaperValue instance;

    private final String name = "MGLU3";
    private Long time = System.currentTimeMillis();
    private BigDecimal value;

    public static String name() {
        return get().name;
    }

    public static long lastUpdateTime() {
        return get().time;
    }

    public static BigDecimal value() {
        return get().value;
    }

    public static void updateValue(BigDecimal value) {
        Long now = System.currentTimeMillis();
        get().time = now;
        get().value = value;
    }

    public static PaperValue get() {
        if (instance == null) {
            instantiate();
        }
        return instance;
    }

    private static synchronized void instantiate() {
        if (instance == null) {
            instance = new PaperValue();
        }
    }

    public PaperValue() {
        this.value = BigDecimal.valueOf(58.32f);
    }

}
