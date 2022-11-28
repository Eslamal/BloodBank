package com.eslamali.bloodbank.bloodbank.ui.activity;

import android.os.Bundle;

import com.eslamali.bloodbank.bloodbank.Helper.HelperMethod;
import com.eslamali.bloodbank.bloodbank.ui.fragment.splashCycleFragment.SplashFragment;
import com.eslamali.bloodbank.R;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HelperMethod.replaceFragment(getSupportFragmentManager(), R.id.activity_splash_frame_layout_id, new SplashFragment());

    }
}
