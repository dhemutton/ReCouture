package com.example.recouture.HomePage;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.recouture.AccessoriesGallery.Accessories;
import com.example.recouture.AccessoriesGallery.AccessoriesActivity;
import com.example.recouture.BagsGallery.BagActivity;
import com.example.recouture.DressGallery.DressActivity;
import com.example.recouture.Item;
import com.example.recouture.OuterwearGallery.OuterwearActivity;
import com.example.recouture.PantsGallery.PantsActivity;
import com.example.recouture.ShirtGallery.ShirtActivity;
import com.example.recouture.ShoesGallery.ShoesActivity;
import com.example.recouture.ShortsGallery.Shorts;
import com.example.recouture.ShortsGallery.ShortsActivity;
import com.example.recouture.SkirtsGallery.SkirtActivity;
import com.example.recouture.SleevelessGallery.Sleeveless;
import com.example.recouture.SleevelessGallery.SleevelessActivity;
import com.example.recouture.SweaterGallery.SweaterActivity;
import com.example.recouture.SwimwearGallery.Swimwear;
import com.example.recouture.SwimwearGallery.SwimwearActivity;
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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.recouture.utils.BottomNavigationViewHelper;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;
import java.util.List;


public class HomepageActivity extends AppCompatActivity implements HomepageOnClick {
    private static final String TAG = "HomeActivity";
    private static final int ACTIVITY_NUM = 0;
    private DrawerLayout myDrawer;
    private ActionBarDrawerToggle myToggle;

    private final int REQUEST_CODE = 1;

    private BottomNavigationViewEx bottomNavigationViewEx;

    private List<Item> itemOutfits;

    private TextView doneOutfits;


    private boolean chooseOutfit;

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

        RelativeLayout relativeLayout = findViewById(R.id.outfitDone);
        relativeLayout.setVisibility(View.INVISIBLE);

        doneOutfits = findViewById(R.id.doneOutfit);

        doneOutfits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        setupBottomNavigationView();
        initImageLoader();

        itemOutfits = new ArrayList<>();

        if (getIntent().hasExtra("chooseOutfits")) {
            chooseOutfit = getIntent().getExtras().getBoolean("chooseOutfits"); // should return true
            assert chooseOutfit;
            relativeLayout.setVisibility(View.VISIBLE);
            bottomNavigationViewEx.setVisibility(View.INVISIBLE);
        }


        ImageView navmenu = (ImageView) findViewById(R.id.navmenuicon);
        manager = getFragmentManager();
        navmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: navigating to 'menu dropdown'");
                    addFragmentViewNav();

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

        imageAdapter.setListener(this);

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

                    for (DataSnapshot childSnapshot : dataSnapshot1.getChildren()) {
                        TagHolder tags = childSnapshot.getValue(TagHolder.class);
                        Log.i(TAG,"tags " + tags);
                        tagHolders.add(tags);
                    }
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
        bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(HomepageActivity.this, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }

    private void addFragmentViewSignout() {
        SignOutFragment fragment = new SignOutFragment();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.add(R.id.signoutfragment,fragment);
        fragmentTransaction.commit();
    }

    private void addFragmentViewNav() {
        NavMenuFragment fragment = new NavMenuFragment();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.add(R.id.navbarfragment,fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onItemClick(ImageAdapter.ImageViewHolder itemView) {
        Log.i(TAG, "item clicked");
        String categoryName = itemView.categoryName.getText().toString();
        Intent intent = new Intent();
        switch (categoryName) {
            case "Shirts":
                if (chooseOutfit) {
                    intent.setClass(this, ShirtActivity.class);
                    intent.putExtra("chooseOutfits", true);
                    startActivityForResult(intent, 1);
                } else {
                    startActivity(intent.setClass(this, ShirtActivity.class));
                }
                break;
            case "Sleeveless":
                if (chooseOutfit) {
                    intent.setClass(this, SleevelessActivity.class);
                    intent.putExtra("chooseOutfits", true);
                    startActivityForResult(intent, 1);
                } else {
                    startActivity(intent.setClass(this, SleevelessActivity.class));
                }
                break;
            case "Outerwear":
                if (chooseOutfit) {
                    intent.setClass(this, OuterwearActivity.class);
                    intent.putExtra("chooseOutfits", true);
                    startActivityForResult(intent, 1);
                } else {
                    startActivity(intent.setClass(this, OuterwearActivity.class));
                }
                break;
            case "Sweater":
                if (chooseOutfit) {
                    intent.setClass(this, SweaterActivity.class);
                    intent.putExtra("chooseOutfits", true);
                    startActivityForResult(intent, 1);
                } else {
                    startActivity(intent.setClass(this, SweaterActivity.class));
                }
                break;
            case "Pants":
                if (chooseOutfit) {
                    intent.setClass(this, PantsActivity.class);
                    intent.putExtra("chooseOutfits", true);
                    startActivityForResult(intent, 1);
                } else {
                    startActivity(intent.setClass(this, PantsActivity.class));
                }
                break;
            case "Shorts":
                if (chooseOutfit) {
                    intent.setClass(this, ShortsActivity.class);
                    intent.putExtra("chooseOutfits", true);
                    startActivityForResult(intent, 1);
                } else {
                    startActivity(intent.setClass(this, ShortsActivity.class));
                }
                break;
            case "Skirts":
                if (chooseOutfit) {
                    intent.setClass(this, SkirtActivity.class);
                    intent.putExtra("chooseOutfits", true);
                    startActivityForResult(intent, 1);
                } else {
                    startActivity(intent.setClass(this, SkirtActivity.class));
                }
                break;
            case "Dresses":
                if (chooseOutfit) {
                    intent.setClass(this, DressActivity.class);
                    intent.putExtra("chooseOutfits", true);
                    startActivityForResult(intent, 1);
                } else {
                    startActivity(intent.setClass(this, DressActivity.class));
                }
                break;
            case "Shoes":
                if (chooseOutfit) {
                    intent.setClass(this, ShoesActivity.class);
                    intent.putExtra("chooseOutfits", true);
                    startActivityForResult(intent, 1);
                } else {
                    startActivity(intent.setClass(this, ShoesActivity.class));
                }
                break;
            case "Bags":
                if (chooseOutfit) {
                    intent.setClass(this, BagActivity.class);
                    intent.putExtra("chooseOutfits", true);
                    startActivityForResult(intent, 1);
                } else {
                    startActivity(intent.setClass(this, BagActivity.class));
                }
                break;
            case "Accessories":
                if (chooseOutfit) {
                    intent.setClass(this, AccessoriesActivity.class);
                    intent.putExtra("chooseOutfits", true);
                    startActivityForResult(intent, 1);
                } else {
                    startActivity(intent.setClass(this, AccessoriesActivity.class));
                }
                break;
            case "Swimwear":
                if (chooseOutfit) {
                    intent.setClass(this, SwimwearActivity.class);
                    intent.putExtra("chooseOutfits", true);
                    startActivityForResult(intent, 1);
                } else {
                    startActivity(intent.setClass(this, SwimwearActivity.class));
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            ArrayList<Item> arrayList = data.getParcelableArrayListExtra("outfits");
            itemOutfits.addAll(arrayList);
            Log.i(TAG,"items " + itemOutfits);
        }
    }

    @Override
    public void finish() {
        Intent data = new Intent();
        data.putParcelableArrayListExtra("outfits", (ArrayList)itemOutfits);
        setResult(RESULT_OK, data);
        super.finish();
    }
}
