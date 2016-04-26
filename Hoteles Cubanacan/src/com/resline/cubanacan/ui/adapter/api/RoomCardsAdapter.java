package com.resline.cubanacan.ui.adapter.api;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.resline.cubanacan.R;
import com.resline.cubanacan.ui.model.CardViewBean;
import com.resline.cubanacan.ui.model.RoomTypeViewBean;
import com.resline.cubanacan.ui.model.RoomViewBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Parent class for card adapters
 */
public abstract class RoomCardsAdapter extends RecyclerView.Adapter<RoomCardsAdapter.ViewHolder> {
    private static final String TAG = "RoomCards";
    protected final List<RoomViewBean> mListItemsCard;
    protected Activity mActivity;
    private int mDrawerCurrentSelectedPos = 0;
    private boolean isInDetailsView = false;
    private boolean isInUserProfile = false;
    private boolean loadRecommendedCardViews = false;

    public RoomCardsAdapter(Activity activity, List<RoomViewBean> listItemsCard) {
        this.mListItemsCard = listItemsCard;
        this.mActivity = activity;
        loadPreferences();
    }

    protected abstract int getCardLayoutRes();

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final RoomViewBean itemCardView = mListItemsCard.get(position);

        holder.itemView.setTag(itemCardView);
        holder.position = position;
        holder.tvHabitacion.setText(itemCardView.getRoomNumber());
    }

    @Override
    public int getItemCount() {
        return mListItemsCard.size();
    }

    private void loadPreferences() {
        // fixme: onPause error
    }

    public RoomViewBean removeItem(int position) {
        final RoomViewBean roomViewBean = mListItemsCard.remove(position);
        notifyItemRemoved(position);
        return roomViewBean;
    }

    public void addItem(int position, RoomViewBean roomViewBean) {
        mListItemsCard.add(position, roomViewBean);
        notifyItemInserted(position);
    }

    public void animateTo(List<RoomViewBean> roomViewBeans) {
        applyAndAnimateRemovals(roomViewBeans);
        applyAndAnimateAdditions(roomViewBeans);
    }

    private void applyAndAnimateRemovals(List<RoomViewBean> newRoomViewBeans) {
        for (int i = mListItemsCard.size() - 1; i >= 0; i--) {
            final RoomViewBean model = mListItemsCard.get(i);
            if (!newRoomViewBeans.contains(model)) {
                removeItem(i);
            }
        }
    }

    private void applyAndAnimateAdditions(List<RoomViewBean> newRoomViewBeans) {
        for (int i = 0, count = newRoomViewBeans.size(); i < count; i++) {
            final RoomViewBean roomViewBean = newRoomViewBeans.get(i);
            if (!mListItemsCard.contains(roomViewBean)) {
                addItem(i, roomViewBean);
            }
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvHabitacion;
        public LinearLayout llRoomType;
        public int position;
        private Activity mActivity;

        public ViewHolder(Activity activity, final View itemView) {
            super(itemView);
            mActivity = activity;
            tvHabitacion = (TextView) itemView.findViewById(R.id.tvHabitacion);
            //llRoomType = (LinearLayout) itemView.findViewById(R.id.llRoomType);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return false;
                }
            });
        }
    }

    // parameter id is accesible only for this class - todo: change this
    protected abstract Bundle getBundle(Long id);

    protected abstract Class<?> getActivityClass();


}
