package com.itt.tijuanacomunicada.tabs;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itt.tijuanacomunicada.R;

public class HoleFragment extends Fragment {

    private RecyclerView rvHoleList;

    public HoleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewRoot = inflater.inflate(R.layout.fragment_hole, container, false);
        rvHoleList = viewRoot.findViewById(R.id.rvHoleList);
        return viewRoot;
    }
}