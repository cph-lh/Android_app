package com.examble.myfirstapp;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class MessageFragment extends Fragment {

    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    private View root;
    private Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        //Inflate the layout for this fragment
        root = inflater.inflate(R.layout.message_fragmemt, container, false);
        button = (Button) root.findViewById(R.id.sendButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage(v);
            }
        });

        return root;
    }

    //Called when the Send-button is tapped and send the string to a new activity
    private void sendMessage(View view) {
        Intent intent = new Intent(getActivity(), DisplayMessageActivity.class);
        EditText editText = (EditText) root.findViewById(R.id.editText);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }
}
