package com.geojit.libin.sip_v3.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.geojit.libin.sip_v3.R;
import com.geojit.libin.sip_v3.components.SIPTextView;

/**
 * Created by 10945 on 8/10/2016.
 */
public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {

    private String[] menu;

    public MenuAdapter(String[] menu) {
        this.menu = menu;
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_menu, parent, false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MenuViewHolder holder, int position) {

        holder.textViewTitle.setText(menu[position]);
    }

    @Override
    public int getItemCount() {
        return menu.length;
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder {

        private SIPTextView textViewTitle;

        public MenuViewHolder(View itemView) {
            super(itemView);

            textViewTitle = (SIPTextView) itemView.findViewById(R.id.menu_title);
        }
    }
}
