package com.eldridge.examples.support.fragments;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.tabs.AbstractFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.eldridge.support.R;

import java.util.UUID;

/**
 * Created by IntelliJ IDEA.
 * User: ryaneldridge
 * Date: 2/18/12
 * Time: 10:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class SampleFragmentA extends AbstractFragment {

    public static SampleFragmentA newInstance(int count) {
        SampleFragmentA fragment = new SampleFragmentA();
        Bundle bundle = new Bundle();
        bundle.putInt("COUNT", count);
        fragment.setArguments(bundle);
        return fragment;
    }
    
    private int getCount() {
        return getArguments().getInt("COUNT");
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragmenta, container, false);
        TextView txtName = (TextView) v.findViewById(R.id.txtName);
        txtName.setText("SampleA Count: " + String.valueOf(getCount()));

        Button btnAddNew = (Button) v.findViewById(R.id.btnAddNew);

        if (getCount() == 2) {
            btnAddNew.setText("New Fragment C");
            btnAddNew.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //You can add fragments this way if you want control over the transition for example
                    //BUT YOU MUST CALL addToBackStack
                    SampleFragmentC fragmentC = new SampleFragmentC();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.add(R.id.tab_a_content_id, fragmentC, UUID.randomUUID().toString());
                    transaction.addToBackStack(null);
                    transaction.commit();
                    addToBackStack(fragmentC);
                }
            });
        } else {
            btnAddNew.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //If you use this method we handle pretty much everything for you.  We could consider accepting a
                    //parameter for animation - it would be pretty easy
                    addFragmentToStack(SampleFragmentA.newInstance(getArguments().getInt("COUNT") + 1));
                }
            });
        }
        
        return v;
    }
}
