package com.example.recouture.StartUpPage;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.ImageHeaderParser;
import com.example.recouture.Add.AddActivity;
import com.example.recouture.HomePage.HomepageActivity;
import com.example.recouture.Outfit.Outfit;
import com.example.recouture.Outfit.SaveOutfit;
import com.example.recouture.R;
import com.example.recouture.utils.FirebaseMethods;
import com.example.recouture.utils.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class SetUpProfile extends AppCompatActivity {

    private EditText username;
    private EditText description;
    private EditText website;
    private ImageView add;
    private ImageView profilepic;
    private TextView upload;

    private String user_id;
    private String email;
    private String displayname;
    private String password;
    private String desc;
    private String site;
    private String url;
    private User user;

    private Uri mImageUri;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private FirebaseUser firebaseUser;
    private String FIREBASE_IMAGE_STORAGE = "photos/users";
    private ActivityIndicator activityIndicator;
    private StorageTask mUploadTask;
    private static final int PICK_IMAGE_REQUEST = 1;
    String TAG = "Setup Profile";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_afterregister);

        setUpWidgets();
        //UniinitImageLoader();


        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference(firebaseUser.getUid());
        mStorageRef = FirebaseStorage.getInstance().getReference()
                .child(FIREBASE_IMAGE_STORAGE + "/" + firebaseUser.getUid() + "/UserData");


        Intent i = getIntent();

        email = i.getStringExtra("email");
        password = i.getStringExtra("password");


        user = new User();

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUserToDatabase();
            }
        });
    }


    private void setUpWidgets() {
        username = (EditText) findViewById(R.id.display_name);
        description = (EditText) findViewById(R.id.description);
        website = (EditText) findViewById(R.id.website);
        add = (ImageView) findViewById(R.id.saveChanges);
        profilepic = (ImageView) findViewById(R.id.profile_photo);
        upload = (TextView) findViewById(R.id.changeProfilePhoto);
    }


    /**
     * Upload file method. Handles logic of uploading all required data to firebase and displaying
     * it in recycler view.
     */
    private void addUserToDatabase() {

        displayname = username.getText().toString().trim();
        desc = description.getText().toString().trim();
        site = website.getText().toString().trim();

        user.setEmail(email);
        user.setPassword(password);
        user.setDisplayname(displayname);
        user.setDescription(displayname);
        user.setWebsite(site);
        user.setUser_id(FirebaseAuth.getInstance().getCurrentUser().getUid());

        final String location = "UserData";
        final DatabaseReference databaseRef = mDatabaseRef.child(location);
        Log.i(TAG, databaseRef.toString());

        databaseRef.setValue(user);

        Toast.makeText(SetUpProfile.this, "Profile saved", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(SetUpProfile.this, HomepageActivity.class);
        startActivity(i);
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
            Log.i(TAG, "message");
            mImageUri = data.getData();
            profilepic.setImageURI(mImageUri);
            //uploadFile();
            final StorageReference fileReference = mStorageRef.child("profilePicture " +
                    "." + FirebaseMethods.getFileExtension(mImageUri, this));
            fileReference.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            user.setImage_path(uri.toString());
                        }
                    });
                }
            });
        }
    }
}
