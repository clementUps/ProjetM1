package servernode.example.com.projetm1;

import android.app.Fragment;
import android.app.FragmentManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Audio.Radio;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.TextView;

import com.projet.M1.action.GestionMail;
import com.projet.M1.enumerations.Conditions;
import com.projet.M1.evenement.Luminosite;
import com.projet.M1.main.R;

public class ConfigurationAction extends Fragment {

    private SeekBar barreDefilement;
    private TextView progression;
    private TextView intitule;
    private TextView unite;
    private int position;
    private RadioGroup radioGroup;
    private Button validerButtonAction;



    public ConfigurationAction() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_configuration_action, container, false);
        barreDefilement = (SeekBar) view.findViewById(R.id.defilementBar);
        progression = (TextView) view.findViewById (R.id.numberSeekText);
        intitule = (TextView) view.findViewById (R.id.intituleText);
        unite = (TextView) view.findViewById (R.id.unite);
        radioGroup = (RadioGroup) view.findViewById(R.id.radioGroupAction);
        validerButtonAction = (Button)view.findViewById(R.id.validerButtonAction);
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
        radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (radioGroup.getChildAt(2).getId() == checkedId) {
                    barreDefilement.setEnabled(true);
                } else {
                    barreDefilement.setEnabled(false);
                }
            }
        });
        validerButtonAction.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                initialisationAction();
            }
        });
        barreDefilement.setEnabled(false);
        return view;
    }

    public void initialisationAction(){
        GestionMail mail  = new GestionMail("testSujet", "testBody", "theateamgeo@gmail.com");
        CreationModule fragment = new CreationModule();
        Communicator communication  = (Communicator)getActivity();
        communication.setAction(mail);
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frame_container, fragment).commit();
    }

    public void setPosition(int position) {
        this.position = position;
    }


}
