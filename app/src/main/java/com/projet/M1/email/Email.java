package com.projet.M1.email;

import android.os.AsyncTask;
import android.util.Log;


public class Email extends AsyncTask<Void, Void, Void> {
    String response = "";
    private int number = 0;
    //envoie par Gmail meme problème
    public void mailLumiere() {
        try {
            //création d'un sender de type Gmail avec ses identifiant Gmail
            GMailSender sender = new GMailSender("theateamgeo@gmail.com", "theateam1234");
            //définie les différentes partie du mail
            sender.sendMail("Lucas test mail Lumiere",
                    "J'ai envoyé un mail car la lumière était trop basse",
                    "theateamgeo@gmail.com",
                    "theateamgeo@gmail.com");
        } catch (Exception e) {
            Log.e("SendMail", e.getMessage(), e);
        }

    }

    public void mailLocalisation() {
        try {
            //création d'un sender de type Gmail avec ses identifiant Gmail
            GMailSender sender = new GMailSender("theateamgeo@gmail.com", "theateam1234");
            //définie les différentes partie du mail
            sender.sendMail("Lucas test mail Localisation",
                    "J'ai envoyé un mail car je suis chez moi",
                    "theateamgeo@gmail.com",
                    "theateamgeo@gmail.com");
        } catch (Exception e) {
            Log.e("SendMail", e.getMessage(), e);
        }

    }
    public void setNumber(int number){
        this.number = number;
    }
    @Override
    protected Void doInBackground(Void... arg0) {
        try {
            switch (number) {
                case 1 : mailLumiere(); break;
                case 2 : mailLocalisation(); break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            response = "UnknownHostException: " + e.toString();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
       // textResponse.setText(response);
        super.onPostExecute(result);
    }
}