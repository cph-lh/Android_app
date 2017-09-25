package com.example.myfirstapp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ExambleFragment extends Fragment {

    private View root;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        root = inflater.inflate(R.layout.examble_fragment, container, false);
        setCameraDistance();

        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onStartAnimation();
            }
        });
        return root;
    }

    //Starts the card flip animation
    private void onStartAnimation() {
        getFragmentManager().beginTransaction()
                .setCustomAnimations(R.animator.card_flip_right_in, R.animator.card_flip_right_out)
                .replace(R.id.fragment_container, new ExambleInfoFragment()).commit();
    }

    //Makes the animation of the flip look smoother
    private void setCameraDistance() {
        int distance = 8000;
        float scale = getResources().getDisplayMetrics().density * distance;
        root.setCameraDistance(scale);
    }
}
