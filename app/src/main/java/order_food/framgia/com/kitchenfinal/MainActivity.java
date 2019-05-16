package order_food.framgia.com.kitchenfinal;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_container, new HomeFragment())
                .commit();
        databaseReference=FirebaseDatabase.getInstance().getReference().child("Order").child("1557496282204").child("cart");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //List<Cart> value = (List<Cart>) dataSnapshot.getValue();
                //Log.d("TAG", "onDataChange:"+value.get(0).getName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        initView();
    }

    private void initView() {
        BottomNavigationView navigationView = findViewById(R.id.bottom_navigation);
        navigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                fragment = new HomeFragment();
                break;
            case R.id.nav_notification:
                fragment = new NotificationFragment();
                break;
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_container, fragment)
                .commit();
        return true;
    }
}
