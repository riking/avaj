package org.riking.home._42.avaj;

import java.util.List;

public class WeatherProvider {
    private static WeatherProvider weatherProvider;
    private String[] weather;

    private WeatherProvider() {
        this.weather = new String[]{
                "RAIN", "FOG", "SUN", "SNOW"
        };
    }

    public static WeatherProvider getProvider() {
        if (weatherProvider == null) {
            weatherProvider = new WeatherProvider();
        }

        return weatherProvider;
    }

    public String getCurrentWeather(Coordinates coordinates) {
        return weather[0];
    }
}
