package com.projet.M1.Module;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.projet.M1.action.Action;
import com.projet.M1.action.Actionneur;
import com.projet.M1.evenement.Capteur;
import com.projet.M1.evenement.Localisation;
import com.projet.M1.evenement.Luminosite;

import java.util.Timer;

/**
 * Created by clement on 12/04/2015.
 */
public class Module{
    Capteur capteur;
    Action action;
    SensorManager mSensorManager = null;
    LocationManager mLocationManager = null;
    Thread mCurrentThread;

    public Module(Capteur capteur,Action action,SensorManager sensorManager){
        this.capteur = capteur;
        this.action = action;
        mSensorManager = sensorManager;
    }

    public Module(Capteur capteur,Action action,LocationManager locationManager){
        Log.i("Constructeur Loca", "OK");
        this.capteur = capteur;
        this.action = action;
        mLocationManager = locationManager;
    }

    public Capteur getCapteur() {
        return capteur;
    }

    public Action getAction() {
        return action;
    }

    public void init() throws ModuleException{
        Sensor mSensor = null;

        final Actionneur actionneur = new Actionneur();
        Timer timer = new Timer();
        capteur.ajouterAction(action);
        timer.scheduleAtFixedRate(action, 0, 30000);
        actionneur.ajouterCapteur(capteur);
        if (capteur.getSensorType() != -1) {
            mSensor = mSensorManager.getDefaultSensor(capteur.getSensorType());
        }
        if(mSensor == null && mLocationManager == null){
            throw new ModuleException();
        }

        if (capteur instanceof Luminosite) {
            mSensor = mSensorManager.getDefaultSensor(capteur.getSensorType());
            if(mSensor == null){
                throw new ModuleException();
            }
            final SensorEventListener mCapteurSensorEventListener = new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent event) {
                   // Log.i("Lumiere", "lux : " + event.values[0]);
                    actionneur.miseAJourCapteur((Luminosite) capteur, event.values[0]);
                    actionneur.gererCapteur();
                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) {
                }
            };
            mSensorManager.registerListener(mCapteurSensorEventListener, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
        else if (capteur instanceof Localisation) {
            Log.i("Instance Loca", "OK");
            final LocationListener locationListener = new LocationListener() {

                @Override
                public void onLocationChanged(Location location) {
                    Log.i("Instance Loca", "OK");
                    Log.i("Localisation", "latitude : " + location.getLatitude() + " logititude :" + location.getLongitude());
                    actionneur.miseAJourCapteur((Localisation) capteur, location.getLatitude(), location.getLongitude(), location.getAccuracy());
                    actionneur.gererCapteur();
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {
                }

                @Override
                public void onProviderEnabled(String provider) {
                }

                @Override
                public void onProviderDisabled(String provider) {
                }
            };
            Log.i("IN Listener", "OK");
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }

    }
    public void setCurrentThread(Thread thread) {
            mCurrentThread = thread;
    }

}
