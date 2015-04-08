package servernode.example.com.projetm1;

import android.app.Activity;
import android.app.FragmentManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.projet.M1.action.GestionMail;
import com.projet.M1.main.R;


public class ConfigurationMail extends Fragment {


    private Button validerButton;
    public ConfigurationMail() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_configuration_mail, container, false);
        validerButton = (Button)rootview.findViewById(R.id.validerButtonMail);
        validerButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                envoieMail();
            }
        });
        return rootview;
    }

    public void envoieMail(){
        GestionMail mail  = new GestionMail(1);
        CreationModule fragment = new CreationModule();
        Communicator communication  = (Communicator)getActivity();
        communication.setAction(mail);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frame_container, fragment).commit();
    }

}
