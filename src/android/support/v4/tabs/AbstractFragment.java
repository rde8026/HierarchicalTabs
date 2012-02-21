package android.support.v4.tabs;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import java.util.ArrayList;
import java.util.Stack;
import java.util.UUID;

/**
 * Created by IntelliJ IDEA.
 * User: ryaneldridge
 * Date: 2/18/12
 * Time: 11:05 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractFragment extends Fragment {

    private UUID stackUUID;
    private boolean isManaged;

    /**
     * Adds the provided fragment to the tab back stack
     * Should be called after transaction.add is committed.
     * @param fragment
     */
    public void addToBackStack(Fragment fragment) {
        if (fragment instanceof AbstractFragment) {
            ((AbstractFragment)fragment).setStackUUID(stackUUID);
            ((AbstractFragment)fragment).setManaged(isManaged);
        } else if (fragment instanceof AbstractListFragment) {
            ((AbstractListFragment)fragment).setStackUUID(stackUUID);
            ((AbstractListFragment)fragment).setManaged(isManaged);
        } else {
            throw new ClassCastException("Unsupported Fragment type.");
        }
        ((AbstractFragmentTabActivity)getActivity()).stacks.get(stackUUID).push(fragment);
    }

    /**
     * Method that handles adding a fragment to the visible tab content.
     * This also manages adding the fragment to the appropriate Tab Stack.
     * <b>If you choose <u>NOT</u> to use this method you <u>MUST</u> call addToBackStack after you call transaction.add() </b>
     * @param fragment
     */
    public void addFragmentToStack(Fragment fragment) {
        //make sure we pass the proper stack reference to the new fragment.
        if (fragment instanceof AbstractFragment) {
            ((AbstractFragment)fragment).setStackUUID(stackUUID);
            ((AbstractFragment)fragment).setManaged(isManaged);
        } else if (fragment instanceof AbstractListFragment) {
            ((AbstractListFragment)fragment).setStackUUID(stackUUID);
            ((AbstractListFragment)fragment).setManaged(isManaged);
        } else {
            throw new ClassCastException("Unsupported Fragment type.");
        }

        ArrayList<Integer> views = ((AbstractFragmentTabActivity) getActivity()).viewIds;
        int visibleView = -1;
        for (int viewId : views) {
            if (getActivity().findViewById(viewId).getVisibility() == View.VISIBLE) {
                visibleView = viewId;
                break;
            }
        }

        if (visibleView == -1 ) {
            throw new RuntimeException("No Visible View Found!");
        } else {
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.add(visibleView, fragment);
            transaction.addToBackStack(null);

            if ( ((AbstractFragmentTabActivity)getActivity()).isStateSaved() ) {
                transaction.commitAllowingStateLoss();
            } else {
                transaction.commit();
            }
            if (isManaged()) {
                ((AbstractFragmentTabActivity)getActivity()).stacks.get(stackUUID).push(fragment);
            }
        }

    }

    protected UUID getStackUUID() {
        return stackUUID;
    }

    protected void setStackUUID(UUID stackUUID) {
        this.stackUUID = stackUUID;
    }

    public boolean isManaged() {
        return isManaged;
    }

    public void setManaged(boolean managed) {
        isManaged = managed;
    }
}
