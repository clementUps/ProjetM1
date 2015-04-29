package com.projet.M1.Json;

import android.util.Log;

import com.projet.M1.Utilisateur.Salle;
import com.projet.M1.Utilisateur.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by clement on 29/04/2015.
 */
public class Anctionner {
    private final String adresse = "/actionner";
    private InputStream in;
    private Salle[] salle;

    public Salle[] getSalle() {
        return salle;
    }

    public void actionner(){

        try {
            if(in != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String json = reader.readLine();
                JSONArray jsonArr = new JSONArray(json);
                salle = new Salle[jsonArr.length()];
                JSONObject jsonObj;
                for(int i = 0; i < jsonArr.length(); i++){
                    jsonObj = jsonArr.getJSONObject(i);
                    salle[i] = new Salle();
                    salle[i].set_id((String)jsonObj.get("_id"));
                    salle[i].setIsOn((boolean) jsonObj.get("isOn"));
                    salle[i].setTitle((String) jsonObj.get("title"));
                }
            }
        } catch(JSONException jsonE){
            Log.e("json erreur", "" + jsonE.getMessage());
        }
        catch (IOException ioE){
            ioE.printStackTrace();
        }
    }

    public void runJson(){
        Thread thread  = new Thread(){
            public void run(){
                try {
                    JSonParserThread jsonPareser = new JSonParserThread(new JSONObject(), adresse);
                    jsonPareser.getJson();
                    in = jsonPareser.getIn();
                    if(in != null) {
                        actionner();
                    } else{
                        Log.e("data json loggin", "impossible de recup les donne");
                    }

                } catch(Exception e){
                    e.printStackTrace();
                }
            }
        };
        thread.start();
        try {
            thread.join();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
