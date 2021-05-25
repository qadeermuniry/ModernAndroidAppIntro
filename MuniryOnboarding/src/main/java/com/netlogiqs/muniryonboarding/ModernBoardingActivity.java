package com.netlogiqs.muniryonboarding;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ModernBoardingActivity extends AppCompatActivity {
    int numPages;
    List<OnBoardingModel> listBoarding;
    ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_modern_boarding);
        initViews();


        final LayoutInflater mLayoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return numPages;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                final View itemView = mLayoutInflater.inflate(R.layout.item_modern_boarding, container, false);
                CardView cvBtn = itemView.findViewById(R.id.cvv);
                Button btnGetStart = itemView.findViewById(R.id.btnGetStart);
                ImageView imageView = itemView.findViewById(R.id.imgMain);
                TextView txtTitle = itemView.findViewById(R.id.txtTitle);
                TextView txtMain = itemView.findViewById(R.id.txtMain);
                imageView.setImageResource(listBoarding.get(position).getDrawable());
                txtTitle.setText(listBoarding.get(position).getTitle());
                txtMain.setText(listBoarding.get(position).getDesc());


                imageView.setVisibility(View.VISIBLE);
                txtTitle.setVisibility(View.VISIBLE);

                imageView.clearAnimation();
                txtTitle.clearAnimation();
                imageView.setAnimation(null);


                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.view_to_top);
                imageView.setAnimation(animation);
                txtTitle.setAnimation(animation);


                cvBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        viewPager.setCurrentItem(position + 1);
                        imageView.setVisibility(View.GONE);
                        txtTitle.setVisibility(View.GONE);
                    }
                });

                if (position == 2) {
                    cvBtn.setVisibility(View.INVISIBLE);
                    btnGetStart.setVisibility(View.VISIBLE);
                }

                new Timer().scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        doBounceAnimation(cvBtn);
                    }
                }, 0, 5000);


                container.addView(itemView);
                return itemView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((RelativeLayout) object);
            }
        });
    }

    private void initViews() {
        viewPager = findViewById(R.id.view_pager);

        viewPager.setOnTouchListener((view, motionEvent) -> true);


    }

    private void doBounceAnimation(View targetView) {
        Interpolator interpolator = v -> {
            return getPowOut(v, 2);//Add getPowOut(v,3); for more up animation
        };
        ObjectAnimator animator = ObjectAnimator.ofFloat(targetView, "translationY", 0, 25, 0);
        animator.setInterpolator(interpolator);
        animator.setStartDelay(200);
        animator.setDuration(800);
        animator.setRepeatCount(2);

        runOnUiThread(() -> animator.start());

    }

    private float getPowOut(float elapsedTimeRate, double pow) {
        return (float) ((float) 1 - Math.pow(1 - elapsedTimeRate, pow));
    }

    public ModernBoardingActivity(List<OnBoardingModel> modelList) {
        this.listBoarding = modelList;
        this.numPages = modelList.size();
    }
}
