package com.example.recouture.HomePage;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.hardware.camera2.CameraDevice;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.recouture.Add.AddActivity;
import com.example.recouture.R;
import android.hardware.Camera;

import org.w3c.dom.Text;

import static android.app.Activity.RESULT_OK;

public class PromptFragment extends Fragment {

    String TAG = "Fragment";

    static final int REQUEST_IMAGE_CAPTURE = 0;
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
        textViewTakePhoto = v.findViewById(R.id.textViewTakePhoto);
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


        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "you clicked on button ", Toast.LENGTH_SHORT).show();
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
