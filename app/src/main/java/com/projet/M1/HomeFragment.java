package com.projet.M1;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.projet.M1.Json.JsonLoggin;
import com.projet.M1.Module.Module;
import com.projet.M1.main.MainActivity;
import com.projet.M1.main.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import servernode.example.com.projetm1.CreationModule;

public class HomeFragment extends Fragment {
	
	public HomeFragment(){}


	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        TextView nomUtilisateur = (TextView) rootView.findViewById(R.id.nomUtilisateur);
        SharedPreferences settings = getActivity().getSharedPreferences(MainActivity.PREFS_NAME, 0);
        Set<String> users = settings.getStringSet("userLoggin", null);
        settings = getActivity().getSharedPreferences(MainActivity.PREFS_NAME, 0);
        JSONObject json = new JSONObject();
        if(users == null){
            nomUtilisateur.setText("Aucun utilisateur détecté. " +
                    "Veuillez vous déconnecter et vous reconnecter");
        }else {
            nomUtilisateur.setText((String) users.toArray()[0]);
        }
        Button addModule = (Button) rootView.findViewById(R.id.addModuleButton);
        addModule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container, new CreationModule()).commit();
            }
        });
        return rootView;
    }
}
