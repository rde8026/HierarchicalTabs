package com.eldridge.examples.standard;

import android.app.*;
import android.os.Bundle;
import android.util.Log;
import com.eldridge.examples.standard.fragments.SampleFragmentA;
import com.eldridge.examples.standard.fragments.SampleFragmentB;
import com.eldridge.examples.standard.fragments.SampleFragmentC;
import com.eldridge.support.R;

/**
 * Created by IntelliJ IDEA.
 * User: ryaneldridge
 * Date: 2/19/12
 * Time: 9:48 AM
 * To change this template use File | Settings | File Templates.
 */
public class SampleStandardActivity extends AbstractTabActivity {

    static final String TAG = SampleStandardActivity.class.getSimpleName();

    private ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mActionBar = getActionBar();
        mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        SampleFragmentA sampleFragmentA = SampleFragmentA.newInstance(0);
        mActionBar.addTab(mActionBar.newTab().setText("Tab A").setTabListener(new TabAListener(this, true, sampleFragmentA, R.id.tab_a_content_id, "TAB_A")));
        SampleFragmentB sampleFragmentB = new SampleFragmentB();
        mActionBar.addTab(mActionBar.newTab().setText("Tab B").setTabListener(new TabBListener(this, true, sampleFragmentB, R.id.tab_b_content_id, "TAB_B")));
        SampleFragmentC sampleFragmentC = new SampleFragmentC();

        //NOTE: This tab is un-managed so it will always reset when clicked on to the root view [SampleC]
        mActionBar.addTab(mActionBar.newTab().setText("Tab C").setTabListener(new TabCListener(this, false, sampleFragmentC, R.id.tab_c_content_id, "TAB_C")));
    }


    private class TabAListener extends AbstractTabListener {

        public TabAListener(Activity activity, boolean isManaged, Fragment fragment, int viewId, String tag) {
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
        public TabBListener(Activity activity, boolean isManaged, Fragment fragment, int viewId, String tag) {
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
        public TabCListener(Activity activity, boolean isManaged, Fragment fragment, int viewId, String tag) {
            super(activity, isManaged, fragment, viewId, tag);    //To change body of overridden methods use File | Settings | File Templates.
        }

        @Override
        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction unused) {
            super.onTabSelected(tab, unused);    //To change body of overridden methods use File | Settings | File Templates.
        }

        @Override
        public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction unused) {
            super.onTabUnselected(tab, unused);    //To change body of overridden methods use File | Settings | File Templates.
        }

        @Override
        public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
            super.onTabReselected(tab, fragmentTransaction);    //To change body of overridden methods use File | Settings | File Templates.
        }
    }

}
