package android.app;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import java.util.UUID;

/**
 * Created by IntelliJ IDEA.
 * User: ryaneldridge
 * Date: 2/19/12
 * Time: 9:20 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractTabActivity extends Activity {


    protected HashMap<UUID, Stack<Fragment>> stacks = new HashMap<UUID, Stack<Fragment>>();
    protected ArrayList<Integer> viewIds = new ArrayList<Integer>();

    private boolean isStateSaved = false;

    public void registerStack(UUID tag, Stack<Fragment> stack) {
        stacks.put(tag, stack);
    }

    public void registerViewId(int viewId) {
        viewIds.add(viewId);
    }

    @Override
    public void onBackPressed() {
        int visibleView = -1;
        for (int id : viewIds) {
            if (findViewById(id).getVisibility() == View.VISIBLE) {
                visibleView = id;
                break;
            }
        }
        Fragment poppedFragment = getFragmentManager().findFragmentById(visibleView);
        Stack<Fragment> stack = null;
        if (poppedFragment instanceof AbstractFragment) {
            stack = stacks.get( ((AbstractFragment)poppedFragment).getStackUUID() );
        } else if (poppedFragment instanceof AbstractListFragment) {
            stack = stacks.get( ((AbstractListFragment)poppedFragment).getStackUUID() );
        } else {
            throw new ClassCastException("Unsupported Fragment type.");
        }

        if (stack != null && !stack.empty()) {
            stack.pop();
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stacks.clear();
    }

    protected Fragment getVisibleFragment() {
        int visibleView = -1;
        for (int id : viewIds) {
            if (findViewById(id).getVisibility() == View.VISIBLE) {
                visibleView = id;
                break;
            }
        }
        if (visibleView != -1) {
            return getFragmentManager().findFragmentById(visibleView);
        } else {
            return null;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        isStateSaved = true;
    }

    public boolean isStateSaved() {
        return isStateSaved;
    }

    public void setStateSaved(boolean stateSaved) {
        isStateSaved = stateSaved;
    }
    
}
