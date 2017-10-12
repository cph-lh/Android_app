package com.example.myfirstapp;

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
            PopUpFragment startFragment = PopUpFragment.newInstance();
            getFragmentManager().beginTransaction().add(R.id.fragment_container, startFragment).addToBackStack(null).commit();
            tag = 9;
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
                if (tag != 2) {
                    getFragmentManager().beginTransaction()
                            .setCustomAnimations(R.animator.slide_up, R.animator.slide_down)
                            .replace(R.id.fragment_container, favorite).addToBackStack(null).commit();
                    tag = 2;
                    return true;
                } else if (tag == 2) {
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, favorite)
                            .addToBackStack(null).commit();
                    return true;
                }

            case R.id.menu_example:
                ExampleFragment example = new ExampleFragment();
                if (tag != 3) {
                    getFragmentManager().beginTransaction()
                            .setCustomAnimations(R.animator.slide_up, R.animator.slide_down)
                            .replace(R.id.fragment_container, example).addToBackStack(null).commit();
                    tag = 3;
                    return true;
                } else if (tag == 3) {
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, example)
                            .addToBackStack(null).commit();
                    return true;
                }

            case R.id.menu_message:
                MessageFragment message = new MessageFragment();
                if (tag != 4) {
                    getFragmentManager().beginTransaction()
                            .setCustomAnimations(R.animator.slide_up, R.animator.slide_down)
                            .replace(R.id.fragment_container, message).addToBackStack(null).commit();
                    tag = 4;
                    return true;
                } else if (tag == 4) {
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, message)
                            .addToBackStack(null).commit();
                    return true;
                }

            case R.id.menu_launch_rocket:
                LaunchRocketAnimationFragment rocket = new LaunchRocketAnimationFragment();
                if (tag != 5) {
                    getFragmentManager().beginTransaction()
                            .setCustomAnimations(R.animator.slide_up, R.animator.slide_down)
                            .replace(R.id.fragment_container, rocket).addToBackStack(null).commit();
                    tag = 5;
                    return true;
                } else if (tag == 5) {
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, rocket)
                            .addToBackStack(null).commit();
                    return true;
                }

            case R.id.menu_bounce_doge:
                BounceDogeAnimationFragment doge = BounceDogeAnimationFragment.newInstance();
                if (tag != 1) {
                    getFragmentManager().beginTransaction()
                            .setCustomAnimations(R.animator.slide_up, R.animator.slide_down)
                            .replace(R.id.fragment_container, doge).addToBackStack(null).commit();
                    tag = 1;
                    return true;
                } else if (tag == 1) {
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, doge)
                            .addToBackStack(null).commit();
                    return true;
                }

            case R.id.menu_transitions:
                TransitionFragment transitions = TransitionFragment.newInstance();
                if (tag != 6) {
                    getFragmentManager().beginTransaction()
                            .setCustomAnimations(R.animator.slide_up, R.animator.slide_down)
                            .replace(R.id.fragment_container, transitions).addToBackStack(null).commit();
                    tag = 6;
                    return true;
                } else if (tag == 6) {
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, transitions)
                            .addToBackStack(null).commit();
                    return true;
                }

            case R.id.menu_list:
                ListFragment list = new ListFragment();
                if (tag != 7) {
                    getFragmentManager().beginTransaction()
                            .setCustomAnimations(R.animator.slide_up, R.animator.slide_down)
                            .replace(R.id.fragment_container, list).addToBackStack(null).commit();
                    tag = 7;
                    return true;
                } else if (tag == 7) {
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, list)
                            .addToBackStack(null).commit();
                    return true;
                }

            case R.id.menu_contact_list:
                ContactFragment contactList = ContactFragment.newInstance();
                if (tag != 8) {
                    getFragmentManager().beginTransaction()
                            .setCustomAnimations(R.animator.slide_up, R.animator.slide_down)
                            .replace(R.id.fragment_container, contactList).addToBackStack(null).commit();
                    tag = 8;
                    return true;
                } else if (tag == 8) {
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, contactList)
                            .addToBackStack(null).commit();
                    return true;
                }
            case R.id.menu_pop_up:
                PopUpFragment popUp = PopUpFragment.newInstance();
                if (tag != 9) {
                    getFragmentManager().beginTransaction()
                            .setCustomAnimations(R.animator.slide_up, R.animator.slide_down)
                            .replace(R.id.fragment_container, popUp).addToBackStack(null).commit();
                    tag = 9;
                    return true;
                } else if (tag == 9) {
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, popUp)
                            .addToBackStack(null).commit();
                    return true;
                }

            default:
                // Isf we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }
}
