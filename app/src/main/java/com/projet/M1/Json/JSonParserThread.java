package com.projet.M1.Json;

import android.os.Looper;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URI;

/**
 * Created by clement on 17/02/2015.
 */
public class JSonParserThread{// extends Thread {//implements Runnable{

    private final String ip = "192.168.1.17";//"192.168.1.25";
    private final int port = 8081;
    private String adresse;

    private JSONObject json;
    private InputStream in = null;

    public JSonParserThread(JSONObject json,String adresse){
        this.json = json;
        this.adresse = adresse;

    }

    public InputStream getIn() {
        return in;
    }

    public void sendJson() {
       // Looper.prepare(); //For Preparing Message Pool for the child Thread
        HttpClient client = new DefaultHttpClient();
        HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); //Timeout Limit
        HttpResponse response;

        try {
            URI uri = new URI("http", "", ip, port, adresse, "", "");
            HttpPost post = new HttpPost(uri);
            StringEntity se = new StringEntity( json.toString());
            se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            post.setEntity(se);
            response = client.execute(post);

            /*Checking response */
            if(response!=null){
                in = response.getEntity().getContent(); //Get the data in the entity
                Log.e("data json request", " sved");
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        //Looper.loop(); //Loop in the message queue
    }



  /* @Override
    public void run() {
        sendJson();
    }*/
}
