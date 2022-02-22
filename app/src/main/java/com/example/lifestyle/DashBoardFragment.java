package com.example.lifestyle;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class DashBoardFragment extends Fragment {

    private Button hike_button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dash_board, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


/*        hike_button = (Button) getView().findViewById(R.id.submit_button);
        hike_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri hikeSearch = Uri.parse("geo:40.753977,-111.88172?q=Hikes");

                //Create implicit intent
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, hikeSearch);

                // if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                startActivity(mapIntent);
                //    Toast.makeText(getActivity(), "Hike button working!", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(getActivity(), "Its null!", Toast.LENGTH_SHORT).show();
//                }
            }
        });*/
    }
}