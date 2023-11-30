package my.fueldelivery.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import my.fueldelivery.app.attendantProductPanel.AttendantHomeFragment;
import my.fueldelivery.app.attendantProductPanel.AttendantOrderFragment;
import my.fueldelivery.app.attendantProductPanel.AttendantPendingOrderFragment;
import my.fueldelivery.app.attendantProductPanel.AttendantProfileFragment;

public class AttendantProductPanel_BottomNavigation extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendant_product_panel_bottom_navigation);

        BottomNavigationView navigationView = findViewById(R.id.attendant_bottom_navigation);
        navigationView.setOnNavigationItemSelectedListener(this);
        String name = getIntent().getStringExtra("PAGE");
        if (name!=null){
            if (name.equalsIgnoreCase("Orderpage")) {
                loadattendantfragment(new AttendantPendingOrderFragment());
            }else if (name.equalsIgnoreCase("Confirmgpage")) {
                loadattendantfragment(new AttendantOrderFragment());
            }else if (name.equalsIgnoreCase("AcceptOrderpage")) {
                loadattendantfragment(new AttendantHomeFragment());
            }else if (name.equalsIgnoreCase("Deliveredpage")) {
                loadattendantfragment(new AttendantHomeFragment());
            }
        }else {
            loadattendantfragment(new AttendantHomeFragment());
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()){
            case R.id.attendantHome:
                fragment=new AttendantHomeFragment();
                break;
            case R.id.PendingOrders:
                fragment=new AttendantPendingOrderFragment();
                break;
            case R.id.Orders:
                fragment=new AttendantOrderFragment();
                break;
            case R.id.attendantProfile:
                fragment=new AttendantProfileFragment();
                break;
        }
        return loadattendantfragment(fragment);
    }

    private boolean loadattendantfragment(Fragment fragment) {

        if (fragment != null){
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,fragment).commit();
            return true;
        }
        return false;
    }
}