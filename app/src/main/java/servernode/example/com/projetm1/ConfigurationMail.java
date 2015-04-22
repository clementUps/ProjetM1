package servernode.example.com.projetm1;

import android.app.Activity;
import android.app.FragmentManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.projet.M1.action.GestionMail;
import com.projet.M1.main.R;


public class ConfigurationMail extends Fragment {


    private Button validerButton;
    private EditText objet;
    private EditText contenu;
    private EditText destinataire;
    public ConfigurationMail() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_configuration_mail, container, false);
        validerButton = (Button)rootview.findViewById(R.id.validerButtonMail);
        destinataire = (EditText)rootview.findViewById(R.id.editText);
        objet = (EditText)rootview.findViewById(R.id.editText2);
        contenu = (EditText)rootview.findViewById(R.id.editText3);
        validerButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                envoieMail();
            }
        });
        return rootview;
    }

    public void envoieMail(){
        GestionMail mail  = new GestionMail(objet.getText().toString(), contenu.getText().toString(), destinataire.getText().toString());
        CreationModule fragment = new CreationModule();
        Communicator communication  = (Communicator)getActivity();
        communication.setAction(mail);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frame_container, fragment).commit();
    }

}
