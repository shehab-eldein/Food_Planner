package com.example.foodplanner.OnBoarding.OnBoarding;

import android.os.Build;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
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
    Button  skipbtn;
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
    public void onViewCreated( View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        skipbtn = view.findViewById(R.id.skipButton);
        mSLideViewPager = (ViewPager) view.findViewById(R.id.slideViewPager);
        mDotLayout = (LinearLayout) view.findViewById(R.id.indicator_layout);

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        viewPagerAdapter = new ViewPagerAdapter(requireContext());
        mSLideViewPager.setAdapter(viewPagerAdapter);

        setUpindicator(0);
        mSLideViewPager.addOnPageChangeListener(viewListener);
        skipBtnClicked();

    }
    public void skipBtnClicked() {
        skipbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Navigation.findNavController(v).navigate(R.id.action_onBoardingBase_Fragment_to_signs_Fragment);

                //finish();

            }
        });
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



        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
    private int getitem(int i){
        return mSLideViewPager.getCurrentItem() + i;
    }

}