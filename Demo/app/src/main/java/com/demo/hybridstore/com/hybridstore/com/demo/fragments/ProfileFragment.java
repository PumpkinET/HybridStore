package com.demo.hybridstore.com.hybridstore.com.demo.fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hybridstore.app.R;
import com.demo.hybridstore.com.hybridstore.model.Auth;

public class ProfileFragment extends Fragment {

    View rootView;

    private ProfileFragment.SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    public ProfileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_profile_history, container, false);
        getActivity().setTitle(Auth.username);

        mSectionsPagerAdapter = new ProfileFragment.SectionsPagerAdapter(getActivity().getSupportFragmentManager());

        mViewPager = (ViewPager) rootView.findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));


        return rootView;
    }

    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
//            return ProfileFragment.PlaceholderFragment.newInstance(position + 1);
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = new ProfileFragmentTab();
                    break;
                case 1:
                    fragment = new HistoryFragmentTab();
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}