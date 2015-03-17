package com.projet.M1.evenement;
import com.projet.M1.action.Action;
import com.projet.M1.enumerations.Conditions;

import java.util.ArrayList;

/**
 * Created by clement on 15/03/2015.
 */
public abstract class Capteur {
    private String nom;
    private boolean opposite; //sert à savoir si la condition est un NOT
    private ArrayList<Action> actionAssoc;
    Conditions conditions; //selon cette condition on ira voir l'autre capteur
    private Capteur capteur; //capteur associé pour une infinité de conditions

    public Capteur(String nom, boolean opposite) {
        this.nom = nom;
        this.opposite = opposite;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public boolean isOpposite() {
        return opposite;
    }

    public void setOpposite(boolean opposite) {
        this.opposite = opposite;
    }

    public ArrayList<Action> getActionAssoc() {
        return actionAssoc;
    }

    public void setActionAssoc(ArrayList<Action> actionAssoc) {
        this.actionAssoc = actionAssoc;
    }

    public Conditions getConditions() {
        return conditions;
    }

    public void setConditions(Conditions conditions) {
        this.conditions = conditions;
    }

    public Capteur getCapteur() {
        return capteur;
    }

    public void setCapteur(Capteur capteur) {
        this.capteur = capteur;
    }
}
