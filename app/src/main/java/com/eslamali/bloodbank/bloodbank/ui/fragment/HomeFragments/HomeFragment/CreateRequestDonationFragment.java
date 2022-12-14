package com.eslamali.bloodbank.bloodbank.ui.fragment.HomeFragments.HomeFragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;


import com.eslamali.bloodbank.bloodbank.Adapter.SpinnerAdpter;
import com.eslamali.bloodbank.bloodbank.Data.Api.RetrofitClient;
import com.eslamali.bloodbank.bloodbank.Data.Local.SharedPrefrance;
import com.eslamali.bloodbank.bloodbank.Helper.HelperMethod;
import com.eslamali.bloodbank.bloodbank.Helper.InternetState;
import com.eslamali.bloodbank.bloodbank.Data.Model.User.registerAndLoginAndEdit.UserData;
import com.eslamali.bloodbank.bloodbank.Data.Model.donationDetails.DonationDetailsAndRequest;
import com.eslamali.bloodbank.R;
import com.eslamali.bloodbank.bloodbank.ui.activity.MapsActivity;
import com.eslamali.bloodbank.bloodbank.ui.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.eslamali.bloodbank.bloodbank.Data.Local.SharedPrefrance.loadData;
import static com.eslamali.bloodbank.bloodbank.Helper.Constant.hospital_address;
import static com.eslamali.bloodbank.bloodbank.Helper.Constant.latitude;
import static com.eslamali.bloodbank.bloodbank.Helper.Constant.longitude;
import static com.eslamali.bloodbank.bloodbank.Helper.GeneralReqestesMethodes.getDataSpinners;


public class CreateRequestDonationFragment extends BaseFragment {
    Unbinder unbinder;
    @BindView(R.id.donation_fragment_et_name)
    EditText donationFragmentEtName;
    @BindView(R.id.donation_fragment_et_age)
    EditText donationFragmentEtAge;
    @BindView(R.id.donation_fragment_sp_type_blood)
    Spinner donationFragmentSpTypeBlood;
    @BindView(R.id.donation_fragment_et_number_complaisant)
    EditText donationFragmentEtNumberComplaisant;
    @BindView(R.id.donation_fragment_et_adress_hospital)
    TextView donationFragmentEtAdressHospital;
    @BindView(R.id.donation_fragment_sp_muhafza)
    Spinner donationFragmentSpMuhafza;
    @BindView(R.id.donation_fragment_sp_city)
    Spinner donationFragmentSpCity;
    @BindView(R.id.donation_fragment_et_phone)
    EditText donationFragmentEtPhone;
    @BindView(R.id.donation_fragment_et_note)
    EditText donationFragmentEtNote;
    @BindView(R.id.donation_fragment_ll_city)
    LinearLayout donationFragmentLlCity;
    @BindView(R.id.donation_fragment_et_hospital_name)
    EditText donationFragmentEtHospitalName;
    private UserData userData;


    public CreateRequestDonationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        intialFragment();
        View view = inflater.inflate(R.layout.fragment_donation_create_request, container, false);
        unbinder = ButterKnife.bind(this, view);
        userData = SharedPrefrance.loadData(getActivity());
        SpinnerAdpter adpter = new SpinnerAdpter(getActivity());
        getDataSpinners(RetrofitClient.getClient().getBloodTypes(), adpter, getString(R.string.bloodtype), donationFragmentSpTypeBlood);
        SpinnerAdpter spinnerAdpter = new SpinnerAdpter(getActivity());
        AdapterView.OnItemSelectedListener listener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    donationFragmentLlCity.setVisibility(View.GONE);
                    donationFragmentSpCity.setVisibility(View.GONE);
                } else {
                    donationFragmentLlCity.setVisibility(View.VISIBLE);
                    donationFragmentSpCity.setVisibility(View.VISIBLE);
                    SpinnerAdpter adpters = new SpinnerAdpter(getActivity());
                    getDataSpinners(RetrofitClient.getClient().getCities(position), adpters, getString(R.string.city), donationFragmentSpCity);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
        getDataSpinners(RetrofitClient.getClient().getGovernorate(), spinnerAdpter, getString(R.string.governorat), donationFragmentSpMuhafza, listener);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void BackPressed() {
        super.BackPressed();
    }

    @OnClick({R.id.donation_fragment_im_location, R.id.donation_fragment_bt_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.donation_fragment_im_location:
                Intent intent = new Intent(getActivity(), MapsActivity.class);
                getActivity().startActivity(intent);
                break;
            case R.id.donation_fragment_bt_send:
                sendRequest();
                break;
        }
    }

    private void sendRequest() {

        String patient_name = donationFragmentEtName.getText().toString().trim();
        String patient_age = donationFragmentEtAge.getText().toString().trim();
        int blood_type_id = donationFragmentSpTypeBlood.getSelectedItemPosition();
        String bags_num = donationFragmentEtNumberComplaisant.getText().toString().trim();
        String hospital_name = donationFragmentEtHospitalName.getText().toString().trim();
        String hospitalAddress = donationFragmentEtAdressHospital.getText().toString().trim();
        int governorate = donationFragmentSpMuhafza.getSelectedItemPosition();
        int city_id = donationFragmentSpCity.getSelectedItemPosition();
        String phone = donationFragmentEtPhone.getText().toString().trim();
        String notes = donationFragmentEtNote.getText().toString().trim();
        if (patient_name.isEmpty()) {
            donationFragmentEtName.requestFocus();
            HelperMethod.customToast(getActivity(), getString(R.string.empty), true);
            return;
        }
        if (patient_age.isEmpty()) {
            donationFragmentEtAge.requestFocus();
            HelperMethod.customToast(getActivity(), getString(R.string.empty), true);
            return;
        }
        if (blood_type_id == 0) {
            HelperMethod.customToast(getActivity(), getString(R.string.selectbloodtype), true);
            return;
        }
        if (bags_num.isEmpty()) {
            donationFragmentEtNumberComplaisant.requestFocus();
            HelperMethod.customToast(getActivity(), getString(R.string.empty), true);
            return;
        }
        if (hospital_name.isEmpty()) {
            donationFragmentEtHospitalName.requestFocus();
            HelperMethod.customToast(getActivity(), getString(R.string.empty), true);
            return;
        }
        if (hospitalAddress.isEmpty()) {
            donationFragmentEtNote.requestFocus();
            HelperMethod.customToast(getActivity(), getString(R.string.empty), true);
            return;
        }
        if (governorate == 0) {
            HelperMethod.customToast(getActivity(), getString(R.string.selectgover), true);
            return;
        }
        if (city_id == 0) {
            HelperMethod.customToast(getActivity(), getString(R.string.selectcity), true);
            return;
        }
        if (phone.isEmpty()) {
            donationFragmentEtPhone.requestFocus();
            HelperMethod.customToast(getActivity(), getString(R.string.empty), true);
            return;
        }
        if (notes.isEmpty()) {
            donationFragmentEtNote.requestFocus();
            HelperMethod.customToast(getActivity(), getString(R.string.empty), true);
            return;
        }
        if (InternetState.isActive(getActivity())) {
            RetrofitClient.getClient().getDonationRequestCreate(userData.getApiToken(), patient_name, patient_age, blood_type_id, bags_num,
                    hospital_name, hospitalAddress, city_id, phone, notes, latitude, longitude).enqueue(new Callback<DonationDetailsAndRequest>() {
                @Override
                public void onResponse(Call<DonationDetailsAndRequest> call, Response<DonationDetailsAndRequest> response) {
                    try {
                        if (response.body().getStatus() == 1) {
                            latitude = 0;
                            longitude = 0;
                            hospital_address = null;
                            HelperMethod.customToast(getActivity(), response.body().getMsg(), false);
                            BackPressed();

                        } else {
                            HelperMethod.customToast(getActivity(), response.body().getMsg(), true);
                        }

                    } catch (Exception e) {
                        HelperMethod.customToast(getActivity(), e.getMessage(), true);
                    }
                }

                @Override
                public void onFailure(Call<DonationDetailsAndRequest> call, Throwable t) {
                    HelperMethod.customToast(getActivity(), getString(R.string.failed), true);
                }
            });
        } else {
            HelperMethod.customToast(getActivity(), getString(R.string.nointernt), true);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if (hospital_address != null) {
            donationFragmentEtAdressHospital.setText(hospital_address);
        }
    }
}
