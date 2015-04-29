package com.projet.M1.Json;

import android.os.Looper;
import android.util.Log;

import com.projet.M1.Utilisateur.Salle;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URI;

/**
 * Created by clement on 17/02/2015.
 */
public class SendAction extends Thread{//implements Runnable{

    private final String ip ="192.168.1.10";
    private final int port = 8081;
    private final String adresse = "/jsonListener/jsonParser/addJSon";
    private String idUser;
    private Salle salle;


    private String event;
    private String action;
    public SendAction(String event,String action){
        this.event = event;
        this.action = action;

    }
    public SendAction(String event,String action,Salle salle,String idUser){
        this.event = event;
        this.action = action;
        this.salle = salle;
        this.idUser = idUser;
    }
    private void sendJson() {
        Looper.prepare(); //For Preparing Message Pool for the child Thread
        HttpClient client = new DefaultHttpClient();
        HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); //Timeout Limit
        HttpResponse response;
        JSONObject json = new JSONObject();

        try {
            URI uri = new URI("http", "", ip, port, adresse, "", "");
            HttpPost post = new HttpPost(uri);
            json.put("evenement", event);
            json.put("action", action);
            json.put("id", idUser);
            JSONObject array = new JSONObject();
            array.put("_id",salle.get_id());
            array.put("title",salle.getTitle());
            array.put("isOn",salle.isOn());
            Log.e("lol",array.toString());
            json.put("_actionner", array);
            StringEntity se = new StringEntity( json.toString());
            se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            post.setEntity(se);
            response = client.execute(post);

            /*Checking response */
            if(response!=null){
                InputStream in = response.getEntity().getContent(); //Get the data in the entity
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

        Looper.loop(); //Loop in the message queue

    }

    public void runJson(){
         Thread thread  = new Thread(){
             public void run(){
                 sendJson();
             }
         };
         thread.start();
     }

    @Override
    public void run() {
        sendJson();
    }
}
