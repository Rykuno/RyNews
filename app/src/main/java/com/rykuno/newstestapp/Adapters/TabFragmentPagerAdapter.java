package com.rykuno.newstestapp.Adapters;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;


import com.rykuno.newstestapp.Activities.Fragments.BusinessFragment;
import com.rykuno.newstestapp.Activities.Fragments.FeaturedFragment;
import com.rykuno.newstestapp.Activities.Fragments.NewsSectionFragment;
import com.rykuno.newstestapp.Activities.Fragments.PoliticsFragment;
import com.rykuno.newstestapp.Activities.Fragments.SportsFragment;
import com.rykuno.newstestapp.Activities.Fragments.TechFragment;
import com.rykuno.newstestapp.Activities.MainActivity;
import com.rykuno.newstestapp.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by rykuno on 8/25/16.
 */


public class TabFragmentPagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> titleList = new ArrayList<>();

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
        super.restoreState(state, loader);
    }

    public TabFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragment(Fragment fragment, String title) {
        fragmentList.add(fragment);
        titleList.add(title);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);

       /* if (position == 0) {
            return NewsSectionFragment.newInstance("order-by=newest");
        } else if (position == 1){
           return NewsSectionFragment.newInstance("section=business");
        }else if (position == 2){
            return NewsSectionFragment.newInstance("section=politics");
        }else if (position == 3){
            return NewsSectionFragment.newInstance("section=sport");
        }else if (position == 4){
            return NewsSectionFragment.newInstance("section=technology");
        }
        return null;
        */
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
    @Override
    public CharSequence getPageTitle(int position) {
        super.getPageTitle(position);
        return titleList.get(position);
    }


}
