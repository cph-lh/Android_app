package com.examble.myfirstapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    private int tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        if (savedInstanceState == null) {
            ContactFragment startFragment = ContactFragment.newInstance();
            getFragmentManager().beginTransaction().add(R.id.fragment_container, startFragment).addToBackStack(null).commit();
            tag = 8;
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
                if (tag != 2) {
                    FavoriteFragment favorite = new FavoriteFragment();
                    getFragmentManager().beginTransaction()
                        .setCustomAnimations(R.animator.slide_up, R.animator.slide_down)
                            .replace(R.id.fragment_container, favorite).addToBackStack(null).commit();
                    tag = 2;
                    return true;
                } else return false;

            case R.id.menu_examble:
                if (tag != 3) {
                    ExambleFragment examble = new ExambleFragment();
                    getFragmentManager().beginTransaction()
                            .setCustomAnimations(R.animator.slide_up, R.animator.slide_down)
                            .replace(R.id.fragment_container, examble).addToBackStack(null).commit();
                    tag = 3;
                    return true;
                } else return false;

            case R.id.menu_message:
                if (tag != 4) {
                    MessageFragment message = new MessageFragment();
                    getFragmentManager().beginTransaction()
                            .setCustomAnimations(R.animator.slide_up, R.animator.slide_down)
                            .replace(R.id.fragment_container, message).addToBackStack(null).commit();
                    tag = 4;
                    return true;
                } else return false;

            case R.id.menu_launch_rocket:
                if (tag != 5) {
                    LaunchRocketAnimationFragment rocket = new LaunchRocketAnimationFragment();
                    getFragmentManager().beginTransaction()
                            .setCustomAnimations(R.animator.slide_up, R.animator.slide_down)
                            .replace(R.id.fragment_container, rocket).addToBackStack(null).commit();
                    tag = 5;
                    return true;
                } else return false;

            case R.id.menu_bounce_doge:
                if (tag != 1) {
                    BounceDogeAnimationFragment doge = new BounceDogeAnimationFragment().newInstance();
                    getFragmentManager().beginTransaction()
                            .setCustomAnimations(R.animator.slide_up, R.animator.slide_down)
                            .replace(R.id.fragment_container, doge).addToBackStack(null).commit();
                    tag = 1;
                    return true;
                } else return false;

            case R.id.menu_transitions:
                if (tag != 6) {
                    TransitionFragment transitions = new TransitionFragment();
                    getFragmentManager().beginTransaction()
                            .setCustomAnimations(R.animator.slide_up, R.animator.slide_down)
                            .replace(R.id.fragment_container, transitions).addToBackStack(null).commit();
                    tag = 6;
                    return true;
                } else return false;

            case R.id.menu_list:
                if (tag != 7) {
                    ListFragment list = new ListFragment();
                    getFragmentManager().beginTransaction()
                            .setCustomAnimations(R.animator.slide_up, R.animator.slide_down)
                            .replace(R.id.fragment_container, list).addToBackStack(null).commit();
                    tag = 7;
                    return true;
                } else return false;

            case R.id.menu_contact_list:
                if (tag != 8) {
                    ContactFragment contactList = new ContactFragment();
                    getFragmentManager().beginTransaction()
                            .setCustomAnimations(R.animator.slide_up, R.animator.slide_down)
                            .replace(R.id.fragment_container, contactList).addToBackStack(null).commit();
                    tag = 8;
                    return true;
                } else return false;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }
}
