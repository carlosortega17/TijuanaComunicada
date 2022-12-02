package com.itt.tijuanacomunicada.tabs;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.itt.tijuanacomunicada.R;
import com.itt.tijuanacomunicada.adapters.BacheAdapter;
import com.itt.tijuanacomunicada.models.BachesModel;

public class HoleFragment extends Fragment {

    private FirebaseFirestore firestore;
    private RecyclerView rvHoleList;
    private BacheAdapter bacheAdapter;

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
        firestore = FirebaseFirestore.getInstance();
        rvHoleList = viewRoot.findViewById(R.id.rvHoleList);
        rvHoleList.setLayoutManager(new LinearLayoutManager(viewRoot.getContext()));
        Query query = firestore.collection("holes");
        FirestoreRecyclerOptions<BachesModel> firestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<BachesModel>().setQuery(query, BachesModel.class).build();
        bacheAdapter = new BacheAdapter(firestoreRecyclerOptions);
        bacheAdapter.notifyDataSetChanged();
        rvHoleList.setAdapter(bacheAdapter);
        return viewRoot;
    }

    @Override
    public void onStart() {
        super.onStart();
        bacheAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        bacheAdapter.stopListening();
    }
}