package com.example.foodplanner.OnBoarding;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.foodplanner.R;
public class onBoardingBase_Fragment extends Fragment {

    ViewPager mSLideViewPager;
    LinearLayout mDotLayout;
    Button backbtn, nextbtn, skipbtn;
    TextView[] dots;
    ViewPagerAdapter viewPagerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_on_boarding_base_, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //backbtn = view.findViewById(R.id.backbtn);
        //nextbtn = view.findViewById(R.id.nextbtn);
        //skipbtn = view.findViewById(R.id.skipButton);
/*        skipbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                /*Intent i = new Intent(MainActivity.this,mainscreen.class);
                startActivity(i);
                finish();

            }
        });*/
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        mSLideViewPager = (ViewPager) view.findViewById(R.id.slideViewPager);
        mDotLayout = (LinearLayout) view.findViewById(R.id.indicator_layout);

        viewPagerAdapter = new ViewPagerAdapter(requireContext());

        mSLideViewPager.setAdapter(viewPagerAdapter);

        setUpindicator(0);
        mSLideViewPager.addOnPageChangeListener(viewListener);

    }
    public void setUpindicator(int position){

        dots = new TextView[4];
        mDotLayout.removeAllViews();

        for (int i = 0 ; i < dots.length ; i++){

            dots[i] = new TextView(requireContext());
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(35);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                dots[i].setTextColor(getResources().getColor(R.color.inactive,requireContext().getTheme()));
            }
            mDotLayout.addView(dots[i]);

        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            dots[position].setTextColor(getResources().getColor(R.color.active,requireContext() .getTheme()));
        }

    }
    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }


        @Override
        public void onPageSelected(int position) {

            setUpindicator(position);

            if (position > 0){

                //backbtn.setVisibility(View.VISIBLE);

            }else {

                //backbtn.setVisibility(View.INVISIBLE);

            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
    private int getitem(int i){
        return mSLideViewPager.getCurrentItem() + i;
    }

}