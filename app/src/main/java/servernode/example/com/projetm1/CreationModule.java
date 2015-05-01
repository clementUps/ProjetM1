package servernode.example.com.projetm1;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.projet.M1.Module.Module;
import com.projet.M1.Module.ModuleException;
import com.projet.M1.action.Action;
import com.projet.M1.evenement.Capteur;
import com.projet.M1.evenement.Localisation;
import com.projet.M1.evenement.Luminosite;
import com.projet.M1.main.R;



/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link #} factory method to
 * create an instance of this fragment.
 */
public class CreationModule extends Fragment {

    private Capteur capteur = null ;
    private Action action = null;
    private Button evenement;
    private Button actionButton;
    private Button creerModuleButton;
    public static final int SCREEN_OFF_RECEIVER_DELAY = 500;
    public CreationModule() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_nouveau_module, container, false);
        evenement = (Button) rootview.findViewById(R.id.evenementButton);
        actionButton = (Button) rootview.findViewById(R.id.actionButton);
        creerModuleButton = (Button) rootview.findViewById(R.id.ajouterModule);
        creerModuleButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                creationModule();
            }
        });
        evenement.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                evenement();
            }
        });
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                action();
            }
        });
        Communicator communication = (Communicator)getActivity();
        boolean capteurTest = false;
        capteur = communication.getCapteur();
        if(capteur != null) {
            Drawable img = getActivity().getResources().getDrawable(R.drawable.lumiere);
            img.setBounds(0, 0, 60, 60);
            evenement.setCompoundDrawables(img, null, null, null);
            evenement.setText("");
            capteurTest = true;
        } else {
            creerModuleButton.setEnabled(false);
        }
        action = communication.getAction();
        if(action != null) {
            Drawable img = getActivity().getResources().getDrawable(R.drawable.mail);
            img.setBounds(0, 0, 60, 60);
            actionButton.setCompoundDrawables(img, null, null, null);
            actionButton.setText("");
            if(capteurTest){
                creerModuleButton.setEnabled(true);
            }
        } else {
            creerModuleButton.setEnabled(false);
        }
        return rootview;
    }
    public void action(){
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frame_container, new CreationNouvelleAction()).commit();
    }
    public void evenement(){
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frame_container, new CreationNouvelleEvenement()).commit();
    }

    public void creationModule(){
        Communicator com = (Communicator)getActivity();
        final Module module;

        if (com.getCapteur() instanceof Luminosite) {
            module = new Module(com.getCapteur(), com.getAction(), (SensorManager) getActivity().getSystemService((getActivity()).SENSOR_SERVICE));
        } else if (com.getCapteur() instanceof Localisation) {
            module = new Module(com.getCapteur(), com.getAction(), (LocationManager)getActivity().getSystemService((getActivity()).LOCATION_SERVICE));
        } else {
            module = null;
        }
        Thread thread  = new Thread() {
            public void run() {
                Looper.prepare();
                android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);
                try {
                    module.init();
                    module.setCurrentThread(Thread.currentThread());
                } catch(ModuleException e){
                    e.printStackTrace();
                  new AlertDialog.Builder(getActivity())
                            .setTitle("Impossible de creer le module").show();
                }
            }
        };
        thread.start();
        try {
            thread.join();
        }catch (Exception e){
            e.printStackTrace();
        }
        com.addModule(module);
    }

}
