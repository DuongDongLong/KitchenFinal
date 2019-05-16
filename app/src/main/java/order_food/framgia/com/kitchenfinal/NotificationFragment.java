package order_food.framgia.com.kitchenfinal;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class NotificationFragment extends Fragment {
    FirebaseRecyclerOptions<Order> options;
    FirebaseRecyclerAdapter<Order, NotificationViewHolder> adapter;
    DatabaseReference databaseReference;
    RecyclerView mRecyclerView;


    public String getTime(long millis){
        long days = TimeUnit.MILLISECONDS.toDays(millis);
        millis -= TimeUnit.DAYS.toMillis(days);
        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        long hoursCurrent= hours <=16 ? hours+7 : hours-17 ;
        millis -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        millis -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);
        String strHour= hoursCurrent<10? "0"+hoursCurrent : hoursCurrent+"";
        String strMinutes= minutes<10? "0"+minutes : minutes+"";
        return  strHour+":"+strMinutes;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = LayoutInflater.from(container.getContext()).inflate(R.layout.notification_fragment, container, false);

        mRecyclerView = view.findViewById(R.id.recyclerView_notification);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        databaseReference = FirebaseDatabase.getInstance().getReference("Order");

        options = new FirebaseRecyclerOptions.Builder<Order>().setQuery(databaseReference.orderByChild("status").equalTo("order"), new SnapshotParser<Order>() {
            @NonNull
            @Override
            public Order parseSnapshot(@NonNull DataSnapshot snapshot) {
                // TODO parse data ra đây
                List<Cart> cartList= (List<Cart>) snapshot.child("cart").getValue();
                return new Order(snapshot.child("table").getValue().toString(),Double.valueOf(snapshot.child("total").getValue().toString()),snapshot.child("status").getValue().toString());
            }
        }).build();

        adapter = new FirebaseRecyclerAdapter<Order, NotificationViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final NotificationViewHolder holder, int position, @NonNull final Order oder) {
                Log.d("TAGG", "parseSnapshot: " + position);
                holder.table.setText("TABLE "+oder.getTable());

                holder.time.setText(getTime(Long.valueOf(adapter.getRef(position).getKey())));
                holder.setItemClickListener(new OnClickItem() {
                    @Override
                    public void setOnClickItem(int position) {
                        Bundle bundle=new Bundle();
                        bundle.putString("TABLE",oder.getTable());
                        bundle.putString("KEY",adapter.getRef(position).getKey()+"");
                        NotificationDetailFragment notificationDetailFragment=new NotificationDetailFragment();
                        notificationDetailFragment.setArguments(bundle);
                        getFragmentManager().beginTransaction().add(R.id.frame_main,notificationDetailFragment).addToBackStack(null).commit();
                    }
                });
            }

            @NonNull
            @Override
            public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view1 = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_notification_layout, viewGroup, false);
                return new NotificationViewHolder(view1);
            }
        };
        adapter.startListening();
        mRecyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }
}