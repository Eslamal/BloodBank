package com.eslamali.bloodbank.bloodbank.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eslamali.bloodbank.bloodbank.Helper.HelperMethod;
import com.eslamali.bloodbank.bloodbank.ui.fragment.HomeFragments.EditProfilFragment;
import com.eslamali.bloodbank.bloodbank.ui.fragment.HomeFragments.HomeFragment.HomeFragment;
import com.eslamali.bloodbank.bloodbank.ui.fragment.HomeFragments.MoreFragment.MoreFragment;
import com.eslamali.bloodbank.bloodbank.ui.fragment.HomeFragments.NotificationFragment;
import com.eslamali.bloodbank.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends BaseActivity {

    @BindView(R.id.home_app_bar_text_view_change)
    TextView homeAppBarTextViewChange;
    @BindView(R.id.home_toolbar)
    RelativeLayout homeToolbar;
    @BindView(R.id.home_lnly_menu)
    LinearLayout homeLnlyMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        setHomeAppBarUnvisible();
         HelperMethod.replaceFragment(getSupportFragmentManager(), R.id.home_frame_fragment, new HomeFragment());
    }

    @OnClick({R.id.home_img_menu, R.id.home_img_notfiction, R.id.home_img_profil, R.id.home_img_home})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.home_img_menu:
                  HelperMethod.replaceFragment(getSupportFragmentManager(), R.id.home_frame_fragment, new MoreFragment());
                break;
            case R.id.home_img_notfiction:
                    HelperMethod.replaceFragment(getSupportFragmentManager(), R.id.home_frame_fragment, new NotificationFragment());
                break;
            case R.id.home_img_profil:
                  HelperMethod.replaceFragment(getSupportFragmentManager(), R.id.home_frame_fragment, new EditProfilFragment());
                break;
            case R.id.home_img_home:
                setHomeAppBarUnvisible();
                     HelperMethod.replaceFragment(getSupportFragmentManager(), R.id.home_frame_fragment, new HomeFragment());
                break;
        }
    }

    public void setHomeAppBarTextViewChange(String Title) {
        homeToolbar.setVisibility(View.VISIBLE);
        homeAppBarTextViewChange.setText(Title);
    }

    public void setHomeAppBarUnvisible() {
        homeToolbar.setVisibility(View.GONE);
    }

    public void setLinervisiable() {
        homeLnlyMenu.setVisibility(View.VISIBLE);
    }

    public void setLinerUnvisible() {
        homeLnlyMenu.setVisibility(View.GONE);
    }

    @OnClick(R.id.home_img_back)
    public void onViewClicked() {
        onBackPressed();
    }
}
