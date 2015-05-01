package servernode.example.com.projetm1;

import android.app.Activity;
import android.app.FragmentManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.projet.M1.enumerations.Conditions;
import com.projet.M1.evenement.Localisation;
import com.projet.M1.main.MainActivity;
import com.projet.M1.main.R;


public class Geolocalisation extends Fragment {
    MapFragment mMapFragment;
    GoogleMap mMap;
    Marker mMark;

    public Geolocalisation() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_geolocalisation, container, false);
        mMapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mMap = mMapFragment.getMap();
        setUpMapListener(view);
        setUpButtonsListener(view);
        return view;
    }

    private void setUpButtonsListener(final View view) {
        Button buttonAddMark = (Button) view.findViewById(R.id.buttonAddMark);
        buttonAddMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.fragmentManager.beginTransaction()
                        .hide(MainActivity.fragmentManager.findFragmentById(R.id.map)).commit();
                Button buttonAddMark = (Button) view.findViewById(R.id.buttonAddMark);
                buttonAddMark.setVisibility(View.INVISIBLE);
                RelativeLayout layoutPrecision =
                        (RelativeLayout) view.findViewById(R.id.layoutPrecision);
                TextView textLatitude = (TextView) view.findViewById(R.id.textLatitude);
                textLatitude.setText(Double.toString(mMark.getPosition().latitude));
                TextView textLongitude = (TextView) view.findViewById(R.id.textLongitude);
                textLongitude.setText(Double.toString(mMark.getPosition().longitude));
                layoutPrecision.setVisibility(View.VISIBLE);
            }
        });
        Button buttonFinishMark = (Button) view.findViewById(R.id.buttonFinishMark);
        buttonFinishMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText champPrecision = (EditText) view.findViewById(R.id.editText4);
                double precision = 0.0;
                if (champPrecision.getText().toString().length() > 0) {
                    precision = Double.parseDouble(champPrecision.getText().toString());
                }
                Localisation newLoc = new Localisation("Test", false, Conditions.IF_THEN,
                        mMark.getPosition().latitude, mMark.getPosition().longitude,precision);
                CreationModule fragment = new CreationModule();
                Communicator communication  = (Communicator)getActivity();
                communication.setCapteur(newLoc);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container, fragment).commit();
            }
        });
    }

    private void setUpMapListener(final View view) {
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if (mMark==null) {
                    mMark = mMap.addMarker(new MarkerOptions().position(latLng));
                    Button buttonAddMark = (Button) view.findViewById(R.id.buttonAddMark);
                    buttonAddMark.setEnabled(true);
                }else{
                    mMap.clear();
                    mMark = mMap.addMarker(new MarkerOptions().position(latLng));
                }
            }
        });
    }

    /**** The mapfragment's id must be removed from the FragmentManager
     **** or else if the same it is passed on the next time then
     **** app will crash ****/
    @Override
    public void onDestroyView() {
        super.onDestroyView();
            MainActivity.fragmentManager.beginTransaction()
                    .remove(MainActivity.fragmentManager.findFragmentById(R.id.map)).commit();
        }

}
