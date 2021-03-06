package com.resline.cubanacan.ui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.resline.cubanacan.R;
import com.resline.cubanacan.ui.activities.DestinoListActivity;
import com.resline.cubanacan.ui.adapter.api.RecyclerViewCardsAdapter;
import com.resline.cubanacan.ui.model.CardViewBean;

import java.util.List;

/**
 * Created by Juan Alejandro on 22/04/2016.
 */
public class DestinosCardAdapter extends RecyclerViewCardsAdapter {
    public DestinosCardAdapter(Activity activity, List<CardViewBean> listItemsCard) {
        super(activity, listItemsCard);
    }

    @Override
    protected int getCardLayoutRes() {
        return R.layout.card_general;
    }

    @Override
    protected int getDefaultImage() {
        return R.drawable.icon;
    }

    @Override
    protected Bundle getBundle(Long id) {
        return new Bundle();
    }

    @Override
    protected Class<?> getActivityClass() {
        return DestinoListActivity.class;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DestinoViewHolder(mActivity, LayoutInflater.from(parent.getContext())
                .inflate(getCardLayoutRes(), parent, false));
    }

    private class DestinoViewHolder extends ViewHolder {
        public DestinoViewHolder(Activity activity, View itemView) {
            super(activity, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mActivity.startActivity(new Intent(mActivity, getActivityClass()).putExtras(getBundle(id)));
                }
            });
        }
    }
}
