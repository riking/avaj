package org.riking.home._42.avaj;

public class Helicopter extends Aircraft implements Flyable {
    private WeatherTower weatherTower;

    /* package */ Helicopter(String name, Coordinates coordinates) {
        super(name, coordinates);
    }

    @Override
    public void updateConditions() {
        String weather = this.weatherTower.getWeather(this.coordinates);

        switch (weather) {
            case "SUN":
                this.log("This is hot.");
                this.adjustCoordinates(+10, 0, +2);
                break;
            case "RAIN":
                this.log("Good thing I still have windows.");
                this.adjustCoordinates(+5, 0,0);
                break;
            case "FOG":
                this.log("Dang, I can barely see in this.");
                this.adjustCoordinates(+1, 0,0);
                break;
            case "SNOW":
                this.log("My rotor is going to freeze!");
                this.adjustCoordinates(0, 0, -12);
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
