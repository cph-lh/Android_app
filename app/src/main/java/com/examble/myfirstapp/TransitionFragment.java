package com.examble.myfirstapp;

import android.app.Fragment;
import android.os.Bundle;
import android.transition.ArcMotion;
import android.transition.ChangeBounds;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class TransitionFragment extends Fragment {

    private ViewGroup root;
    private Button button;
    private boolean visible;
    private TextView text;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        root = (ViewGroup) inflater.inflate(R.layout.transition_fragment, container, false);
        text = (TextView) root.findViewById(R.id.text);
        button = (Button) root.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransitionManager.beginDelayedTransition(root);
                visible = !visible;
                text.setVisibility(visible ? View.VISIBLE : View.GONE);
            }
        });
        return root;
    }
}
