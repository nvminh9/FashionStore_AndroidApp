package com.example.appshop;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private ViewPager2 mViewPager2;
    private BottomNavigationView mBottomNavigationView;
    //Layout Manager dùng cho RecyclerView Tin tức
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
        initListener();

        // Xử lý ViewPager chuyển các Fragment
        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        mViewPager2.setAdapter(adapter);
            // Hiệu ứng chuyển Fragment theo chiều sâu
        mViewPager2.setPageTransformer(new DepthPageTransformer());
        mViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position){
                    case 0:
                        mBottomNavigationView.getMenu().findItem(R.id.tab_home).setChecked(true);
                        break;
                    case 1:
                        mBottomNavigationView.getMenu().findItem(R.id.tab_favorite).setChecked(true);
                        break;
                    case 2:
                        mBottomNavigationView.getMenu().findItem(R.id.tab_cart).setChecked(true);
                        break;
                    case 3:
                        mBottomNavigationView.getMenu().findItem(R.id.tab_profile).setChecked(true);
                        break;
                }
            }
        });

        // Bắt sự kiện click chọn của item trong BottomNavigationView ( sau đó setCurrentItem với position của id item được chọn cho ViewPager )
        mBottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.tab_home){
                    mViewPager2.setCurrentItem(0);
                }else if(item.getItemId() == R.id.tab_favorite){
                    mViewPager2.setCurrentItem(1);
                }else if(item.getItemId() == R.id.tab_cart){
                    mViewPager2.setCurrentItem(2);
                }else if(item.getItemId() == R.id.tab_profile){
                    mViewPager2.setCurrentItem(3);
                }
                return true;
            }
        });

    }

    private void initUI(){
        mViewPager2 = (ViewPager2) findViewById(R.id.view_pager2);
        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
    }

    private void initListener(){

    }
}