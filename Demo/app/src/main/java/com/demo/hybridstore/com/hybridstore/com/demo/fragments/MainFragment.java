package com.demo.hybridstore.com.hybridstore.com.demo.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hybridstore.app.R;
import com.demo.hybridstore.com.hybridstore.adapters.CardAdapter;
import com.demo.hybridstore.com.hybridstore.model.Item;

import java.util.ArrayList;

public class MainFragment extends Fragment {

    private RecyclerView mRecycleView;
    private CardAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private View mViewCard;
    private String target;

    public MainFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        getActivity().setTitle("Hybrid Store");

        ArrayList<Item> card = new ArrayList<>();
        card.add(new Item("Samsung", "https://www.t-mobile.com/content/dam/t-mobile/en-p/cell-phones/samsung/samsung-j3-prime/samsung-galaxy-j3-prime-black-1-3x.jpg", "HEHE"));
        card.add(new Item("Pool", "https://i5.walmartimages.com/asr/aea961ed-876f-4262-88b4-515bde3221c5_1.948fb279b2701f750ad70d95e2f1fee9.jpeg?odnHeight=450&odnWidth=450&odnBg=FFFFFF", "A swimming pool, swimming bath, wading pool, or paddling pool is a structure designed to hold water to enable swimming or other leisure activities"));
        
        mViewCard = rootView.findViewById(R.id.viewCard);
        mRecycleView = rootView.findViewById(R.id.recycleviewer);

        mRecycleView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(rootView.getContext());
        mAdapter = new CardAdapter(card);
        mRecycleView.setAdapter(mAdapter);
        mRecycleView.setLayoutManager(mLayoutManager);

        mAdapter.setOnItemClickListener(new CardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
            }
        });
        return rootView;
    }
}