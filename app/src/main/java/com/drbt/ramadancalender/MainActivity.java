package com.drbt.ramadancalender;

import android.graphics.Color;
import android.graphics.Point;

import android.os.Build;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    NestedScrollView bottomScroll;
    LinearLayout topScroll;
    LinearLayout topLinearLayout,topInsideLinearlayout;
    TextView alarmDate,currentDate,currentYear,weekArabicDate,dayOffset;
    CardView upperCard,insideRamdan,outsideRamadan;
    MaterialCalendarView materialCalendarView;

    Calendar calender;

    public final String[] MONTH = {"January","February","March","April","May","June","July","August","September","October","November","December"};
    public final String[] DAYS = {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
    public final int[] ARABIC_DAYS = {2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //topScroll = (LinearLayout) findViewById(R.id.topScroll);
        //bottomScroll = (NestedScrollView) findViewById(R.id.bottomScroll);
        //topLinearLayout = (LinearLayout) findViewById(R.id.topLinearLayout);
        //topInsideLinearlayout = (LinearLayout) findViewById(R.id.topInsideLinearlayout);
        upperCard = (CardView) findViewById(R.id.upperPart);
        insideRamdan = (CardView) findViewById(R.id.insideRamadan);
        outsideRamadan = (CardView) findViewById(R.id.outsideRamadan);
        materialCalendarView = (MaterialCalendarView) findViewById(R.id.calendarView);
        alarmDate = (TextView) findViewById(R.id.alarmDate);
        currentDate = (TextView) findViewById(R.id.currentDate);
        currentYear = (TextView) findViewById(R.id.currentYear);
        weekArabicDate = (TextView) findViewById(R.id.weekArabicDate);
        dayOffset = (TextView) findViewById(R.id.offsetDayText);

        calender = Calendar.getInstance();
        materialCalendarView.setSelectedDate(calender);

        String currentMonthText = MONTH[calender.get(Calendar.MONTH)];
        int currentYearText = calender.get(Calendar.YEAR);
        int currentDateText = calender.get(Calendar.DATE);

        calender.get(Calendar.DAY_OF_YEAR);

        alarmDate.setText(currentMonthText+" "+currentDateText);
        currentDate.setText(currentMonthText+" "+currentDateText+", ");
        currentYear.setText(currentYearText+"");

        String arabicMonth="";
        int arabicDate=0,arabicYear=1438;

        if(currentMonthText.equals("May")) {
            if(currentDateText<=27) {
                arabicMonth = "Sha'ban";
                arabicDate = currentDateText+3;
            }else{
                arabicMonth = "Ramadhan";
                arabicDate = currentDateText-27;
            }
        }
        else if(currentMonthText.equals("June")) {
            if(currentDateText<=25) {
                arabicMonth = "Ramadhan";
                arabicDate = currentDateText+4;
            }else{
                arabicMonth = "Shawwal";
                arabicDate = currentDateText-25;
            }
        }

        int dayOfWeek = calender.get(Calendar.DAY_OF_WEEK);
        weekArabicDate.setText(DAYS[dayOfWeek-1]+" | "+arabicMonth+" "+arabicDate+", "+arabicYear);

        int dayOfYear = calender.get(Calendar.DAY_OF_YEAR);





        if(dayOfYear<148) { //Not the month of Ramadan
            insideRamdan.setVisibility(View.GONE);
            outsideRamadan.setVisibility(View.VISIBLE);

            SpannableStringBuilder builder = new SpannableStringBuilder();

            String green = (148-dayOfYear)+"";
            SpannableString greenSpannable= new SpannableString(green);
            greenSpannable.setSpan(new ForegroundColorSpan(Color.parseColor("#6FCA4D")), 0, green.length(), 0);
            builder.append(greenSpannable);

            String daysTill = " days till the month of";
            SpannableString daysTillSpannable= new SpannableString(daysTill);
            //greenSpannable.setSpan(new ForegroundColorSpan(Color.parseColor("#6FCA4D")), 0, green.length(), 0);
            builder.append(daysTillSpannable);

            String ramadan = " Ramadan ";
            SpannableString ramadanSpannable= new SpannableString(ramadan);
            ramadanSpannable.setSpan(new ForegroundColorSpan(Color.parseColor("#BE4338")), 0, ramadan.length(), 0);
            builder.append(ramadanSpannable);

            String s = "starts";
            SpannableString startsSpannable= new SpannableString(s);
            //startsSpannable.setSpan(new ForegroundColorSpan(Color.parseColor("#BE4338")), 0, s.length(), 0);
            builder.append(startsSpannable);

            dayOffset.setText(builder, TextView.BufferType.SPANNABLE);

            //dayOffset.setText();
        }
        else if(dayOfYear>178) {
            insideRamdan.setVisibility(View.GONE);
            outsideRamadan.setVisibility(View.VISIBLE);

            SpannableStringBuilder builder = new SpannableStringBuilder();


            String green = (dayOfYear-178)+"";
            SpannableString greenSpannable= new SpannableString(green);
            greenSpannable.setSpan(new ForegroundColorSpan(Color.parseColor("#6FCA4D")), 0, green.length(), 0);
            builder.append(greenSpannable);

            String daysTill = " days passed since Ramadan.";
            SpannableString daysTillSpannable= new SpannableString(daysTill);
            //greenSpannable.setSpan(new ForegroundColorSpan(Color.parseColor("#6FCA4D")), 0, green.length(), 0);
            builder.append(daysTillSpannable);

            dayOffset.setText(builder, TextView.BufferType.SPANNABLE);

            //dayOffset.setText(" days passed since Ramadan.");
        }
        else {
            insideRamdan.setVisibility(View.VISIBLE);
            outsideRamadan.setVisibility(View.GONE);
        }

//        int y1=0,y2=0;
//        bottomScroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
//            @Override
//            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//
//                //if (scrollY % 5 == 0) {
//                    if (scrollY > oldScrollY) {
//                        //expandOrCollapse(topLinearLayout, "collapse", scrollY, oldScrollY);
//                        DropDownAnim dropDownAnim = new DropDownAnim(upperCard,upperCard.getHeight()-(scrollY/5),false);
//                        dropDownAnim.setDuration(5000);
//                        upperCard.startAnimation(dropDownAnim);
//                    } else {
//                        //expandOrCollapse(topLinearLayout, "expand", oldScrollY, scrollY);
//                        DropDownAnim dropDownAnim = new DropDownAnim(upperCard,upperCard.getHeight()+(scrollY/5),true);
//                        dropDownAnim.setDuration(5000);
//                        upperCard.startAnimation(dropDownAnim);
//                    }
//                //}
//            }
//        });

    }

    public void expandOrCollapse(final View v,String exp_or_colpse,int oldY,int newY) {
        TranslateAnimation anim = null;
        if(exp_or_colpse.equals("expand"))
        {
            anim = new TranslateAnimation(0.0f, 0.0f, -oldY, newY);
            v.setVisibility(View.VISIBLE);
        }
        else{
            anim = new TranslateAnimation(0.0f, 0.0f, oldY, -newY);
            Animation.AnimationListener collapselistener= new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    v.setVisibility(View.GONE);
                }
            };

            anim.setAnimationListener(collapselistener);
        }

        // To Collapse
        //

        anim.setDuration(300);
        anim.setInterpolator(new AccelerateInterpolator(0.5f));
        v.startAnimation(anim);
    }

    public class DropDownAnim extends Animation {
        private final int targetHeight;
        private final View view;
        private final boolean down;

        public DropDownAnim(View view, int targetHeight, boolean down) {
            this.view = view;
            this.targetHeight = targetHeight;
            this.down = down;
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            int newHeight;
            if (down) {
                newHeight = (int) (targetHeight * interpolatedTime);
            } else {
                newHeight = (int) (targetHeight * (1 - interpolatedTime));
            }
            view.getLayoutParams().height = newHeight;
            view.requestLayout();
        }

        @Override
        public void initialize(int width, int height, int parentWidth,
                               int parentHeight) {
            super.initialize(width, height, parentWidth, parentHeight);
        }

        @Override
        public boolean willChangeBounds() {
            return true;
        }
    }
}
