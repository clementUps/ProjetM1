package com.projet.M1;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.projet.M1.Json.JsonSingUp;
import com.projet.M1.Utilisateur.User;
import com.projet.M1.main.R;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by clement on 31/03/2015.
 */
public class SingUpFragment extends Fragment {
    public SingUpFragment(){}
    public static final String PREFS_NAME = "MyPrefsFile";
    private Button creerUtilisateur;
    private EditText prenom;
    private EditText nom;
    private EditText email;
    private EditText username;
    private EditText password;
    private  View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.singup, container, false);

        creerUtilisateur = (Button)rootView.findViewById(R.id.creerBouton);
        creerUtilisateur.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                singupEnvoie();
            }
        });
        return rootView;
    }

    public void singupEnvoie(){
        nom = (EditText)rootView.findViewById(R.id.firstname);
        prenom = (EditText) rootView.findViewById(R.id.lastname);
        email = (EditText)rootView.findViewById(R.id.email);
        username = (EditText) rootView.findViewById(R.id.username);
        password = (EditText) rootView.findViewById(R.id.password);
        if(username.getText().toString() != null && password.getText().toString() != null
            && nom.getText().toString() != null && prenom.getText().toString() != null
             && email.getText().toString() != null) {
            if (username.getText().toString() != "" && password.getText().toString() != ""
                    && password.getText().toString().length() > 6) {
                JsonSingUp json = new JsonSingUp(prenom.getText().toString(), nom.getText().toString(),
                        email.getText().toString(), username.getText().toString(),password.getText().toString());
                json.runJson();
                User user = json.getUser();
                Log.e("data json loggin ", " id " + user.getIds() + " email " + user.getNom());
                if(user != null) {
                    Set<String> users = new HashSet<String>();
                    users.add(user.getNom());
                    users.add(user.getIds());
                    users.add(user.getEmail());
                    users.add(user.getPassword());
                    SharedPreferences settings = getActivity().getSharedPreferences(PREFS_NAME, 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putStringSet("userLoggin", users);
                    // Commit the edits!
                    editor.commit();
            /*FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, new HomeFragment()).commit();*/
                    getActivity().finish();
                    startActivity(getActivity().getIntent());
                } else {
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame_container, new SingUpFragment()).commit();
                }

            }
        } else {
            TextView colorPassword = (TextView) rootView.findViewById(R.id.textpassword);
            colorPassword.setBackgroundColor(Color.RED);
            TextView coloruserName = (TextView) rootView.findViewById(R.id.textusername);
            coloruserName.setBackgroundColor(Color.RED);
        }


    }

}
