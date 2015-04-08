package servernode.example.com.projetm1;

import android.app.FragmentManager;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.projet.M1.main.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link #} factory method to
 * create an instance of this fragment.
 */
public class CreationModule extends Fragment {


    Button evenement;
    Button action;
    public CreationModule() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_nouveau_module, container, false);
        evenement = (Button) rootview.findViewById(R.id.evenementButton);
        action = (Button) rootview.findViewById(R.id.actionButton);
        evenement.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                evenement();
            }
        });
        action.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                action();
            }
        });
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
}
