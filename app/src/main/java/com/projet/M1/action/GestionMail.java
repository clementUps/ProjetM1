package com.projet.M1.action;

import android.util.Log;
import com.projet.M1.Json.SendAction;
import com.projet.M1.email.Email;


/**
 * Created by Lucas on 17/03/2015.
 */
public class GestionMail extends Action {
    boolean envoye = false;
    private String objet;
    private String contenu;
    private String destinataire;

    public GestionMail(String objet, String contenu, String destinataire) {
        this.objet = objet;
        this.contenu = contenu;
        this.destinataire = destinataire;
    }

    public boolean isEnvoye() {
        return envoye;
    }

    @Override
    public void actionner() {

        if (!envoye) {
            Log.i("Mail", "J'envoie un vrai mail");
            Email envoieMail = new Email();
            envoieMail.setObjet(objet);
            envoieMail.setContenu(contenu);
            envoieMail.setDestinaire(destinataire);
            SendAction json = new SendAction("lumiere", "mail");
            json.runJson();
            envoieMail.execute();
        }
        envoye = true;
    }

    @Override
    public void run() {
        envoye = false;
    }
}
