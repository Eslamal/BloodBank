package com.eslamali.bloodbank.bloodbank.ui.fragment.splashCycleFragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eslamali.bloodbank.bloodbank.Data.Local.SharedPrefrance;
import com.eslamali.bloodbank.R;
import com.eslamali.bloodbank.bloodbank.ui.activity.HomeActivity;
import com.eslamali.bloodbank.bloodbank.ui.fragment.BaseFragment;

import static com.eslamali.bloodbank.bloodbank.Data.Local.SharedPrefrance.loadData;
import static com.eslamali.bloodbank.bloodbank.Helper.HelperMethod.replaceFragment;


public class SplashFragment extends BaseFragment {


    private static final long SPLASH_DISPLAY_LENGTH = 3000;

    public SplashFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        intialFragment();
        View view = inflater.inflate(R.layout.fragment_splash, container, false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                if (SharedPrefrance.loadaData(getActivity(), SharedPrefrance.REMEMBER) && SharedPrefrance.loadData(getActivity()) != null) {
                    Intent intent = new Intent(getActivity(), HomeActivity.class);
                    getActivity().startActivity(intent);
                    getActivity().finish();
                } else {
                    replaceFragment(getActivity().getSupportFragmentManager(), R.id.activity_splash_frame_layout_id, new SliderFragment());
                }
            }
        }, SPLASH_DISPLAY_LENGTH);
        return view;
    }


    @Override
    public void BackPressed() {
        getActivity().finish();
    }
}
