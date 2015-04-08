package servernode.example.com.projetm1;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;


import com.projet.M1.main.R;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class CreationNouvelleAction extends Fragment {
    public ListView listeViewAction;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CreationNouvelleAction() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_liste_action, container, false);
        listeViewAction = (ListView) view.findViewById(R.id.listViewAction);
        // Set the adapter
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(),R.array.action,android.R.layout.simple_list_item_1);
        if(adapter == null){
            Log.e("adapter","erreur adpater");
        }
        listeViewAction.setAdapter(adapter);
        listeViewAction.setOnItemClickListener(new OnItemClickListener() {
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
                fragment = new ConfigurationAction();
                ((ConfigurationAction)fragment).setPosition(position);
                break;
            case 3:
                fragment = new ConfigurationMail();
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onNouvelleActionInteraction(int position);
    }

}
