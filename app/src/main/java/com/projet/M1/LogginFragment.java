package com.projet.M1;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.projet.M1.Json.JsonLoggin;
import com.projet.M1.Utilisateur.User;
import com.projet.M1.main.MainActivity;
import com.projet.M1.main.R;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by clement on 30/03/2015.
 */
public class LogginFragment extends Fragment{
    private Button loggingButton;
    private static View view;
    private EditText userNames,passwords;
    public static final String PREFS_NAME = "MyPrefsFile";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e("MyApp", "Probleme ");
        view = inflater.inflate(R.layout.logging, container, false);
        loggingButton = (Button)view.findViewById(R.id.boutonLogging);
        loggingButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                logging();
            }
        });
        return view;
    }

    public void logging() {
        userNames = (EditText)view.findViewById(R.id.username);
        passwords = (EditText) view.findViewById(R.id.password);

        Log.e("data json loggin "," user name "+userNames.getText().toString()+" password "+passwords.getText().toString());
        JsonLoggin json = new JsonLoggin(userNames.getText().toString(),passwords.getText().toString());
        json.runJson();
        User user = json.getUser();
        Log.e("data json loggin "," id "+user.getIds()+" email "+user.getNom());
        if(user != null) {
            Set<String> users = new HashSet<String>();
            users.add("nom:"+user.getNom());
            users.add("id:"+user.getIds());
            users.add("mail:"+user.getEmail());
            users.add("password:"+user.getPassword());
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
                    .replace(R.id.frame_container, new LogginFragment()).commit();
        }

    }
}
