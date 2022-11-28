package com.eslamali.bloodbank.bloodbank.ui.fragment.HomeFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eslamali.bloodbank.bloodbank.Adapter.NotificationRecyclerAdapter;
import com.eslamali.bloodbank.bloodbank.Data.Api.RetrofitClient;
import com.eslamali.bloodbank.bloodbank.Data.Local.SharedPrefrance;
import com.eslamali.bloodbank.bloodbank.Helper.HelperMethod;
import com.eslamali.bloodbank.bloodbank.Helper.InternetState;
import com.eslamali.bloodbank.bloodbank.ui.fragment.HomeFragments.HomeFragment.HomeFragment;
import com.eslamali.bloodbank.bloodbank.Data.Model.Notification.Notification;
import com.eslamali.bloodbank.bloodbank.Data.Model.Notification.NotificationData;
import com.eslamali.bloodbank.bloodbank.Data.Model.User.registerAndLoginAndEdit.UserData;
import com.eslamali.bloodbank.bloodbank.Helper.OnEndLess;
import com.eslamali.bloodbank.R;
import com.eslamali.bloodbank.bloodbank.ui.activity.HomeActivity;
import com.eslamali.bloodbank.bloodbank.ui.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.eslamali.bloodbank.bloodbank.Data.Local.SharedPrefrance.loadData;


public class NotificationFragment extends BaseFragment {
    Unbinder unbinder;
    @BindView(R.id.fragment_notification_rv)
    RecyclerView fragmentNotificationRv;
    @BindView(R.id.fragment_notification_tv_no_item)
    TextView fragmentNotificationTvNoItem;
    private LinearLayoutManager liner;
    private OnEndLess onEndLess;
    private NotificationRecyclerAdapter recyclerAdapter;
    private List<NotificationData> notficationData = new ArrayList<>();
    private UserData userData;
    private int max = 0;
    private HomeActivity home;

    public NotificationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        intialFragment();
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        unbinder = ButterKnife.bind(this, view);
        home = (HomeActivity) getActivity();
        home.setHomeAppBarTextViewChange(getString(R.string.notification));
        userData = SharedPrefrance.loadData(getActivity());
        intialRecycle();
        if (notficationData.size() == 0) {
            getNotification(1);
        }
        return view;
    }

    private void intialRecycle() {
        liner = new LinearLayoutManager(getActivity());
        fragmentNotificationRv.setLayoutManager(liner);
        onEndLess = new OnEndLess(liner, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= max) {
                    if (max != 0 && current_page != 1) {
                        onEndLess.previous_page = current_page;
                        getNotification(current_page);

                    } else {
                        onEndLess.current_page = onEndLess.previous_page;
                    }
                } else {
                    onEndLess.current_page = onEndLess.previous_page;
                }

            }
        };
        fragmentNotificationRv.addOnScrollListener(onEndLess);
        recyclerAdapter = new NotificationRecyclerAdapter(getActivity(), getActivity(), notficationData);
        fragmentNotificationRv.setAdapter(recyclerAdapter);
    }

    private void getNotification(int current_page) {
        if (InternetState.isActive(getActivity())) {
            RetrofitClient.getClient().getListNotification(userData.getApiToken(), current_page).enqueue(new Callback<Notification>() {
                @Override
                public void onResponse(Call<Notification> call, Response<Notification> response) {
                    try {


                        if (response.body().getStatus() == 1) {
                            if (current_page == 1) {
                                if (response.body().getData().getTotal() > 0) {
                                    fragmentNotificationTvNoItem.setVisibility(View.GONE);
                                } else {
                                    fragmentNotificationTvNoItem.setVisibility(View.VISIBLE);
                                    fragmentNotificationTvNoItem.setText(getString(R.string.No_Item));
                                }

                            }

                            max = response.body().getData().getLastPage();
                            notficationData.addAll(response.body().getData().getData());
                            recyclerAdapter.notifyDataSetChanged();

                        } else {
                            HelperMethod.customToast(getActivity(),response.body().getMsg(),true);

                        }

                    } catch (Exception e) {
                        HelperMethod.customToast(getActivity(),e.getMessage(),true);
                    }

                }

                @Override
                public void onFailure(Call<Notification> call, Throwable t) {
                    HelperMethod.customToast(getActivity(),getString(R.string.failed),true);
                }
            });

        } else {
            HelperMethod.customToast(getActivity(),getString(R.string.nointernt),true);
        }

    }

    @Override
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    @Override
    public void BackPressed() {
       home.setHomeAppBarUnvisible();
        HelperMethod.replaceFragment(home.getSupportFragmentManager(), R.id.home_frame_fragment, new HomeFragment());
    }
}
