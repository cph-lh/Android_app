package com.examble.myfirstapp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ExambleInfoFragment extends Fragment {

    private View root;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        root = inflater.inflate(R.layout.examble_info_fragment, container, false);
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
    protected void onStartAnimation() {
        getFragmentManager().beginTransaction()
                .setCustomAnimations(R.animator.card_flip_left_in, R.animator.card_flip_left_out)
                .replace(R.id.fragment_container, new ExambleFragment()).commit();
    }

    //Makes the flip animation look smoother
    private void setCameraDistance() {
        int distance = 8000;
        float scale = getResources().getDisplayMetrics().density * distance;
        root.setCameraDistance(scale);
    }
}
