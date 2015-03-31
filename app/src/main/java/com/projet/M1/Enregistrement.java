package com.projet.M1;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.projet.M1.main.R;

/**
 * Created by clement on 31/03/2015.
 */
public class Enregistrement extends Fragment {

        public Enregistrement(){}
        private Button singin;
        private Button singup;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.enregistrement, container, false);
            singin = (Button)rootView.findViewById(R.id.loggingButton);
            singup = (Button)rootView.findViewById(R.id.creerBouton);
            singin.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    logging();
                }
            });
            singup.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    singupUser();
                }
            });
            return rootView;
        }

        public void logging(){
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, new LogginFragment()).commit();
        }
        public void singupUser(){
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, new SingUpFragment()).commit();

        }
 }

