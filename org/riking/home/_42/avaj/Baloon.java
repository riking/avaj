package org.riking.home._42.avaj;

/**
 *
 * Note: typo in name is as per spec
 */
public class Baloon extends Aircraft implements Flyable {
    private WeatherTower weatherTower;

    /* package */ Baloon(String name, Coordinates coordinates) {
        super(name, coordinates);
    }

    @Override
    public void updateConditions() {
        String weather = this.weatherTower.getWeather(this.coordinates);

        switch (weather) {
            case "RAIN":
                this.log("Curse this rain! It messed up my balloon.");
                this.adjustCoordinates(0, 0, -5);
                break;
            case "FOG":
                this.log("I can't see anything like this!");
                this.adjustCoordinates(0, 0, -3);
                break;
            case "SUN":
                this.log("Let's enjoy the good weather. But first, let me take a selfie.");
                this.adjustCoordinates(0, +2, +4);
                break;
            case "SNOW":
                this.log("A pocket of cold air! We're going down!");
                this.adjustCoordinates(0, 0, -15);
                break;
        }

        if (this.coordinates.getHeight() == 0) {
            this.logLanding(this.coordinates);
            this.weatherTower.unregister(this);
        }
    }

    @Override
    public void registerTower(WeatherTower weatherTower) {
        weatherTower.register(this);
        this.weatherTower = weatherTower;
    }
}
