package com.example.rayons;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.rayons.API.APIRequest;
import com.example.rayons.API.RetroServer;
import com.example.rayons.Adapter.AdapterData;
import com.example.rayons.Model.DataModel;
import com.example.rayons.Model.ResponseModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Result;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Ship extends Fragment {
    RecyclerView rvData;
    RecyclerView.Adapter adData;
    RecyclerView.LayoutManager lmData;
    List<DataModel> Data = new ArrayList<>();
    SwipeRefreshLayout SWL;
    ProgressBar PbBar;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public Ship() {
        // Required empty public constructor
    }

    public static Ship newInstance(String param1, String param2) {
        Ship fragment = new Ship();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ship, container, false);

        SWL = view.findViewById(R.id.swl_data);
        PbBar = view.findViewById(R.id.pb_data);
        rvData = view.findViewById(R.id.parent_data_kapal);

        lmData = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        rvData.setLayoutManager(lmData);
        setData();

        SWL.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                SWL.setRefreshing(true);
                setData();
                SWL.setRefreshing(false);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setData();
    }

    public void setData(){
        PbBar.setVisibility(View.VISIBLE);

        APIRequest API = RetroServer.KonekRetrofit().create(APIRequest.class);
        Call<ResponseModel> TampilData = API.getKapal();

        TampilData.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                int Kode = response.body().getKode();
                String Pesan = response.body().getPesan();

                Data = response.body().getData();
                adData = new AdapterData(getActivity(), Data);
                rvData.setAdapter(adData);
                adData.notifyDataSetChanged();

                PbBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getActivity(),"Gagal Menghubungi Serve" + t.getMessage(), Toast.LENGTH_SHORT).show();
                PbBar.setVisibility(View.INVISIBLE);
            }
        });
    }

}