package com.example.recouture.HomePage;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.recouture.Add.AddActivity;
import com.example.recouture.Outfit.CreateOutfit;
import com.example.recouture.R;

import static android.app.Activity.RESULT_OK;

public class PromptFragment extends Fragment {

    String TAG = "Fragment";

    static final int REQUEST_IMAGE_CAPTURE = 2;
    private static final int PICK_IMAGE_REQUEST = 1;
    TextView textViewTakePhoto;
    TextView textViewAlbum;
    TextView textViewAddOutfit;
    Button buttonCancel;

    private Uri mImageUri;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_promptforadd, container, false);
        textViewTakePhoto = v.findViewById(R.id.feed);
        textViewAlbum = v.findViewById(R.id.textViewAlbum);
        textViewAddOutfit = v.findViewById(R.id.textViewAddOutfit);
        buttonCancel = v.findViewById(R.id.buttonCancel);

        textViewTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCamera();
            }
        });

        textViewAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });

        textViewAddOutfit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: navigating to create outfit");
                Intent createOutfit = new Intent(getActivity(), CreateOutfit.class);
                createOutfit.putExtra("going to create","something");
                startActivity(createOutfit);
            }
        });


        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: navigating back to homepage");
                getFragmentManager().beginTransaction().remove(PromptFragment.this).commit();
            }
        });
        return v;
    }


//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.textViewAlbum:
//                openFileChooser();
//                break;
//            case R.id.textViewTakePhoto:
//                openCamera();
//        }
//    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            Log.i(TAG,"message");
            mImageUri = data.getData();
            Intent intent = new Intent(getActivity(),AddActivity.class);
            intent.putExtra("imagePath",mImageUri.toString());
            Log.i(TAG,mImageUri.toString());
            intent.putExtra("requestCode",PICK_IMAGE_REQUEST);
            startActivity(intent);
        }
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap thumbnail = (Bitmap) extras.get("data");
            Intent intent = new Intent(getActivity(), AddActivity.class);
            intent.putExtra("image", thumbnail);
            intent.putExtra("requestCode",REQUEST_IMAGE_CAPTURE);
            startActivity(intent);

        }
    }


}
