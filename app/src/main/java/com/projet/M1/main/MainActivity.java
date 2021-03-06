package com.projet.M1.main;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.content.res.TypedArray;

import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import servernode.example.com.projetm1.Communicator;

import com.projet.M1.ContactFragment;
import com.projet.M1.HomeFragment;
import com.projet.M1.LogginFragment;
import com.projet.M1.Module.Module;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.projet.M1.action.Action;
import com.projet.M1.adapter.NavDrawerListAdapter;

import com.projet.M1.evenement.Capteur;
import com.projet.M1.model.NavDrawerItem;

import org.json.JSONObject;

import servernode.example.com.projetm1.CreationModule;


public class MainActivity extends ActionBarActivity
        implements Communicator{

  //  Button buttonLogin,buttonAction;

    /*  OnClickListener buttonActionListener =
                new OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        SendAction send = new SendAction("event1","action1");
                        try {
                            send.start();
                        } catch(Exception exception){
                            exception.printStackTrace();
                        }
                    }
            };

        OnClickListener buttonLoginListener =
                new OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        JsonLoggin json = new JsonLoggin("aze","azerty123456");
                        json.runJson();
                        User user = json.getUser();
                        Log.e("data json loggin "," "+user.getIds());
                    }
                };
    */
    private Capteur capteur;
    private Action action;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private List<Module> listeModule = new ArrayList<Module>();

    public static FragmentManager fragmentManager;

    // nav drawer title
    private CharSequence mDrawerTitle;

    // used to store app title
    private CharSequence mTitle;

    // slide menu items
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;

    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;
    boolean val = false;
    public static final String PREFS_NAME = "MyPrefsFile";
    public static final String MODULE_STORAGE = "modeStore";

    public void setCapteur(Capteur capteur) {
        this.capteur = capteur;
    }
    @Override
    public Action getAction() {
        return action;
    }

    @Override
    public void setAction(Action action) {
        this.action = action;
    }

    public boolean checkLoggin(){
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        Set<String> users = settings.getStringSet("userLoggin", null);
        if(users == null){
            return false;
        }
        return true;
    }
    public void loggout(){
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.remove("userLoggin");
        editor.commit();
    }

    public void storeModule(){
        SharedPreferences settings;
        Editor editor;
        settings = getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        editor = settings.edit();
        JSONObject json = new JSONObject();
        try {
            json.put("listeModule", listeModule);
            editor.putString(MODULE_STORAGE, json.toString());
            editor.commit();
            Log.e("module ",json.toString());
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void loadModule(){
        SharedPreferences settings;
        settings = getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        JSONObject json = new JSONObject();
        if (settings.contains(MODULE_STORAGE)) {

            String jsonString = settings.getString(MODULE_STORAGE, null);
            try {
                json = json.getJSONObject(jsonString);
                listeModule = (List<Module>)json.get("listeModule");
                Log.e("module ",listeModule.get(0).getAction().getNom());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.fragmentManager = getFragmentManager();
        setContentView(R.layout.activity_main);
        val = checkLoggin();
        if(val == true) {
            mTitle = mDrawerTitle = getTitle();

            // load slide menu items
            navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

            // nav drawer icons from resources
            navMenuIcons = getResources()
                    .obtainTypedArray(R.array.nav_drawer_icons);

            mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

            navDrawerItems = new ArrayList<NavDrawerItem>();

            // adding nav drawer items to array
            // Home
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
            // Find People
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
            // Photos
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
            // Communities, Will add a counter here
            navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], -1));


            // Recycle the typed array
            navMenuIcons.recycle();

            mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

            // setting the nav drawer list adapter
            adapter = new NavDrawerListAdapter(getApplicationContext(),
                    navDrawerItems);
            mDrawerList.setAdapter(adapter);

            // enabling action bar app icon and behaving it as toggle button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);

            mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                    R.drawable.ic_drawer, //nav menu toggle icon
                    R.string.app_name // nav drawer open - description for accessibility
            ) {
                public void onDrawerClosed(View view) {
                    getSupportActionBar().setTitle(mTitle);
                    // calling onPrepareOptionsMenu() to show action bar icons
                    invalidateOptionsMenu();
                }

                public void onDrawerOpened(View drawerView) {
                    getSupportActionBar().setTitle(mDrawerTitle);
                    // calling onPrepareOptionsMenu() to hide action bar icons
                    invalidateOptionsMenu();
                }
            };
            mDrawerLayout.setDrawerListener(mDrawerToggle);
            if(listeModule == null){
                loadModule();
            }
            if (savedInstanceState == null) {
                // on first time display view for first nav item
                displayView(0);
            }
        } else {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, new LogginFragment()).commit();

        }
    }

    @Override
    public Capteur getCapteur() {
        return capteur;
    }

    /**
     * Slide menu item click listener
     * */
    private class SlideMenuClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // display view for selected nav drawer item
            displayView(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // toggle nav drawer on selecting action bar app icon/title
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action bar actions click
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /* *
     * Called when invalidateOptionsMenu() is triggered
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // if nav drawer is opened, hide the action items
     if(val == true) {
         boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
         menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
         return super.onPrepareOptionsMenu(menu);
     }
        return super.onPrepareOptionsMenu(menu);
    }

    /**
     * Diplaying fragment view for selected nav drawer list item
     * */
    private void displayView(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new HomeFragment();
                break;
            case 1:
                fragment = new ContactFragment();
                break;
            case 2:
                fragment = new CreationModule();
                break;
            case 3:
                new AlertDialog.Builder(this)
                        .setTitle("Déconnexion")
                        .setMessage("Etes vous sûr de vouloir vous déconnecter?")
                        .setPositiveButton("Confirmer", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                loggout();
                                finish();
                                startActivity(getIntent());
                            }
                        })
                        .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .show();
                break;

            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment).commit();

            // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(navMenuTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
        } else {
                // error in creating fragment
                Log.e("MainActivity", "Error in creating fragment");
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        if(val == true) {
            mDrawerToggle.syncState();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        if(val == true) {
            mDrawerToggle.onConfigurationChanged(newConfig);
        }
    }


    public void transaction(Fragment fragment){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void addModule(Module module){
        loadModule();
        if(listeModule == null){
            listeModule = new ArrayList<Module>();
        }
        listeModule.add(module);
        storeModule();
    }
}
