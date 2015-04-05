package com.projet.M1.evenement;
import android.location.LocationManager;

import com.projet.M1.enumerations.Conditions;


public class Localisation extends Capteur{
    private LocationManager lm;

    private double latitude;
    private double longitude;
    private float accuracy;

    private double latitudeCond; //latitude condition
    private double longitudeCond; //longitude condition
    private double precision; //precision à laquelle on vérifie la position condition par rapport à la position réelle

    public Localisation(String nom, Boolean opposite, Conditions conditions, double latitudeCond, double longitudeCond, double precision) {
        super(nom, opposite, conditions);
        this.latitudeCond = latitudeCond;
        this.longitudeCond = longitudeCond;
        this.precision = precision;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLatitudeCond() {
        return latitudeCond;
    }

    public double getLongitudeCond() {
        return longitudeCond;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getPrecision() {
        return precision;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public float getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(float accuracy) {
        this.accuracy = accuracy;
    }

    @Override
    public void initialiser() {

    }
}