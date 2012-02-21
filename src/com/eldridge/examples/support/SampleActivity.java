package com.eldridge.examples.support;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.tabs.AbstractTabListener;
import android.support.v4.tabs.AbstractFragmentTabActivity;
import android.util.Log;
import com.eldridge.examples.support.fragments.SampleFragmentA;
import com.eldridge.examples.support.fragments.SampleFragmentB;
import com.eldridge.examples.support.fragments.SampleFragmentC;
import com.eldridge.support.R;

/**
 * Created by IntelliJ IDEA.
 * User: ryaneldridge
 * Date: 2/18/12
 * Time: 10:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class SampleActivity extends AbstractFragmentTabActivity {
    
    private static final String TAG = SampleActivity.class.getSimpleName();


    private ActionBar mActionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //set up action bar
        mActionBar = getActionBar();
        mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        //set up some tabs
        SampleFragmentA fragmentA = SampleFragmentA.newInstance(0);
        mActionBar.addTab(mActionBar.newTab().setText("Tab A").setTabListener(new TabAListener(this, true, fragmentA, R.id.tab_a_content_id, "TAB_A")));
        SampleFragmentB fragmentB = new SampleFragmentB();
        mActionBar.addTab(mActionBar.newTab().setText("Tab B").setTabListener(new TabBListener(this, true, fragmentB, R.id.tab_b_content_id, "TAB_B")));
        Fragment sampleC = new SampleFragmentC();

        //NOTE: This tab is un-managed so it will always reset when clicked on to the root view [SampleC]
        mActionBar.addTab(mActionBar.newTab().setText("Tab C").setTabListener(new TabCListener(this, false, sampleC, R.id.tab_c_content_id, "TAB_C")));

    }

    private class TabAListener extends AbstractTabListener {

        public TabAListener(FragmentActivity activity, boolean isManaged, Fragment fragment, int viewId, String tag) {
            super(activity, isManaged, fragment, viewId, tag);
        }

        @Override
        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
            super.onTabSelected(tab, fragmentTransaction);
            Log.d(TAG, "**** TAB A Selected ****");
        }

        @Override
        public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
            super.onTabUnselected(tab, fragmentTransaction);
            Log.d(TAG, "**** TAB A UnSelected ****");
        }

        @Override
        public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
            super.onTabReselected(tab, fragmentTransaction);
            Log.d(TAG, "**** TAB A Reselected ****");
        }
    }

    private class TabBListener extends AbstractTabListener {
        public TabBListener(FragmentActivity activity, boolean isManaged, Fragment fragment, int viewId, String tag) {
            super(activity, isManaged, fragment, viewId, tag);
        }

        @Override
        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
            super.onTabSelected(tab, fragmentTransaction);
            Log.d(TAG, "**** TAB B Selected ****");
        }

        @Override
        public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
            super.onTabUnselected(tab, fragmentTransaction);
            Log.d(TAG, "**** TAB B UnSelected ****");
        }

        @Override
        public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
            super.onTabReselected(tab, fragmentTransaction);
            Log.d(TAG, "**** TAB B Reselected ****");
        }
    }

    private class TabCListener extends AbstractTabListener {
        public TabCListener(FragmentActivity activity, boolean isManaged, Fragment fragment, int viewId, String tag) {
            super(activity, isManaged, fragment, viewId, tag);    //To change body of overridden methods use File | Settings | File Templates.
        }

        @Override
        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
            super.onTabSelected(tab, fragmentTransaction);    //To change body of overridden methods use File | Settings | File Templates.
        }

        @Override
        public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
            super.onTabUnselected(tab, fragmentTransaction);    //To change body of overridden methods use File | Settings | File Templates.
        }

        @Override
        public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
            super.onTabReselected(tab, fragmentTransaction);    //To change body of overridden methods use File | Settings | File Templates.
        }
    }

}
