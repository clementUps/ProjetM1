package com.projet.M1.main;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.projet.M1.action.GestionMail;
import com.projet.M1.evenement.Capteur;
import com.projet.M1.evenement.Luminosite;


public class MainActivity extends Activity {

    Sensor mLumiere = null;
    SensorManager mSensorManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Actionneur monActionneur = new Actionneur();
        final Luminosite lumiere = new Luminosite("Luminosite", false, 30);
        GestionMail envoieMail = new GestionMail();
        lumiere.ajouterAction(envoieMail);
        monActionneur.ajouterCapteur(lumiere);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mLumiere = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        if(mLumiere == null)
            Log.e("capteur", "Luminosite fail");

        final SensorEventListener mLumiereSensorEventListener = new SensorEventListener() {
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                Log.i("capteur","Luminosite precision"+accuracy );
            }

            public void onSensorChanged(SensorEvent sensorEvent) {
                Log.i("capteur"," Luminosite : "+sensorEvent.values[0]);
                monActionneur.miseAJourCapteur(lumiere, sensorEvent.values[0]);
                monActionneur.gererCapteur();
            }
        };

        mSensorManager.registerListener(mLumiereSensorEventListener, mLumiere, SensorManager.SENSOR_DELAY_NORMAL);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
