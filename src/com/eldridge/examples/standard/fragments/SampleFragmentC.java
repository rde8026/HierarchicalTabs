package com.eldridge.examples.standard.fragments;

import android.app.AbstractFragment;
import android.app.AbstractListFragment;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.eldridge.support.R;

/**
 * Created by IntelliJ IDEA.
 * User: ryaneldridge
 * Date: 2/19/12
 * Time: 9:50 AM
 * To change this template use File | Settings | File Templates.
 */
public class SampleFragmentC extends AbstractListFragment {

    static final String[] items = new String[]{"ITEM 1", "ITEM 2", "ITEM 3", "ITEM 4", "ITEM 5", "ITEM 6", "ITEM 7", "ITEM 8", "ITEM 9", "ITEM 10"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragmentc, container, false);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, items);
        getListView().setAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Log.d(SampleFragmentC.class.getSimpleName(), "**** List Item Clicked: [" + position + "] ****");
        SimpleFragment simpleFragment = new SimpleFragment();
        Bundle b = new Bundle();
        b.putInt("POSITION", position);
        simpleFragment.setArguments(b);
        addFragmentToStack(simpleFragment);
    }


    private class SimpleFragment extends AbstractFragment {

        private int getPosition() {
            return getArguments().getInt("POSITION");
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            LinearLayout linearLayout = new LinearLayout(getActivity());
            linearLayout.setBackgroundColor(Color.parseColor("#cccccc"));
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            TextView textView = new TextView(getActivity());
            textView.setText(items[getPosition()]);

            linearLayout.addView(textView);

            return linearLayout;
        }


    }

}