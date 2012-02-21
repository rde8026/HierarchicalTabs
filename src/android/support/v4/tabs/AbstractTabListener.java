package android.support.v4.tabs;

import android.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import java.util.Iterator;
import java.util.Stack;
import java.util.UUID;

/**
 * Created by IntelliJ IDEA.
 * User: ryaneldridge
 * Date: 2/18/12
 * Time: 10:16 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractTabListener implements ActionBar.TabListener {

    private boolean isManaged = false;
    private Stack<Fragment> mStack;
    private FragmentActivity mActivity;
    private Fragment mFragment;
    private String mTag;
    int mViewId;
    private UUID stackTag;
    
    public AbstractTabListener(FragmentActivity activity, boolean isManaged, Fragment fragment, int viewId, String tag) {
        this.isManaged = isManaged;
        this.mActivity = activity;
        this.mFragment = fragment;
        this.mTag = tag;
        this.mViewId = viewId;
        ((AbstractFragmentTabActivity)mActivity).registerViewId(viewId);
        if (isManaged) {
            mStack = new Stack<Fragment>();
            stackTag = UUID.randomUUID();
            ((AbstractFragmentTabActivity)mActivity).registerStack(stackTag, mStack);
            if (fragment instanceof AbstractFragment) {
                ((AbstractFragment)fragment).setStackUUID(stackTag);
                ((AbstractFragment)fragment).setManaged(isManaged);
            } else if (fragment instanceof AbstractListFragment) {
                ((AbstractListFragment)fragment).setStackUUID(stackTag);
                ((AbstractListFragment)fragment).setManaged(isManaged);
            }
        }
    }


    @Override
    public void onTabSelected(ActionBar.Tab tab, android.app.FragmentTransaction fragmentTransaction) {
        FragmentManager manager = mActivity.getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment f = manager.findFragmentById(mViewId);
        if (f != null && f.getClass() == this.mFragment.getClass()) {
            transaction.attach(f);
        } else {
            transaction.add(mViewId, mFragment, mTag);
        }
        transaction.commit();

        if (isManaged) {
            Iterator<Fragment> iterator = mStack.iterator();
            while (iterator.hasNext()) {
                FragmentTransaction ft = manager.beginTransaction();
                Fragment fragment = iterator.next();
                if (fragment.isAdded()) {
                    ft.attach(fragment);
                } else {
                    ft.add(mViewId, fragment);
                }
                ft.addToBackStack(null);
                ft.commit();
            }
        }
        mActivity.findViewById(mViewId).setVisibility(View.VISIBLE);
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, android.app.FragmentTransaction fragmentTransaction) {
        FragmentManager manager = mActivity.getSupportFragmentManager();
        if (isManaged) {
            int currentBackStack = manager.getBackStackEntryCount();
            for (int i = 0; i < currentBackStack; i++) {
                Fragment visibleFragment = manager.findFragmentById(mViewId);
                FragmentTransaction transaction = manager.beginTransaction();
                visibleFragment.setRetainInstance(true);
                manager.saveFragmentInstanceState(visibleFragment);
                transaction.detach(visibleFragment);
                transaction.commit();
            }
        }

        FragmentTransaction transaction = manager.beginTransaction();
        transaction.detach(mFragment);
        transaction.commit();

        manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        mActivity.findViewById(mViewId).setVisibility(View.GONE);
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, android.app.FragmentTransaction fragmentTransaction) {
        //un-used
    }

    protected void clearStack() {
        if (this.mStack != null) {
            this.mStack.clear();
        }
    }

}
