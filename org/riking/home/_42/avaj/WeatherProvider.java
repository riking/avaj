package org.riking.home._42.avaj;

public class WeatherProvider {
    private static WeatherProvider weatherProvider;
    private final String[] weather;

    private PerlinNoise3 temperature;
    private PerlinNoise3 humidity;

    private WeatherProvider() {
        this.weather = new String[] { "RAIN", "FOG", "SUN", "SNOW" };

        this.temperature = new PerlinNoise3(2, null, false);
        this.humidity = new PerlinNoise3(3, null, true);
    }

    public static WeatherProvider getProvider() {
        if (weatherProvider == null) {
            weatherProvider = new WeatherProvider();
        }

        return weatherProvider;
    }

    public String getCurrentWeather(Coordinates coordinates) {
        PerlinNoise3.Vector3 coordVector = new PerlinNoise3.Vector3(coordinates.getLatitude() + 0.5,
                coordinates.getLongitude() + 0.5, coordinates.getHeight() + 0.5);
        double temp = this.temperature.get(coordVector);
        double hum = this.humidity.get(coordVector);
        return weather[0];
    }

}
