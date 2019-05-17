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
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import order_food.framgia.com.kitchenfinal.adapter.NotificationDeatailViewHolder;

public class OrderDetailFragment extends Fragment implements View.OnClickListener {
    TextView table;
    String txtTable, key;
    Button done,update;
    FirebaseRecyclerOptions<Cart> options;
    FirebaseRecyclerAdapter<Cart, NotificationDeatailViewHolder> adapter;
    DatabaseReference databaseReference;
    DatabaseReference databaseReferenceUpdate;
    RecyclerView mRecyclerView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        final View view = inflater.inflate(R.layout.order_detail_fragment, container, false);
        mRecyclerView=view.findViewById(R.id.order_recyclerViewCart);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        table = view.findViewById(R.id.textTableOrder);
        txtTable = getArguments().getString("TABLE");
        key = getArguments().getString("KEY");

        done=view.findViewById(R.id.button_order_accept);
        databaseReferenceUpdate = FirebaseDatabase.getInstance().getReference().child("Order").child(key);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
                databaseReferenceUpdate.child("status").setValue("done");
            }
        });

        Log.d("TAG", "onCreateView: "+key);
        table.setText("Table" + txtTable);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Order").child(key).child("cart");



        options = new FirebaseRecyclerOptions.Builder<Cart>().setQuery(databaseReference, new SnapshotParser<Cart>() {
            @NonNull
            @Override
            public Cart parseSnapshot(@NonNull DataSnapshot snapshot) {
                // TODO parse data ra đây
                return new Cart(snapshot.child("name").getValue().toString(),
                        snapshot.child("description").getValue().toString(),
                        snapshot.child("image").getValue().toString(),
                        snapshot.child("discount").getValue().toString(),
                        snapshot.child("menuId").getValue().toString(),
                        snapshot.child("price").getValue().toString(),
                        Integer.valueOf(snapshot.child("quantity").getValue().toString()));
            }


        }).build();

        adapter = new FirebaseRecyclerAdapter<Cart, NotificationDeatailViewHolder>(options) {

            @NonNull
            @Override
            public NotificationDeatailViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                View view1 = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_notification_detail_layout, viewGroup, false);
                return new NotificationDeatailViewHolder(view1);
            }

            @Override
            protected void onBindViewHolder(@NonNull NotificationDeatailViewHolder holder, int position, @NonNull Cart model) {
                holder.name.setText(model.getName());
                holder.quantity.setText(model.getQuantity()+"");
                Glide.with(view).load(model.getImage()).into(holder.imageView);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){


        }
    }
}
