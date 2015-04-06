package com.projet.M1.main;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.projet.M1.action.GestionMail;
import com.projet.M1.enumerations.Conditions;
import com.projet.M1.evenement.Capteur;
import com.projet.M1.evenement.Localisation;
import com.projet.M1.evenement.Luminosite;

import java.util.Timer;


public class MainActivity extends Activity {
    TextView texteLumiere;
    TextView texteLocalisation;
    TextView localisationProvider;
    TextView mailLumi;
    TextView mailLoca;

    Sensor mLumiere = null;
    SensorManager mSensorManager = null;

    LocationManager locationManager;

    /* CREATION DE L'ACTIONNEUR */
    final Actionneur monActionneur = new Actionneur();

    /* CREATION DES CAPTEURS */
    final Luminosite lumiere = new Luminosite("Luminosite", false, Conditions.IF_THEN, 10);
    final Localisation localisation = new Localisation("Localisation", false, Conditions.IF_THEN, 43.561838, 1.468124, 0.000005);

    Timer timerMail = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* AJOUT DES ACTIONS AUX CAPTEURS */
        final GestionMail envoieMailLumi = new GestionMail(1);
        lumiere.ajouterAction(envoieMailLumi);
        final GestionMail envoieMailLoca = new GestionMail(2);
        localisation.ajouterAction(envoieMailLoca);

        /* TIMER POUR REINITIALISER L'ENVOI DE MAIL -> 30sec */
        timerMail.scheduleAtFixedRate(envoieMailLoca, 0, 30000);
        timerMail.scheduleAtFixedRate(envoieMailLumi, 0, 30000);

        /* AJOUT DES CAPTEURS A l'ACTIONNEUR */
        monActionneur.ajouterCapteur(lumiere);
        monActionneur.ajouterCapteur(localisation);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mLumiere = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        texteLumiere = (TextView)findViewById(R.id.texteLumiere);
        texteLocalisation = (TextView)findViewById(R.id.texteLocalisation);
        localisationProvider = (TextView)findViewById(R.id.localisationProvider);
        mailLumi = (TextView)findViewById(R.id.mailLumi);
        mailLoca = (TextView)findViewById(R.id.mailLoca);
        mailLumi.setText("Mail Luminisote envoye : " + envoieMailLumi.isEnvoye());
        mailLoca.setText("Mail Localisation envoye : " + envoieMailLoca.isEnvoye());

        localisationProvider.setText("Localisation Provider : enabled");

        if (mLumiere == null) {
            Log.e("capteur", "Luminosite fail");
        }

        final SensorEventListener mLumiereSensorEventListener = new SensorEventListener() {
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                Log.i("capteur", "Luminosite precision" + accuracy);
            }

            public void onSensorChanged(SensorEvent sensorEvent) {
                //Log.i("capteur", " Luminosite : " + sensorEvent.values[0]);
                texteLumiere.setText("Luminosite : " + sensorEvent.values[0]);
                monActionneur.miseAJourCapteur(lumiere, sensorEvent.values[0]);
                monActionneur.gererCapteur();
                mailLumi.setText("Mail Luminosite envoye : " + envoieMailLumi.isEnvoye());
            }
        };

        final LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (location != null) {
                   /* Log.i("Géolocalisation", "Latitude: " + location.getLatitude() +
                            " Longitude : " + location.getLongitude() +
                            " Altitude : " + location.getAltitude() +
                            " Précision : " + location.getAccuracy());*/
                    texteLocalisation.setText("Latitude: " + location.getLatitude() +
                            " Longitude : " + location.getLongitude() +
                            " Altitude : " + location.getAltitude() +
                            " Précision : " + location.getAccuracy());
                    monActionneur.miseAJourCapteur(localisation, location.getLatitude(), location.getLongitude(), location.getAccuracy());
                    monActionneur.gererCapteur();
                    mailLoca.setText("Mail Localisation envoye : " + envoieMailLoca.isEnvoye());
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                Log.i("Géolocalisation","Status changed");
            }

            @Override
            public void onProviderEnabled(String provider) {
                Log.i("Géolocalisation","Provider enabled");
                localisationProvider.setText("Localisation Provider : enabled");
            }

            @Override
            public void onProviderDisabled(String provider) {
                Log.i("Géolocalisation","Provider disabled");
                localisationProvider.setText("Localisation Provider : disabled");
            }
        };

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
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
