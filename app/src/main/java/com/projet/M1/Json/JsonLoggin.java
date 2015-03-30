package com.projet.M1.Json;

import android.util.Log;

import com.projet.M1.Utilisateur.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by clement on 28/03/2015.
 */
public class JsonLoggin {
        private final String adresse = "/auth/signin";

        private String userName;
        private String password;
        private InputStream in;
        private User user;
        public String getAdresse() {
            return adresse;
        }

        public JsonLoggin(String userName,String password){
            this.userName = userName;
            this.password = password;
        }

        public JSONObject creerJSon() throws JSONException {
            JSONObject json = new JSONObject();
            json.put("username",userName);
            json.put("password",password);
            return json;
        }

    public User getUser() {
        return user;
    }

    public void loggin(){
            try {
                if(in != null){
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    String json = reader.readLine();
                    Log.e("data json request"," "+json);
                    JSONObject jsonObj = new JSONObject(json);

                    User user = new User();
                    user.setId((String)jsonObj.get("_id"));
                    user.setEmail((String)jsonObj.get("email"));
                    user.setPassword(password);
                    user.setNom(userName);
                    this.user = user;
                }else {
                    this.user = null;
                }
            } catch(JSONException jsonE){
                jsonE.printStackTrace();
            }
            catch (IOException ioE){
                ioE.printStackTrace();
            }
        }

    public void runJson(){
        Thread thread  = new Thread(){
            public void run(){
                try {
                    JSonParserThread jsonPareser = new JSonParserThread(creerJSon(), adresse);
                    Log.e("data json loggin", "recup les donne");
                    jsonPareser.sendJson();
                    in = jsonPareser.getIn();
                    if(in != null) {
                        loggin();
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
