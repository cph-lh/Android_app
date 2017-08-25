package com.examble.myfirstapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        if (savedInstanceState == null) {
            LaunchRocketAnimationFragment startFragment = new LaunchRocketAnimationFragment();
            getFragmentManager().beginTransaction().add(R.id.fragment_container, startFragment)
                    .addToBackStack(null).commit();
        }
    }

    //Called when the Send-button is tapped in message fragment
    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu this adds itemArray to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_favorite:
                FavoriteFragment favorite = new FavoriteFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, favorite)
                        .addToBackStack(null).commit();
                return true;

            case R.id.menu_examble:
                ExambleFragment examble = new ExambleFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, examble)
                        .addToBackStack(null).commit();
                return true;

            case R.id.menu_message:
                MessageFragment message = new MessageFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, message)
                        .addToBackStack(null).commit();
                return true;

            case R.id.menu_rocketlaunch:
                LaunchRocketAnimationFragment rocket = new LaunchRocketAnimationFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, rocket)
                        .addToBackStack(null).commit();
                return true;

            case R.id.menu_list:
                ListFragment list = new ListFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, list)
                        .addToBackStack(null).commit();
                return true;

            case R.id.menu_contact_list:
                ContactListFragment contactList = new ContactListFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, contactList)
                        .addToBackStack(null).commit();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }
}
