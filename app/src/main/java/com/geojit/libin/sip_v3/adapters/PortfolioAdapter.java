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
import com.geojit.libin.sip_v3.modals.PortfolioDataModel;

import java.util.ArrayList;

/**
 * Created by 10946 on 8/17/2016.
 */
public class PortfolioAdapter extends RecyclerView.Adapter<PortfolioAdapter.PortfolioViewHolder> {

    private boolean isInner;
    private Context mContext;
    private int lastPosition = 0;
    private ArrayList<PortfolioDataModel> data;

    public PortfolioAdapter(ArrayList<PortfolioDataModel> data, boolean isInner) {
        this.data = data;
        this.isInner = isInner;
    }

    @Override
    public PortfolioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);

        View view;
        if (viewType == 1) {
            view = inflater.inflate(R.layout.row_portfolio_odd, parent, false);
        } else {
            view = inflater.inflate(R.layout.row_portfolio_even, parent, false);
        }


        return new PortfolioViewHolder(view, isInner);
    }

    @Override
    public int getItemViewType(int position) {
        return position % 2;
    }

    @Override
    public void onBindViewHolder(PortfolioViewHolder holder, int position) {

        PortfolioDataModel modal = data.get(position);

        holder.textViewSchemeName.setText(modal.getSchemeName());
        //holder.textViewFundType.setText(modal.getFundType());
       // holder.textViewNAV.setText(modal.getNAV());
        holder.textViewHoldingUnit.setText(modal.getHoldingUnit());
        holder.textViewHoldingValue.setText(modal.getHoldingValue());

        if (modal.getIcon() != null) {
            switch (modal.getIcon()) {
                case "birla":
                    holder.imageView.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.birla));
                    break;

                case "geojit":
                    holder.imageView.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.geojit));
                    break;

                case "icici":
                    holder.imageView.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.icici));
                    break;

                case "l&t":
                    holder.imageView.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.lt));
                    break;

                case "sbi":
                    holder.imageView.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.sbi));
                    break;

                case "kotak":
                    holder.imageView.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.kotak));
                    break;

                case "uti":
                    holder.imageView.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.uti));
                    break;

                case "axis":
                    holder.imageView.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.axis));
                    break;

                case "sundaram":
                    holder.imageView.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.sf));
                    break;

                case "tata":
                    holder.imageView.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.tata));
                    break;

                case "reliance":
                    holder.imageView.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.rel));
                    break;

                case "invesco":
                    holder.imageView.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.inves));
                    break;

                case "franklin":
                    holder.imageView.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.franklin));
                    break;

                case "dsp":
                    holder.imageView.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.dsp));
                    break;

                case "dhfl":
                    holder.imageView.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.dhfl));
                    break;

            }
        }

    }

    private void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(mContext, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public class PortfolioViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private SIPTextView textViewFundType,textViewHoldingUnit,textViewHoldingValue,textViewSchemeName;
        private RupeesTextView textViewNAV;

        public PortfolioViewHolder(View itemView, boolean isInner) {
            super(itemView);

            textViewSchemeName = (SIPTextView) itemView.findViewById(R.id.textViewSchemeName);
            //textViewFundType = (SIPTextView) itemView.findViewById(R.id.textViewFundType);
            textViewNAV = (RupeesTextView) itemView.findViewById(R.id.textViewNAV);
            textViewHoldingUnit = (SIPTextView) itemView.findViewById(R.id.textViewHoldingUnit);
            textViewHoldingValue = (SIPTextView) itemView.findViewById(R.id.textViewHoldingValue);
            imageView = (ImageView) itemView.findViewById(R.id.imageViewIcon);

            if (isInner) {
                imageView.setVisibility(View.GONE);
            }
        }
    }

}
