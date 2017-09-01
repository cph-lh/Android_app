package com.examble.myfirstapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        if (savedInstanceState == null) {
            BounceDogeAnimationFragment startFragment = BounceDogeAnimationFragment.newInstance();
            getFragmentManager().beginTransaction().add(R.id.fragment_container, startFragment).addToBackStack(null).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflates the menu this adds itemArray to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    //Defines what happens when a menu item is selected from the action bar menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_favorite:
                FavoriteFragment favorite = new FavoriteFragment();
                getFragmentManager().beginTransaction()
                        .setCustomAnimations(R.animator.slide_up, R.animator.slide_down)
//                        .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                        .replace(R.id.fragment_container, favorite).addToBackStack(null).commit();
                return true;

            case R.id.menu_examble:
                ExambleFragment examble = new ExambleFragment();
                getFragmentManager().beginTransaction()
                        .setCustomAnimations(R.animator.slide_up, R.animator.slide_down)
//                        .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                        .replace(R.id.fragment_container, examble).addToBackStack(null).commit();
                return true;

            case R.id.menu_message:
                MessageFragment message = new MessageFragment();
                getFragmentManager().beginTransaction()
                        .setCustomAnimations(R.animator.slide_up, R.animator.slide_down)
//                        .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                        .replace(R.id.fragment_container, message).addToBackStack(null).commit();
                return true;

            case R.id.menu_launch_rocket:
                LaunchRocketAnimationFragment rocket = new LaunchRocketAnimationFragment();
                getFragmentManager().beginTransaction()
                        .setCustomAnimations(R.animator.slide_up, R.animator.slide_down)
//                        .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                        .replace(R.id.fragment_container, rocket).addToBackStack(null).commit();
                return true;

            case R.id.menu_bounce_doge:
                BounceDogeAnimationFragment doge = new BounceDogeAnimationFragment();
                getFragmentManager().beginTransaction()
                        .setCustomAnimations(R.animator.slide_up, R.animator.slide_down)
//                        .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                        .replace(R.id.fragment_container, doge).addToBackStack(null).commit();
                return true;

            case R.id.menu_list:
                ListFragment list = new ListFragment();
                getFragmentManager().beginTransaction()
                        .setCustomAnimations(R.animator.slide_up, R.animator.slide_down)
//                        .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                        .replace(R.id.fragment_container, list).addToBackStack(null).commit();
                return true;

            case R.id.menu_contact_list:
                ContactListFragment contactList = new ContactListFragment();
                getFragmentManager().beginTransaction()
                        .setCustomAnimations(R.animator.slide_up, R.animator.slide_down)
//                        .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                        .replace(R.id.fragment_container, contactList).addToBackStack(null).commit();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }
}
