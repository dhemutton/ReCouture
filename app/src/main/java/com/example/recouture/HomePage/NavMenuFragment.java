package com.example.recouture.HomePage;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.recouture.Outfit.ViewOutfits;
import com.example.recouture.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.recouture.StartUpPage.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.net.Inet4Address;

/**
 * Created by User on 6/4/2017.
 */

public class NavMenuFragment extends Fragment {

    private static final String TAG = "NavMenuFragment";
    private boolean isOpen = true;
    private FragmentManager manager;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homepagenav, container, false);
        Button closeNav = (Button) view.findViewById(R.id.navmenuicon);
/*
        if (isOpen == true) {
            closeNav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onClick: navigating back to homepage");
                    getActivity().getFragmentManager().beginTransaction().remove(NavMenuFragment.this).commit();

                }
            });

        }
*/


        //View Outfits
        Button viewoutfits = (Button) view.findViewById(R.id.button3);
        manager = getFragmentManager();
        viewoutfits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: navigating back to 'outfit'");
                Intent viewOutfit = new Intent(getActivity(), ViewOutfits.class);
                viewOutfit.putExtra("going to create","something");
                startActivity(viewOutfit);
            }
        });


        //SIGN OUT
        Button signOut = (Button) view.findViewById(R.id.button4);
        manager = getFragmentManager();
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: navigating back to 'signout fragment'");
                addFragmentViewSignout();
            }
        });
        return view;
    }


    private void addFragmentViewSignout() {
        SignOutFragment fragment = new SignOutFragment();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.add(R.id.signoutfragment,fragment);
        fragmentTransaction.commit();
    }
}

