package com.resline.cubanacan.ui.fragments.api;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.resline.cubanacan.R;

/**
 * Created by Juan Alejandro on 23/04/2016.
 *
 */
public abstract class RecyclerViewWithFAB extends RecyclerViewFragment {
    FloatingActionButton fabReservar;

    @Override
    protected void loadViewComponents() {
        super.loadViewComponents();
        fabReservar = (FloatingActionButton) mViewRecyclerCardsView.findViewById(R.id.fabReservar);
        fabReservar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // your code here when the fab button is touched
            }
        });
    }

    @Override
    protected View getRecyclerViewCardsRes(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_recycler_view_with_fab, container, false);
    }
}
