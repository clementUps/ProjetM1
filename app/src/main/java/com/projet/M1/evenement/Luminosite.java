package com.projet.M1.evenement;

import android.hardware.Sensor;

import com.projet.M1.enumerations.Conditions;

/**
 * Created by Lucas on 17/03/2015.
 */
public class Luminosite extends Capteur {

    private float nbLuxCond; //lux pour appliquer la condition
    private float lux; //lux actuel du capteur

    public Luminosite(String nom, Boolean opposite, Conditions conditions, int luxCond){
        super(nom, opposite, conditions);
        nbLuxCond = luxCond;
    }

    public float getNbLuxCond() {
        return nbLuxCond;
    }

    public void setNbLuxCond(float nbLuxCond) {
        this.nbLuxCond = nbLuxCond;
    }

    public float getLux() {
        return lux;
    }

    public void setLux(float lux) {
        this.lux = lux;
    }

    @Override
    public void initialiser() {
    }
}