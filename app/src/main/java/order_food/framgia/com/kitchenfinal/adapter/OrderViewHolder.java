package order_food.framgia.com.kitchenfinal.adapter;



import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import order_food.framgia.com.kitchenfinal.R;

public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView table,time;
        private OnClickItem itemClickListener;
        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            table=itemView.findViewById(R.id.txtTable);
            time=itemView.findViewById(R.id.order_time);
            itemView.setOnClickListener(this);

        }

        public void setItemClickListener(OnClickItem itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.setOnClickItem(getAdapterPosition());
        }

    }
