package android.app;


import android.view.View;

import java.util.Iterator;
import java.util.Stack;
import java.util.UUID;

/**
 * Created by IntelliJ IDEA.
 * User: ryaneldridge
 * Date: 2/19/12
 * Time: 9:18 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractTabListener implements ActionBar.TabListener {

    private boolean isManaged = false;
    private Stack<Fragment> mStack;
    private Activity mActivity;
    private Fragment mFragment;
    private String mTag;
    int mViewId;
    private UUID stackTag;

    public AbstractTabListener(Activity activity, boolean isManaged, Fragment fragment, int viewId, String tag) {
        this.isManaged = isManaged;
        this.mActivity = activity;
        this.mFragment = fragment;
        this.mTag = tag;
        this.mViewId = viewId;
        ((AbstractTabActivity)mActivity).registerViewId(viewId);
        if (isManaged) {
            mStack = new Stack<Fragment>();
            stackTag = UUID.randomUUID();
            ((AbstractTabActivity)mActivity).registerStack(stackTag, mStack);
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
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction unused) {
        FragmentTransaction transaction = mActivity.getFragmentManager().beginTransaction();
        Fragment f = mActivity.getFragmentManager().findFragmentById(mViewId);
        if (f != null && f.getClass() == this.mFragment.getClass()) {
            transaction.attach(f);
        } else {
            transaction.add(mViewId, mFragment, mTag);
        }
        transaction.commit();

        if (isManaged) {
            Iterator<Fragment> iterator = mStack.iterator();
            while (iterator.hasNext()) {
                FragmentTransaction ft = mActivity.getFragmentManager().beginTransaction();
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
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction unused) {
        FragmentManager manager = mActivity.getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        if (isManaged) {
            int currentBackStack = manager.getBackStackEntryCount();
            for (int i = 0; i < currentBackStack; i++) {
                Fragment visibleFragment = manager.findFragmentById(mViewId);
                FragmentTransaction ft = manager.beginTransaction();
                visibleFragment.setRetainInstance(true);
                manager.saveFragmentInstanceState(visibleFragment);
                ft.detach(visibleFragment);
                ft.commit();
            }
        }


        transaction.detach(mFragment);
        transaction.commit();

        manager.popBackStack(null, android.support.v4.app.FragmentManager.POP_BACK_STACK_INCLUSIVE);
        mActivity.findViewById(mViewId).setVisibility(View.GONE);
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    protected void clearStack() {
        if (this.mStack != null) {
            this.mStack.clear();
        }
    }

}
