package servernode.example.com.projetm1;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.projet.M1.main.R;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the
 * {@link servernode.example.com.projetm1.CreationNouvelleEvenement.OnSelectedEventListener}
 * interface.
 */
public class CreationNouvelleEvenement extends Fragment{
    private ListView listeViewEvenement;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CreationNouvelleEvenement() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_liste_evenement, container, false);
        listeViewEvenement = (ListView) view.findViewById(R.id.listViewEvenement);
        // Set the adapter
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(),R.array.evenement,android.R.layout.simple_list_item_1);
        listeViewEvenement.setAdapter(adapter);
        listeViewEvenement.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listOnClick(position);
            }
        });

        return view;
    }

    public void listOnClick(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
            case 1:
            case 2:
                fragment = new ConfigurationEvenementCapteur();
                ((ConfigurationEvenementCapteur)fragment).setPosition(position);
                break;
            case 4:
                fragment = new Geolocalisation();
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment).commit();

        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }


    public interface OnSelectedEventListener {
        public void onFragmentInteraction(int position);
    }

}
