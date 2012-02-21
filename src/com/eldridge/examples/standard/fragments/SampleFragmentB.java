package com.eldridge.examples.standard.fragments;

import android.app.AbstractFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.eldridge.support.R;

/**
 * Created by IntelliJ IDEA.
 * User: ryaneldridge
 * Date: 2/19/12
 * Time: 9:50 AM
 * To change this template use File | Settings | File Templates.
 */
public class SampleFragmentB extends AbstractFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragmentb, container, false);
        Button btnAddNew = (Button) v.findViewById(R.id.btnAddNew);
        btnAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFragmentToStack(SampleFragmentA.newInstance(0));
            }
        });
        return v;
    }

}
