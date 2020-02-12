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

import br.com.jpe.stm.utils.StmProperties;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;

/**
 *
 * A class to communicate with Alpha Vantage API
 *
 * https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=MSFT&interval=1min&apikey=demo
 *
 * @author joaovperin
 */
public class AlphaVantageApi {

    private static final String BASE_URI = "https://www.alphavantage.co/query";

    // TODO (11/02/2020): Use CompletableFutures!
    //symbol,open,high,low,price,volume,latestDay,previousClose,change,changePercent
    public static AlphaVantageApiResult query(String key) {
        HttpClient httpClient = HttpClient.newHttpClient();
        try {
            HttpResponse<String> response = httpClient.send(createHttpRequest(), BodyHandlers.ofString());
            return AlphaVantageApiResult.from(response.body());
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace(System.err);
        }
        return AlphaVantageApiResult.empty();
    }

    private static HttpRequest createHttpRequest() {
        try {
            return new Builder()
                    .function(Function.GLOBAL_QUOTE)
                    .apiKey(StmProperties.API_KEY.value())
                    .build();
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid URI", e);
        }
    }

    public static final class Builder {

        private String function = Function.TIME_SERIES_INTRADAY.name();
        private String symbol = "MGLU3.SAO";
        private String interval = "1min";
        private String apiKey = "demo";

        public Builder function(Function function) {
            this.function = function.name();
            return this;
        }

        public Builder symbol(String symbol) {
            this.symbol = symbol.concat(".SAO");
            return this;
        }

        public Builder interval(String interval) {
            this.interval = interval;
            return this;
        }

        public Builder apiKey(String apiKey) {
            this.apiKey = apiKey;
            return this;
        }

        public HttpRequest build() throws URISyntaxException {
            return HttpRequest.newBuilder()
                    .GET()
                    .timeout(Duration.ofSeconds(10L))
                    .uri(new URI(new StringBuilder(BASE_URI).append('?')
                            //                            .append("outputsize=").append("compact")
                            .append("&function=").append(function)
                            .append("&symbol=").append(symbol)
                            .append("&datatype=").append("csv")
                            .append("&interval=").append(interval)
                            .append("&apikey=").append(apiKey)
                            .toString())).build();
        }

    }

    public enum Function {
        GLOBAL_QUOTE,
        TIME_SERIES_INTRADAY;
    }

}
