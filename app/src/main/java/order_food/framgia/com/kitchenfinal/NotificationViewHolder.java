package order_food.framgia.com.kitchenfinal;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import order_food.framgia.com.kitchenfinal.adapter.OnClickItem;

public class NotificationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView table,time;
    private OnClickItem itemClickListener;
    public NotificationViewHolder(@NonNull View itemView) {
        super(itemView);
        table=itemView.findViewById(R.id.notification_table);
        time=itemView.findViewById(R.id.notification_time);
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
