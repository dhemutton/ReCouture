package com.example.recouture.HomePage;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recouture.R;

import org.w3c.dom.Text;

public class PromptFragment extends Fragment {

    TextView textViewTakePhoto;
    TextView textViewAlbum;
    TextView textViewAddOutfit;
    Button buttonCancel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_promptforadd,container,false);
        textViewTakePhoto = v.findViewById(R.id.textViewTakePhoto);
        //textViewAlbum = v.findViewById(R.id.textViewAlbum);
        //textViewAddOutfit = v.findViewById(R.id.textViewAddOutfit);
        buttonCancel = v.findViewById(R.id.buttonCancel);

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"you clicked on button ",Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }


}
