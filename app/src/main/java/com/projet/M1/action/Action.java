package com.projet.M1.action;

import java.util.TimerTask;

/**
 * Created by clement on 15/03/2015.
 */
public abstract class Action extends TimerTask{
    private String nom;

    public  abstract void actionner();


}
