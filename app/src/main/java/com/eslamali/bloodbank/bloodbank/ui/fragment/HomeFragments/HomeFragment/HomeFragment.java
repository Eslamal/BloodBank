package com.eslamali.bloodbank.bloodbank.ui.fragment.HomeFragments.HomeFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.ViewPager;

import com.eslamali.bloodbank.bloodbank.Adapter.FragmentPagerViewAdapter;
import com.eslamali.bloodbank.bloodbank.Helper.HelperMethod;
import com.google.android.material.tabs.TabLayout;
import com.eslamali.bloodbank.R;
import com.eslamali.bloodbank.bloodbank.ui.activity.HomeActivity;
import com.eslamali.bloodbank.bloodbank.ui.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class HomeFragment extends BaseFragment {
    @BindView(R.id.home_tab_layout_id)
    TabLayout homeTabLayoutId;
    @BindView(R.id.home_view_pager)
    ViewPager homeViewPager;

    Unbinder unbinder;
    private HomeActivity home;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        intialFragment();
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        home = (HomeActivity) getActivity();
        home.setHomeAppBarUnvisible();
        FragmentPagerViewAdapter adapter = new FragmentPagerViewAdapter(getChildFragmentManager(), 1,getActivity());
        homeViewPager.setAdapter(adapter);
        homeTabLayoutId.setupWithViewPager(homeViewPager);
        return view;
    }

    @Override
    public void BackPressed() {
        getActivity().finish();
    }

    @OnClick(R.id.home_float_bt_add_reqest)
    public void onViewClicked() {
        home.setHomeAppBarTextViewChange("donation  request");
        HelperMethod.replaceFragment(getActivity().getSupportFragmentManager(), R.id.home_frame_fragment, new CreateRequestDonationFragment());
    }
}
