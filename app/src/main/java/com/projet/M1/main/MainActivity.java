package com.projet.M1.main;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import servernode.example.com.projetm1.*;


public class MainActivity extends ActionBarActivity
        implements fragment_ecran_titre.OnFragmentInteractionListener,
        nouvelEvenement.OnFragmentInteractionListener,
        nouvelleAction.OnFragmentInteractionListener,
        nouveauModule.OnFragmentInteractionListener,
        MenuFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.fragment_container) != null) {

            if (savedInstanceState != null) return;

            Fragment firstFragment = new nouveauModule();

            firstFragment.setArguments(getIntent().getExtras());

            getFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, firstFragment).commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void nouvEvenement(View view) {
        Fragment nouvFragment = new nouvelEvenement();

        transactionFragment(nouvFragment);
    }

    public void nouvAction(View view) {
        Fragment nouvFragment = new nouvelleAction();

        transactionFragment(nouvFragment);
    }

    public void menuEvenement(View view) {
        View fragmentMenu = findViewById(R.id.fragment);
        ViewGroup.LayoutParams params = fragmentMenu.getLayoutParams();
        params.width = findViewById(R.id.mainActivity).getWidth();
        fragmentMenu.setLayoutParams(params);
        View boutonMenu = findViewById(R.id.button8);
        boutonMenu.setVisibility(View.INVISIBLE);
        View fragmentContainer = findViewById(R.id.fragment_container);
        fragmentContainer.setVisibility(View.INVISIBLE);
    }

    public void transactionFragment(Fragment nouvFragment){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, nouvFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onFragmentInteraction(String id) {

    }
}
