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
package br.com.jpe.stm.monitor.api;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

/**
 * A class to represent the result of a query
 *
 * @author joaovperin
 */
public class AlphaVantageApiResult {

    private final Map<String, String> responseMap;

    private AlphaVantageApiResult(String responseString) {
        this.responseMap = transformToMap(responseString);
    }

    public static AlphaVantageApiResult from(String httpResponse) {
        return new AlphaVantageApiResult(httpResponse);
    }

    public static AlphaVantageApiResult empty() {
        return new AlphaVantageApiResult(null);
    }

    public BigDecimal getPrice() {
        return new BigDecimal(get("price").orElse("0"));
    }

    private Optional<String> get(String key) {
        return Optional.ofNullable(responseMap.get(key));
    }

    private static Map<String, String> transformToMap(String csvBody) {
        //
        //symbol,open,high,low,price,volume,latestDay,previousClose,change,changePercent
        //MGLU3.SAO,53.2000,54.8000,51.7300,54.2700,9488700,2020-02-11,52.3800,1.8900,3.6082%
        //
        Map<String, String> map = new TreeMap<>();
        if (csvBody != null && !csvBody.isBlank()) {
            String[] split = csvBody.split("\n");
            String[] keys = split[0].split(","), values = split[1].split(",");
            for (int i = 0; i < keys.length && i < values.length; i++) {
                map.put(keys[i], values[i]);
            }
        }
        return map;
    }
}
