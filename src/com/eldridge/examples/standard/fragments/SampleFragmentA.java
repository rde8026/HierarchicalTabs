package com.eldridge.examples.standard.fragments;

import android.app.AbstractFragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
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
 * Date: 2/19/12
 * Time: 9:50 AM
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
                    SampleFragmentC fragmentC = new SampleFragmentC();
                    FragmentTransaction transaction = getActivity().getFragmentManager().beginTransaction();
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
                    addFragmentToStack(SampleFragmentA.newInstance(getArguments().getInt("COUNT") + 1));
                }
            });
        }

        return v;
    }

}
