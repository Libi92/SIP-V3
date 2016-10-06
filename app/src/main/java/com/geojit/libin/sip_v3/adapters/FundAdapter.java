package com.geojit.libin.sip_v3.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.geojit.libin.sip_v3.R;
import com.geojit.libin.sip_v3.components.RupeesTextView;
import com.geojit.libin.sip_v3.components.SIPTextView;
import com.geojit.libin.sip_v3.modals.FundDataModal;

import java.util.ArrayList;

/**
 * Created by 10945 on 8/6/2016.
 */
public class FundAdapter extends RecyclerView.Adapter<FundAdapter.FundViewHolder> {

    private ArrayList<FundDataModal> data;
    private Context mContext;
    private int lastPosition = 0;
    private boolean isInner;

    public FundAdapter(ArrayList<FundDataModal> data, boolean isInner) {
        this.data = data;
        this.isInner = isInner;
    }

    @Override
    public FundViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);

        View view;
        if (viewType == 1) {
            view = inflater.inflate(R.layout.row_topfund_odd, parent, false);
        } else {
            view = inflater.inflate(R.layout.row_top_fund_even, parent, false);
        }


        return new FundViewHolder(view, isInner);
    }

    @Override
    public void onBindViewHolder(FundViewHolder holder, int position) {
        FundDataModal modal = data.get(position);

        holder.textViewSchemeName.setText(modal.getSchemeName());
        holder.textViewType.setText(modal.getFundType());
        holder.textViewNAV.setText(modal.getNAV());

        holder.textViewGrowth.setText(String.format("+%s (%s%%)", modal.getGrowth(), modal.getGrowthPercentage()));
        if (modal.getIcon() != null) {

            int id = mContext.getResources().getIdentifier(modal.getIcon(), "mipmap", mContext.getPackageName());
            holder.imageView.setImageDrawable(mContext.getResources().getDrawable(id));
        }

        setAnimation(holder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position % 2;
    }

    private void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(mContext, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    public class FundViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private SIPTextView textViewSchemeName;
        private SIPTextView textViewType;
        private RupeesTextView textViewNAV;
        private SIPTextView textViewGrowth;

        public FundViewHolder(View itemView, boolean isInner) {
            super(itemView);

            textViewSchemeName = (SIPTextView) itemView.findViewById(R.id.textViewSchemeName);
            textViewType = (SIPTextView) itemView.findViewById(R.id.textViewType);
            textViewNAV = (RupeesTextView) itemView.findViewById(R.id.textViewNAV);
            textViewGrowth = (SIPTextView) itemView.findViewById(R.id.textViewGrowth);
            imageView = (ImageView) itemView.findViewById(R.id.imageViewIcon);

            if (isInner) {
                imageView.setVisibility(View.GONE);
            }
        }
    }
}
