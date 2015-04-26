package servernode.example.com.projetm1;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.projet.M1.main.R;

public class ChoixStructure extends Fragment {

    private Button validerButton;

    public static ChoixStructure newInstance(String param1, String param2) {
        ChoixStructure fragment = new ChoixStructure();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public ChoixStructure() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choix_structure, container, false);
        validerButton = (Button)view.findViewById(R.id.structbutton);
        validerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().
                        replace(R.id.frame_container, new CreationNouvelleEvenement()).commit();
            }
        });
        return view;
    }
}
