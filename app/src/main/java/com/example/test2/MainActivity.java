package com.example.test2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private OnboardingAdapter onboardingAdapter;
    private LinearLayout layoutOnboardingIndicators;
    private MaterialButton buttonOnboardingAction;

    EditText emailId, password;
    Button btnSignup;
    TextView tvSignIn;
    FirebaseAuth mFirebaseAuth;



    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layoutOnboardingIndicators = findViewById(R.id.layoutOnboardingIndicators);
        buttonOnboardingAction = findViewById(R.id.buttonOnboardingAction);


        setupOnboardingItems();

        ViewPager2 onboardingViewPager = findViewById(R.id.onboardingViewPager);
        onboardingViewPager.setAdapter(onboardingAdapter);

        setupOnboardingIndicators();
        setCurrentOnboardingIndicator(0);

        onboardingViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentOnboardingIndicator(position);



            }
        });

        buttonOnboardingAction.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onboardingViewPager.getCurrentItem() + 1 <onboardingAdapter.getItemCount()){
                    onboardingViewPager.setCurrentItem(onboardingViewPager.getCurrentItem() + 1);
                }else{
                    startActivity(new Intent(getApplicationContext(), MainActivity2.class));
                    finish();
                }
            }
        });

    }

    private void setupOnboardingItems() {

        List<OnboardingItem> onboardingItems = new ArrayList<>();

        OnboardingItem itemPayOnline = new OnboardingItem();
        itemPayOnline.setTitle("Luptă pentru progres, nu pentru perfecțiune.");
        itemPayOnline.setDescription("Trebuie să păstrezi o gândire pozitivă și să-ți repeți că poți continua acest program, chiar dacă este greu, chiar dacă te simți obosit după 40 de repetări, chiar dacă ai transpirat și te simți epuizat. Scuzele nu vă definesc, nici nu te ajută.");
        itemPayOnline.setImage(R.drawable.intro);

        OnboardingItem itemOnTheWay = new OnboardingItem();
        itemOnTheWay.setTitle("Transformarea nu este un eveniment viitor, ci o activitate prezentă.");
        itemOnTheWay.setDescription("Care a fost scopul tău? Amintește-ți: nu ai început să te antrenezi la întâmplare. Așadar, trebuie să-ți furnizezi motivele necesare, care te-au convins să începi programul de antrenament.");
        itemOnTheWay.setImage(R.drawable.illustration);

        OnboardingItem itemEatTogether = new OnboardingItem();
        itemEatTogether.setTitle("Nu-ți spun că va fi ușor, dar cu siguranță va merita.");
        itemEatTogether.setDescription("Nu îți sculptezi doar corpul, ci și creierul! Când te provoci, îți îmbunătățești corpul și atitudinea.");
        itemEatTogether.setImage(R.drawable.banner);

        onboardingItems.add(itemPayOnline);
        onboardingItems.add(itemOnTheWay);
        onboardingItems.add(itemEatTogether);

        onboardingAdapter = new OnboardingAdapter(onboardingItems);
    }

    private void setupOnboardingIndicators()
    {
        ImageView[] indicators = new ImageView[onboardingAdapter.getItemCount()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8, 0, 8, 0);
        for(int i = 0; i < indicators.length; i++){
            indicators[i] = new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(
                    getApplicationContext(),
                    R.drawable.onboarding_indicator_inactive
            ));
            indicators[i].setLayoutParams(layoutParams);
            layoutOnboardingIndicators.addView(indicators[i]);
        }

    }

    @SuppressLint("SetTextI18n")
    private void setCurrentOnboardingIndicator(int index) {
            int childCount = layoutOnboardingIndicators.getChildCount();
            for(int i = 0;  i < childCount; i++){
                ImageView imageView = (ImageView) layoutOnboardingIndicators.getChildAt(i);
                if(i == index){
                    imageView.setImageDrawable(
                            ContextCompat.getDrawable(getApplicationContext(),R.drawable.onboarding_indicator_active)
                    );
                }else
                {
                    imageView.setImageDrawable(
                            ContextCompat.getDrawable(getApplicationContext(),R.drawable.onboarding_indicator_inactive)
                    );
                }
        }
            if (index == onboardingAdapter.getItemCount()-1){
                buttonOnboardingAction.setText("Start");
            }else{
                buttonOnboardingAction.setText("Next");
            }
    }

}