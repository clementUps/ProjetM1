package com.projet.M1.main;

import com.projet.M1.action.Action;
import com.projet.M1.action.GestionMail;
import com.projet.M1.evenement.Capteur;
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

    public void miseAJourCapteur(Capteur c, float val)
    {
        for (Capteur cap : gestionCapteur){
            if (cap.equals(c)) {
                if (cap instanceof Luminosite)
                ((Luminosite) cap).setLux(val);
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
                        if(((Luminosite) c).getNbLuxCond() >= ((Luminosite) c).getLux() )
                            act.actionner();
                }
            }
        }
    }

}
