package com.projet.M1.email;

import android.os.AsyncTask;
import android.util.Log;


public class Email extends AsyncTask<Void, Void, Void> {
    String response = "";
    private String objet;
    private String contenu;
    private String destinaire;

    public void setObjet(String objet) {
        this.objet = objet;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public void setDestinaire(String destinaire) {
        this.destinaire = destinaire;
    }

    @Override
    protected Void doInBackground(Void... arg0) {
        try {
            // création d'un sender de type Gmail avec ses identifiant Gmail
            GMailSender sender = new GMailSender("theateamgeo@gmail.com", "theateam1234");
            //définie les différentes partie du mail
            sender.sendMail(objet,
                    contenu,
                    "theateamgeo@gmail.com",
                    destinaire);
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