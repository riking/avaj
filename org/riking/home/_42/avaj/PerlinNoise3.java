package org.riking.home._42.avaj;

import java.lang.Double;
import java.lang.IllegalArgumentException;
import java.lang.Iterable;
import java.lang.Math;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Random;

// Perlin noise implementation by eevee licensed under ISC.

public class PerlinNoise3 {
    private final int octaves;
    private final Vector3 tiling;
    private final boolean unbias;
    // map from grid point (Int[3]) to gradient vector (Unit vector)
    private Map<Vector3, Vector3> gradient;
    private final Random rand;

    private static final int dimensions = 3;
    // range of noise is ±sqrt(dim)/2
    private static final double scale_factor = 2. * (1. / Math.sqrt(3));

    public final static class Vector3 implements Iterable<Double> {
        private double x;
        private double y;
        private double h;

        public Vector3(double x, double y, double h) {
            this.x = x;
            this.y = y;
            this.h = h;
        }

        public Vector3(Vector3 other) {
            this(other.x, other.y, other.h);
        }

        @Override
        public int hashCode() {
            final long prime = 31;
            long result = 1;
            result = prime * result + Double.doubleToLongBits(x);
            result = prime * result + Double.doubleToLongBits(y);
            result = prime * result + Double.doubleToLongBits(h);
            return (int) (result ^ (result >> 32));
        }

        @Override
        public boolean equals(Object other) {
            if (!(other instanceof Vector3)) { // final class
                return false;
            }
            Vector3 o = (Vector3) other;
            return this.x == o.x && this.y == o.y && this.h == o.h;
        }

        public double get(int dim) {
            switch (dim) {
            case 0:
                return this.x;
            case 1:
                return this.y;
            case 2:
                return this.h;
            }
            throw new NoSuchElementException();
        }

        public void set(int dim, double value) {
            switch (dim) {
            case 0:
                this.x = value;
            case 1:
                this.y = value;
            case 2:
                this.h = value;
            default:
                throw new IllegalArgumentException();
            }
        }

        public double magnitude() {
            return Math.sqrt(this.x * this.x + this.y * this.y + this.h * this.h);
        }

        public void scaleFactor(double scale) {
            this.x *= scale;
            this.y *= scale;
            this.h *= scale;
        }

        @Override
        public Iterator<Double> iterator() {
            return new Iter();
        }

        private class Iter implements Iterator<Double> {
            private int state;

            @Override
            public boolean hasNext() {
                return state < 3;
            }

            @Override
            public Double next() {
                int _state = state;
                this.state = _state + 1;
                return get(_state);
            }
        }
    }

    public PerlinNoise3() {
        this(2, null, false);
    }

    public PerlinNoise3(int octaves, Vector3 tiling, boolean unbias) {
        // this.dimensions = dimensions;
        this.octaves = octaves;
        if (tiling == null) {
            this.tiling = new Vector3(0, 0, 0);
        } else {
            this.tiling = tiling;
        }
        this.unbias = unbias;
        this.gradient = new HashMap<>();

        SecureRandom seeder = new SecureRandom();
        this.rand = new Random(seeder.nextLong());
    }

    public void setSeed(long seed) {
        this.rand.setSeed(seed);
        this.gradient.clear();
    }

    private static double smoothstep(double t) {
        return t * t * (3. - 2. * t);
    }

    private static double lerp(double t, double a, double b) {
        return a + t * (b - a);
    }

    private Vector3 generateGradient() {
        while (true) {
            Vector3 random_point = new Vector3(this.rand.nextGaussian(), this.rand.nextGaussian(),
                    this.rand.nextGaussian());

            double mag = random_point.magnitude();
            if (mag < 0.001) {
                // Try again if we get a small magnitude
                continue;
            }
            random_point.scaleFactor(1. / mag);
            return random_point;
        }
    }

    /**
     * @param grid_point Vector3 with integer points
     * @return Random gradient for noise.
     */
    private Vector3 getGradient(Vector3 grid_point) {
        Vector3 r = this.gradient.get(grid_point);
        if (r == null) {
            r = this.generateGradient();
            this.gradient.put(grid_point, r);
        }
        return r;
    }

    private double getPlainNoise(Vector3 point) {
        // List of (min, max) bounds in each dimension
        // Shoehorning vec3 in here - these are ACTUALLY vec2 s!!
        List<Vector3> grid_coords = new ArrayList<>(3);
        for (Double d : point) {
            double min = Math.floor(d);
            if (min >= (1L << 52)) {
                // Boundary where float precision hits increments of 1
                // Adding 1 has no effect this far out.
                throw new IllegalArgumentException("Point out of range (Max 2^52)");
            }
            double max = min + 1;
            grid_coords.add(new Vector3(min, max, 0));
        }

        // cartesian product across elements of grid_coords
        // loop generates (x, y, h), (x, y, H), (x, Y, h), ...
        List<Double> dots = new ArrayList<>(3);
        for (int step = 0; step < 2 * 2 * 2; step++) {
            double x, y, h;
            x = grid_coords.get(0).get((step / 1) % 2);
            y = grid_coords.get(1).get((step / 2) % 2);
            h = grid_coords.get(2).get((step / 4) % 2);
            Vector3 grid_point = new Vector3(x, y, h);
            Vector3 gradient = getGradient(grid_point);

            double dot = 0;
            for (int dim = 0; dim < PerlinNoise3.dimensions; dim++) {
                dot += gradient.get(dim) + (point.get(dim) - grid_point.get(dim));
            }
            dots.add(dot);
        }

        int dim = PerlinNoise3.dimensions;
        while (dots.size() > 1) {
            dim--;
            // the .get(0) on the end is selecting the min instead of max
            double s = smoothstep(point.get(dim) - grid_coords.get(dim).get(0));
            List<Double> next_dots = new ArrayList<>();
            while (dots.size() > 0) {
                Double a = dots.remove((int) 0);
                Double b = dots.remove((int) 0);
                next_dots.add(lerp(s, a, b));
            }
            dots = next_dots;
        }

        return dots.get(0) * PerlinNoise3.scale_factor;
    }

    /**
     * Get the value of this Perlin noise function at the given point.
     */
    public double get(Vector3 point) {
        double ret = 0;

        // Octaves loop
        for (int o = 0; o < this.octaves; o++) {
            int o2 = 1 << o;
            Vector3 new_point = new Vector3(point);
            new_point.scaleFactor(o2);
            for (int dim = 0; dim < PerlinNoise3.dimensions; dim++) {
                if (this.tiling.get(dim) != 0) {
                    new_point.set(dim, new_point.get(dim) % (this.tiling.get(dim) * o2));
                }
            }

            ret += this.getPlainNoise(new_point) / o2;
        }

        // Need to scale n back down since adding all those extra octaves has
        // probably expanded it beyond ±1
        // 1 octave: ±1
        // 2 octaves: ±1½
        // 3 octaves: ±1¾
        ret /= 2. - Math.pow(2, (1 - this.octaves));

        if (this.unbias) {
            // The output of the plain Perlin noise algorithm has a fairly
            // strong bias towards the center due to the central limit theorem
            // -- in fact the top and bottom 1/8 virtually never happen.  That's
            // a quarter of our entire output range!  If only we had a function
            // in [0..1] that could introduce a bias towards the endpoints...
            double r = (ret + 1) / 2;
            // Doing it this many times is a completely made-up heuristic.
            for (int i = 0; i < (int) (this.octaves / 2 + 0.5); i++) {
                r = smoothstep(r);
            }
            ret = r * 2 - 1;
        }

        return ret;
    }
}