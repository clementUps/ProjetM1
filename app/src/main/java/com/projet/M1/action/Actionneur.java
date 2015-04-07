package com.projet.M1.action;

import com.projet.M1.action.Action;
import com.projet.M1.action.GestionMail;
import com.projet.M1.evenement.Capteur;
import com.projet.M1.evenement.Localisation;
import com.projet.M1.evenement.Luminosite;

import java.util.ArrayList;

/**
 * Created by Lucas on 17/03/2015.
 */
public class Actionneur {

    private ArrayList<Capteur> gestionCapteur;

    public Actionneur(){
        this.gestionCapteur = new ArrayList<Capteur>();
    }

    public void ajouterCapteur(Capteur c){
        gestionCapteur.add(c);
    }

    public void supprimerCapteur(Capteur c){
        gestionCapteur.remove(c);
    }

    // Mise a jour capteur Luminosite
    public void miseAJourCapteur(Luminosite c, float lux)
    {
        for (Capteur cap : gestionCapteur){
            if (cap.equals(c)) {
                ((Luminosite) cap).setLux(lux);
            }
        }
    }

    // Mise a jour capteur Localisation
    public void miseAJourCapteur(Localisation c, double latitude, double longitude, float precision)
    {
        for (Capteur cap : gestionCapteur){
            if (cap.equals(c)) {
                if (cap instanceof Localisation) {
                    ((Localisation) cap).setLatitude(latitude);
                    ((Localisation) cap).setLongitude(longitude);
                    ((Localisation) cap).setAccuracy(precision);
                }
            }
        }
    }

    public void gererCapteur() {
        for (Capteur c : gestionCapteur){
            if (c instanceof Luminosite)
            {
                for (Action act : c.getActionAssoc())
                {
                    if (act instanceof GestionMail)
                        if(((Luminosite) c).getNbLuxCond() >= ((Luminosite) c).getLux() ) {
                            act.actionner();
                        }
                }
            }

            if (c instanceof Localisation)
            {
                for (Action act : c.getActionAssoc())
                {
                    if (act instanceof GestionMail)
                        if(((Localisation) c).getLatitude() + ((Localisation) c).getPrecision() >=
                                ((Localisation) c).getLatitudeCond() ||
                                ((Localisation) c).getLatitude() - ((Localisation) c).getPrecision() >=
                        ((Localisation) c).getLatitudeCond()) {
                            if (((Localisation) c).getLongitude() + ((Localisation) c).getPrecision() >=
                                    ((Localisation) c).getLongitudeCond() ||
                            ((Localisation) c).getLongitude() - ((Localisation) c).getPrecision() >=
                                    ((Localisation) c).getLongitudeCond()) {
                                act.actionner();
                            }
                        }
                }
            }
        }
    }

}
