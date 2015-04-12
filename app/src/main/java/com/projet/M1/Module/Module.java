package com.projet.M1.Module;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.projet.M1.action.Action;
import com.projet.M1.action.Actionneur;
import com.projet.M1.evenement.Capteur;
import com.projet.M1.evenement.Luminosite;

import java.util.Timer;

/**
 * Created by clement on 12/04/2015.
 */
public class Module{
    Capteur capteur;
    Action action;
    SensorManager mSensorManager = null;
    Thread mCurrentThread;
    public Module(Capteur capteur,Action action,SensorManager sensorManager){
        this.capteur = capteur;
        this.action = action;
        mSensorManager = sensorManager;
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
        mSensor = mSensorManager.getDefaultSensor(capteur.getSensorType());
        if(mSensor == null){
            throw new ModuleException();
        }
        final SensorEventListener mCapteurSensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                actionneur.miseAJourCapteur((Luminosite)capteur, event.values[0]);
                actionneur.gererCapteur();
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        mSensorManager.registerListener(mCapteurSensorEventListener, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }
    public void setCurrentThread(Thread thread) {
            mCurrentThread = thread;
    }

}
