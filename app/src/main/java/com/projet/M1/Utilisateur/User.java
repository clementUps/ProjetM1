package com.projet.M1.Utilisateur;

/**
 * Created by clement on 28/03/2015.
 */
public class User {

    private static String id;

    private String nom;
    private  String password;
    private String email;

    public static void setId(String id) {
        User.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static String getIds() {
        return id;
    }

     public User(){
         id = "553651424d95cd6413609bde";
     }

    public User(String id){
        this.id = id;
    }
}
