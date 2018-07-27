package com.example.recouture.Friends;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.recouture.Posts.ViewPost;
import com.example.recouture.R;
import com.example.recouture.utils.BaseActivity;
import com.example.recouture.utils.BottomNavigationViewHelper;
import com.example.recouture.utils.FirebaseMethods;
import com.example.recouture.utils.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;
import java.util.List;

public class AddFriend extends BaseActivity {



    // database ref points to root() since we will query each user uid.
    DatabaseReference databaseReference;

    EditText searchFriendText;

    RecyclerView searchRecyclerview;

    List<User> searchUser;



    // firebase query to query for usernames
    //Query query;

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()) {
                for (DataSnapshot childSnapShot : dataSnapshot.getChildren()) {
                    User user = childSnapShot.getValue(User.class);
                    searchUser.add(user);
                }

            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };





    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(setView());
        super.onCreate(savedInstanceState);

        setUpWidgets();


    }

    @Override
    public int setView() {
        return R.layout.activity_searchfriends;
    }


    public void setUpWidgets() {
        searchRecyclerview = findViewById(R.id.searchRecyclerView);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        searchFriendText = findViewById(R.id.searchFriendText);
        searchUser = new ArrayList<>();
    }



    public void setUpTextWatcher(EditText searchFriendText) {
        searchFriendText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Query query = databaseReference.orderByChild("UserData/displayname")
                        .startAt(charSequence.toString())
                        .endAt(charSequence.toString() + "\uf8ff");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }




}
