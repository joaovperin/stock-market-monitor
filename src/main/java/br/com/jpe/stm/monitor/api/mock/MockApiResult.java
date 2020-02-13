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
package br.com.jpe.stm.monitor.api.mock;

import br.com.jpe.stm.monitor.api.ApiResult;
import br.com.jpe.stm.paper.PaperValue;
import java.math.BigDecimal;
import java.util.Random;

/**
 * A mocked API Result
 *
 * @author joaovperin
 */
public class MockApiResult implements ApiResult {

    private static final Random RNG = new Random();

    @Override
    public BigDecimal price() {
        BigDecimal nextNumber = new BigDecimal(RNG.nextFloat());
        return (RNG.nextBoolean() || RNG.nextBoolean()) // 66% true chance
                ? PaperValue.value().add(nextNumber) // true goes up
                : PaperValue.value().subtract(nextNumber); // false goes down
    }

}
