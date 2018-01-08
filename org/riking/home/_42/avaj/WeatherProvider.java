package org.riking.home._42.avaj;

public final class WeatherProvider {
    /**
     * Singleton instance
     */
    private static WeatherProvider weatherProvider;

    /**
     * Stores the constant weather strings
     */
    private final String[] weather;

    /**
     * Perlin noise used to generate weather
     */
    private PerlinNoise3 temperature;
    private PerlinNoise3 humidity;

    private WeatherProvider() {
        this.weather = new String[] { "RAIN", "SUN", "SNOW", "FOG" };

        this.temperature = new PerlinNoise3(3, null, false);
        this.humidity = new PerlinNoise3(4, null, true);
    }

    public static WeatherProvider getProvider() {
        if (weatherProvider == null) {
            weatherProvider = new WeatherProvider();
        }

        return weatherProvider;
    }

    public String getCurrentWeather(Coordinates coordinates) {
        PerlinNoise3.Vector3 coordVector = new PerlinNoise3.Vector3(coordinates.getLatitude() / 20.,
                coordinates.getLongitude() / 20., coordinates.getHeight() / 20.);
        double temp = this.temperature.get(coordVector);
        double hum = this.humidity.get(coordVector);

        if (temp > 0) {
            if (hum > 0) {
                return weather[0];
            } else {
                return weather[1];
            }
        } else {
            if (hum > 0) {
                return weather[2];
            } else {
                return weather[3];
            }
        }
    }

}
