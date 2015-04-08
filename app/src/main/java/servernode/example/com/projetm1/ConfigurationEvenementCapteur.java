package servernode.example.com.projetm1;

import android.app.FragmentManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.projet.M1.enumerations.Conditions;
import com.projet.M1.evenement.Luminosite;
import com.projet.M1.main.MainActivity;
import com.projet.M1.main.R;

public class ConfigurationEvenementCapteur extends Fragment {


    private SeekBar barreDefilement;
    private TextView progression;
    private TextView intitule;
    private TextView unite;
    private int position;
    private Button validerButton;



    public ConfigurationEvenementCapteur() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_configuration_evenement_capteur, container, false);
        barreDefilement = (SeekBar) view.findViewById(R.id.defilementBar);
        progression = (TextView) view.findViewById (R.id.numberSeekText);
        intitule = (TextView) view.findViewById (R.id.intituleText);
        unite = (TextView) view.findViewById (R.id.unite);
        validerButton = (Button)view.findViewById(R.id.validerButton);
        switch(position){
            case 0:
                unite.setText("lux");
                intitule.setText("lumiere");
                break;
            case 1:
                unite.setText("°C");
                intitule.setText("temparature");
                break;
            case 2:
                unite.setText("db");
                intitule.setText("Sonor");
                break;
        }
        progression = (TextView) view.findViewById (R.id.numberSeekText);
        progression = (TextView) view.findViewById (R.id.numberSeekText);

        barreDefilement.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progression.setText(""+progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        validerButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                initialisationEvenement();
            }
        });
        return view;
    }
    public void setPosition(int position) {
        this.position = position;
    }

    public void initialisationEvenement(){
        Luminosite lumiere  = new Luminosite("Luminosite", false, Conditions.IF_THEN, barreDefilement.getProgress());
        CreationModule fragment = new CreationModule();
        Communicator communication  = (Communicator)getActivity();
        communication.setCapteur(lumiere);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frame_container, fragment).commit();
    }

}
