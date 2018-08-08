package com.example.recouture.Friends;

import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
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

    /**
     * Add friend activity is responsible for finding potential friends of the user. UI includes
     * using a adapter and recycler view to display users included with a search feature.
     */

    private static final String TAG = "AddFriend";


    /*
    database reference to query each User's uid.
     */
    DatabaseReference databaseReference;


    /*
    EditText to type in friend's display name
     */
    EditText searchFriendText;

    /*
    recycler view to find user's on firebase.
     */
    RecyclerView searchRecyclerview;

    /*
    List of users to populate recycler view.
     */
    List<User> searchUser;

    SearchFriendsAdapter adapter;

    /*
    Event listener to listen for changes in edit text string length and notify adapter of the
    changes.
     */
    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            adapter.clear();
            Log.i(TAG,dataSnapshot.toString());
            if (dataSnapshot.exists()) {
                for (DataSnapshot childSnapShot : dataSnapshot.getChildren()) {
                    if (!checkSameUser(childSnapShot)) {
                        Log.i(TAG, childSnapShot.toString());
                        User user = childSnapShot.child("UserData").getValue(User.class);
                        Log.i(TAG, user.toString());
                        searchUser.add(user);
                    }
                }
                adapter.notifyDataSetChanged();
            }
            Log.i(TAG,"size " + searchUser.size());
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    private boolean checkSameUser(DataSnapshot dataSnapshot) {
        String userId = FirebaseMethods.getUserUid();
        if (dataSnapshot.getKey().equals(userId)) {
            return true;
        }
        return false;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(setView());
        super.onCreate(savedInstanceState);
        Log.i(TAG,"onCreate");

        setUpWidgets();

        adapter = new SearchFriendsAdapter(this,searchUser);
        searchRecyclerview.setAdapter(adapter);
        searchRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        setUpTextWatcher(searchFriendText);
    }

    @Override
    public int setView() {
        return R.layout.activity_searchfriends;
    }


    public void setUpWidgets() {
        searchRecyclerview = findViewById(R.id.friendRecyclerView);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        searchFriendText = findViewById(R.id.searchFriendText);
        searchUser = new ArrayList<>();
    }


    /**
     * TextWatcher that listens for changes in edit text that will update the recycler view.
     * @param searchFriendText the edit text widget to apply the query onto.
     */
    public void setUpTextWatcher(EditText searchFriendText) {
        searchFriendText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.i(TAG,"beforeTextChanged");
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Log.i(TAG,"onTextCHanged");
                Log.i(TAG,charSequence.toString());
                if (charSequence.length() == 0) {
                    searchRecyclerview.setVisibility(View.INVISIBLE);
                } else {
                    searchRecyclerview.setVisibility(View.VISIBLE);
                }
                Query query = databaseReference.orderByChild("UserData/displayname")
                        .startAt(charSequence.toString())
                        .endAt(charSequence.toString() + "\uf8ff");
                query.addValueEventListener(valueEventListener);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.i(TAG,"afterTextChanged");
            }
        });
    }
}
