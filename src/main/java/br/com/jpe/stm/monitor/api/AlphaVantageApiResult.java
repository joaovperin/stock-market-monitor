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

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * A class to represent the result of a query
 *
 * @author joaovperin
 */
public class AlphaVantageApiResult {

    private final Map<String, Map<String, Map<String, Object>>> responseMap;

    private AlphaVantageApiResult(String responseString) {
        try {
            this.responseMap = new ObjectMapper().readValue(responseString, Map.class);
        } catch (JsonProcessingException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    public AlphaVantageApiResult in(String key) {
        Map<String, Map<String, Object>> get = responseMap.get(key);
        if (get != null) {
            return new AlphaVantageApiResult("................");
        }
        return this;
    }

    public static AlphaVantageApiResult from(String httpResponse) {
        return new AlphaVantageApiResult(httpResponse);
    }

}
