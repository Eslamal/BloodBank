package com.eslamali.bloodbank.bloodbank.ui.activity;

import android.os.Bundle;

import com.eslamali.bloodbank.bloodbank.Helper.HelperMethod;
import com.eslamali.bloodbank.bloodbank.ui.fragment.userCycleFragment.LoginFragment;
import com.eslamali.bloodbank.R;

public class UserRecycleActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_recycle);
        HelperMethod.replaceFragment(getSupportFragmentManager(), R.id.activity_user_frame_fragment, new LoginFragment());
    }
}
