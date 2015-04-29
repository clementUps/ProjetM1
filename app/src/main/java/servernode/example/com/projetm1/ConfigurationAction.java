package servernode.example.com.projetm1;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Audio.Radio;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.projet.M1.Json.Anctionner;
import com.projet.M1.Utilisateur.Salle;
import com.projet.M1.action.GestionMail;
import com.projet.M1.enumerations.Conditions;
import com.projet.M1.evenement.Luminosite;
import com.projet.M1.main.MainActivity;
import com.projet.M1.main.R;

import java.util.Iterator;
import java.util.Set;

public class ConfigurationAction extends Fragment implements AdapterView.OnItemSelectedListener{

    private SeekBar barreDefilement;
    private TextView progression;
    private RadioButton radioALlume;
    private RadioButton radioEteind;
    private TextView intitule;
    private TextView unite;
    private int position;
    private RadioGroup radioGroup;
    private Spinner spinner;
    private Button validerButtonAction;
    private int selection=-1;
    private Salle[] salle;
    private boolean isOn = true;


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
        radioALlume = (RadioButton)view.findViewById(R.id.allumerRadioButton);
        radioEteind = (RadioButton)view.findViewById(R.id.eteindreRadioButton);
        spinner = (Spinner)view.findViewById(R.id.spinner);
        Anctionner actionner = new Anctionner();
        actionner.runJson();
        salle = actionner.getSalle();
        String[] str = new String[salle.length];
        for(int i=0; i < salle.length; i++){
            str[i] = new String(salle[i].getTitle());
        }
        ArrayAdapter adapter = new ArrayAdapter(getActivity().getApplicationContext(),android.R.layout.simple_spinner_item,str);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
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
        radioALlume.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                isOn = true;
            }
        });
        radioEteind.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                isOn = false;
            }
        });

        barreDefilement.setEnabled(false);
        return view;
    }

    public void initialisationAction(){
        String idUser="";
        SharedPreferences settings = getActivity().getSharedPreferences(MainActivity.PREFS_NAME, 0);
        Set<String> users = settings.getStringSet("userLoggin", null);
        if(users != null){
            Iterator iter = users.iterator();
            String[]  affiche= {"","Aucun utilisateur détecté. " +
                    "Veuillez vous déconnecter et vous reconnecter"};
            while (iter.hasNext()){
                String splitter = (String)iter.next();
                if(splitter.contains("id:")){
                    affiche  = splitter.split(":");
                }
            }
            idUser = affiche[1];
        }
        Salle sa = salle[selection==-1?0:selection];
        sa.setIsOn(isOn);
        GestionMail mail  = new GestionMail("testSujet", "testBody", "theateamgeo@gmail.com",sa,idUser);
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


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selection = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
