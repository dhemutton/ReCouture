package com.example.recouture.HomePage;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.recouture.TagHolder;
import com.example.recouture.utils.UniversalImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.example.recouture.R;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.recouture.utils.BottomNavigationViewHelper;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;


public class HomepageActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";
    private static final int ACTIVITY_NUM = 0;


    private RecyclerView recyclerView;
    private ImageAdapter imageAdapter;
    private FragmentManager manager;
    private EditText searchText;
    private RecyclerView searchRecyclerView;
    private SearchAdapter searchAdapter;
    private List<TagHolder> tagHolders;
    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference mTagDatabaseRef = FirebaseDatabase.getInstance().getReference(firebaseUser.getUid() + "/Tags");
    private  Context mContext = HomepageActivity.this;
    private TextWatcher filterTextWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // DO THE CALCULATIONS HERE AND SHOW THE RESULT AS PER YOUR CALCULATIONS
            Query query = mTagDatabaseRef.orderByKey().startAt(s.toString()).endAt(s.toString()
                    + "\uf8ff");
            query.addValueEventListener(searchEventListener);
            if (!TextUtils.isEmpty(s)) {
                recyclerView.setVisibility(View.INVISIBLE);
                searchRecyclerView.setVisibility(View.VISIBLE);
            } else {
                recyclerView.setVisibility(View.VISIBLE);
                searchRecyclerView.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_2);
        Log.d(TAG, "onCreate: starting.");

        setupBottomNavigationView();
        initImageLoader();

        TextView signOut = (TextView) findViewById(R.id.logout);
        manager = getFragmentManager();
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: navigating back to 'signout fragment'");
                addFragmentView();
            }
        });

        searchText = findViewById(R.id.searchText);

        // add text watcher to make main recycler view invisible when saerching tags
        searchText.addTextChangedListener(filterTextWatcher);

        searchRecyclerView = findViewById(R.id.searchRecyclerView);

        tagHolders = new ArrayList<>();

        searchRecyclerView.setVisibility(View.INVISIBLE);

        setUpSearch();


        recyclerView = findViewById(R.id.recyclerViewHomePage);
        imageAdapter = new ImageAdapter(this);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.HORIZONTAL));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(HomepageActivity.this,3));
        recyclerView.setAdapter(imageAdapter);

    }


    public void setUpSearch() {
        searchRecyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.HORIZONTAL));
        searchRecyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));

        searchAdapter = new SearchAdapter(getApplicationContext(),tagHolders);
        searchRecyclerView.setHasFixedSize(true);
        searchRecyclerView.setLayoutManager(new GridLayoutManager(HomepageActivity.this,3));
        searchRecyclerView.setAdapter(searchAdapter);

    }

    ValueEventListener searchEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            tagHolders.clear();
            if (dataSnapshot.exists()) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    TagHolder tags = dataSnapshot1.getValue(TagHolder.class);
                    tagHolders.add(tags);
                }
                searchAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    private void initImageLoader() {
        UniversalImageLoader universalImageLoader = new UniversalImageLoader(mContext);
        ImageLoader.getInstance().init(universalImageLoader.getConfig());
    }

    private void setupBottomNavigationView(){
        Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(HomepageActivity.this, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }

    private void addFragmentView() {
        SignOutFragment fragment = new SignOutFragment();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.add(R.id.signoutfragment,fragment);
        fragmentTransaction.commit();
    }

}
