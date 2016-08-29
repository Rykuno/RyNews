package com.rykuno.newstestapp.Activities;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import com.rykuno.newstestapp.Activities.Fragments.NewsSectionFragment;
import com.rykuno.newstestapp.Adapters.TabFragmentPagerAdapter;
import com.rykuno.newstestapp.R;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabFragmentPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Add the fragments which will be utilized via the viewpager

        adapter = new TabFragmentPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(NewsSectionFragment.newInstance("order-by=newest"), "Featured");
        adapter.addFragment(NewsSectionFragment.newInstance("section=business"), "Business");
        adapter.addFragment(NewsSectionFragment.newInstance("section=politics"), "Politics");
        adapter.addFragment(NewsSectionFragment.newInstance("section=sport"), "Sports");
        adapter.addFragment(NewsSectionFragment.newInstance("section=technology"), "Tech");

        // Find the view pager that will allow the user to swipe between fragments
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        viewPager.setOffscreenPageLimit(5);
        // Create an adapter that knows which fragment should be shown on each page
        viewPager.setAdapter(adapter);

        // Set the adapter onto the view pager
        tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}




