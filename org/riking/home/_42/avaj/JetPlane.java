package org.riking.home._42.avaj;

public class JetPlane extends Aircraft implements Flyable {
    private WeatherTower weatherTower;

    /* package */ JetPlane(String name, Coordinates coordinates) {
        super(name, coordinates);
    }

    @Override
    public void updateConditions() {
        String weather = this.weatherTower.getWeather(this.coordinates);

        switch (weather) {
            case "RAIN":
                this.log("It's raining. Better watch out for lightning.");
                this.adjustCoordinates(0, +5, 0);
                break;
            case "FOG":
                this.log("Heavy fog, switching to IFR.");
                this.adjustCoordinates(0, +1, 0);
                break;
            case "SUN":
                this.log("Sunny out, climbing.");
                this.adjustCoordinates(0, +10, +2);
                break;
            case "SNOW":
                this.log("OMG! Winter is coming!");
                this.adjustCoordinates(0, 0, -7);
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
