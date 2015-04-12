package com.projet.M1.evenement;
import com.projet.M1.action.Action;
import com.projet.M1.enumerations.Conditions;

import java.util.ArrayList;

/**
 * Created by clement on 15/03/2015.
 */
public abstract class Capteur {
    private String nom;
    private final int sensorType;
    private boolean opposite; //sert à savoir si la condition est un NOT
    private ArrayList<Action> actionAssoc;
    Conditions conditions; //selon cette condition on ira voir l'autre capteur
    private Capteur capteur; //permet de gérer récurisevement les capteurs avec une infinité de conditions.

    public Capteur(String nom, boolean opposite, Conditions conditions,int sensorType) {
        this.sensorType = sensorType;
        this.nom = nom;
        this.opposite = opposite;
        this.conditions = conditions;
        this.actionAssoc = new ArrayList<Action>();
    }
    public int getSensorType(){
        return sensorType;
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

    public void ajouterAction(Action action) {
        actionAssoc.add(action);
    }

    public void supprimerAction(Action action) {
        actionAssoc.remove(action);
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

    public void setActionAssoc(ArrayList<Action> actionAssoc) {
        this.actionAssoc = actionAssoc;
    }

    public void setCapteur(Capteur capteur) {
        this.capteur = capteur;
    }

    public abstract void initialiser();
}
