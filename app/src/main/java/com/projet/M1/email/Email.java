package com.projet.M1.email;

import android.os.AsyncTask;
import android.util.Log;


public class Email extends AsyncTask<Void, Void, Void> {
    String response = "";
    private int number = 0;
    //envoie par Gmail meme problème
    public void testMail() {
        try {
            //création d'un sender de type Gmail avec ses identifiant Gmail
            GMailSender sender = new GMailSender("theateamgeo@gmail.com", "theateam1234");
            //définie les différentes partie du mail
            sender.sendMail("Lucas test mail",
                    "J'ai envoyé un mail car la lumière était trop basse",
                    "theateamgeo@gmail.com",
                    "theateamgeo@gmail.com");
        } catch (Exception e) {
            Log.e("SendMail", e.getMessage(), e);
        }

    }
    // envoie d'un mail
    public void mailTruc(){
        // création de l'utilisateur du mail
        Mail m = new Mail("theateamgeo@gmail.com", "theateam1234");
        //destinataire
        String[] toArr = {"theateamgeo@gmail.com"};
        m.setTo(toArr);
        // de la part de
        m.setFrom("theateamgeo@gmail.com");
        // objet du mail
        m.setSubject("This is an email sent using my Mail JavaMail wrapper from an Android device.");
        // corp du mail
        m.setBody("mail body.");
        try {
            m.send();
        }
        catch (Exception e){
            Log.e("myaappException", "truc" + e.toString());
        }
    }
    public void setNumber(int number){
        this.number = number;
    }
    @Override
    protected Void doInBackground(Void... arg0) {
        try {
            if(number != 1) {
                mailTruc();
            }
            else{
                testMail();
            }
        } catch (Exception e) {
            e.printStackTrace();
            response = "UnknownHostException: " + e.toString();
        }
        return null;
    }


    @Override
    protected void onPostExecute(Void result) {
       // textResponse.setText(response);
        super.onPostExecute(result);
    }
}