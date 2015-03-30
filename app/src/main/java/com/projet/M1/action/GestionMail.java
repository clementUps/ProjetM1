package com.projet.M1.action;

import android.util.Log;

import com.projet.M1.email.Email;

/**
 * Created by Lucas on 17/03/2015.
 */
public class GestionMail extends Action {

    boolean envoye = false;
    @Override
    public void actionner() {
        if (!envoye) {
            Log.i("Mail", "J'envoie un vrai mail");
            Email envoieMail = new Email();
            envoieMail.setNumber(1);
            envoieMail.execute();
        }
        envoye = true;
    }
}
