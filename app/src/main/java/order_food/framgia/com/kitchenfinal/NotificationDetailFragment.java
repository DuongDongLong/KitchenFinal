package order_food.framgia.com.kitchenfinal;

import android.app.AlertDialog;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import order_food.framgia.com.kitchenfinal.adapter.NotificationDeatailViewHolder;

public class NotificationDetailFragment extends Fragment implements View.OnClickListener {
    TextView table;
    String txtTable, key;
    Button accept,update;
    String tableTitle;
    EditText note,time;
    List<Cart> carts =new ArrayList<>();
    FirebaseRecyclerOptions<Cart> options;
    FirebaseRecyclerAdapter<Cart, NotificationDeatailViewHolder> adapter;
    DatabaseReference databaseReference;
    DatabaseReference databaseReferenceUpdate;
    DatabaseReference databaseReferenceNotification;
    RecyclerView mRecyclerView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        final View view = inflater.inflate(R.layout.notification_detail_fragment, container, false);
        mRecyclerView=view.findViewById(R.id.recyclerViewCart);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        table = view.findViewById(R.id.textTable);
        accept=view.findViewById(R.id.button_order_accept);
        update=view.findViewById(R.id.button_update);


        accept.setOnClickListener(this);
        update.setOnClickListener(this);
        tableTitle=getArguments().getString("TABLE");
        txtTable = tableTitle;
        key = getArguments().getString("KEY");

        Log.d("TAG", "onCreateView: "+key);
        table.setText("Table" + txtTable);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Order").child(key).child("cart");
        databaseReferenceUpdate = FirebaseDatabase.getInstance().getReference().child("Order").child(key);
        databaseReferenceNotification = FirebaseDatabase.getInstance().getReference().child("Notification");


        options = new FirebaseRecyclerOptions.Builder<Cart>().setQuery(databaseReference, new SnapshotParser<Cart>() {
            @NonNull
            @Override
            public Cart parseSnapshot(@NonNull DataSnapshot snapshot) {
                // TODO parse data ra đây
                Cart cart= new Cart(snapshot.child("name").getValue().toString(),
                        snapshot.child("description").getValue().toString(),
                        snapshot.child("image").getValue().toString(),
                        snapshot.child("discount").getValue().toString(),
                        snapshot.child("menuId").getValue().toString(),
                        snapshot.child("price").getValue().toString(),
                        Integer.valueOf(snapshot.child("quantity").getValue().toString()));
                carts.add(cart);
                return cart;
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
            case R.id.button_order_accept:
                final  AlertDialog.Builder alertBuilder = new AlertDialog.Builder(v.getContext());
                View view=getLayoutInflater().inflate(R.layout.oder_dialog,null);
                Button send=view.findViewById(R.id.button_send);
                Button cancel=view.findViewById(R.id.button_cancel);
                note=view.findViewById(R.id.textViewNode);
                time=view.findViewById(R.id.textViewQuantityCart);
                alertBuilder.setView(view);
                final AlertDialog dialog=alertBuilder.create();
                send.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(Integer.valueOf(time.getText().toString())<0||Integer.valueOf(time.getText().toString())>60)
                            Toast.makeText(getContext(),"Time failed",Toast.LENGTH_SHORT).show();
                        else {
                            Notification notification=new Notification(carts,tableTitle,"Order accept please wait  ",note.getText().toString(),time.getText().toString(),"update");
                            databaseReferenceNotification.child(key).setValue(notification);
                            databaseReferenceUpdate.child("status").setValue("accept");
                            getFragmentManager().popBackStack();
                            dialog.dismiss();
                        }
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                break;
            case  R.id.button_update:
                final  AlertDialog.Builder alertBuilder1 = new AlertDialog.Builder(v.getContext());
                View view1=getLayoutInflater().inflate(R.layout.oder_dialog_update,null);
                final EditText note1=view1.findViewById(R.id.textViewNodeUpdate);
                Button sendUpdate=view1.findViewById(R.id.button_send_update);
                Button cancelUpdate=view1.findViewById(R.id.button_cancel_update);
                alertBuilder1.setView(view1);
                final AlertDialog dialog1=alertBuilder1.create();
                sendUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Notification notification=new Notification(carts,tableTitle,"Over food , pls order again ",note1.getText().toString(),"0","update");

                        String key =  databaseReferenceNotification.push().getKey();
                        databaseReferenceNotification.child(key).setValue(notification);
                        databaseReferenceUpdate.removeValue();
                        getFragmentManager().popBackStack();
                        dialog1.dismiss();
                    }
                });
                cancelUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog1.dismiss();
                    }
                });

                dialog1.show();

                break;
        }
    }
}
