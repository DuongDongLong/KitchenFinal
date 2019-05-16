package order_food.framgia.com.kitchenfinal.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import order_food.framgia.com.kitchenfinal.R;

public class NotificationDeatailViewHolder  extends RecyclerView.ViewHolder {
    public TextView name,quantity;
    public ImageView imageView;
    public NotificationDeatailViewHolder(@NonNull View itemView) {
        super(itemView);
        name=itemView.findViewById(R.id.textViewNameFood);
        quantity=itemView.findViewById(R.id.textViewQuantityItem);
        imageView=itemView.findViewById(R.id.imageView);
    }
}