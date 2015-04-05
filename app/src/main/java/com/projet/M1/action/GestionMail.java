package com.projet.M1.action;

import android.util.Log;

import com.projet.M1.email.Email;

/**
 * Created by Lucas on 17/03/2015.
 */
public class GestionMail extends Action {
    boolean envoye = false;
    int numMail;

    public GestionMail(int num) {
        this.numMail = num;
    }

    public boolean isEnvoye() {
        return envoye;
    }

    @Override
    public void actionner() {

        if (!envoye) {
            Log.i("Mail", "J'envoie un vrai mail");
            Email envoieMail = new Email();
            envoieMail.setNumber(numMail);
            envoieMail.execute();
        }
        envoye = true;
    }
}
