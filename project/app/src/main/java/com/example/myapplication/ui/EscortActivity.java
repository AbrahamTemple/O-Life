package com.example.myapplication.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.myapplication.R;
import com.example.myapplication.domain.CardBean;
import com.example.myapplication.domain.ProvinceBean;
import com.example.myapplication.event.RxTimer;
import com.example.myapplication.util.HideUtil;
import com.example.myapplication.util.SharedPreferencesUtils;
import com.example.myapplication.view.fragment.BezierFragment;
import com.example.myapplication.view.fragment.BgFragment;
import com.example.myapplication.view.fragment.LoadingFragment;
import com.example.myapplication.view.layout.ChatBarView;
import com.example.myapplication.view.layout.ResizableImageView;
import com.skydoves.elasticviews.ElasticAnimation;
import com.skydoves.elasticviews.ElasticButton;
import com.skyfishjy.library.RippleBackground;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = "/olife/escort")
public class EscortActivity extends AppCompatActivity {

    private ArrayList<ProvinceBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();

    private ArrayList<CardBean> cardItem = new ArrayList<>();

    private ArrayList<String> food = new ArrayList<>();
    private ArrayList<String> clothes = new ArrayList<>();
    private ArrayList<String> computer = new ArrayList<>();

    private TimePickerView pvTime;
    private OptionsPickerView pvOptions;

    @BindView(R.id.btn_Time)
    ElasticButton btn_Time;

    @BindView(R.id.btn_Options)
    ElasticButton btn_Options;

    @BindView(R.id.imageLayer1)
    ResizableImageView imageLayer1;

    @BindView(R.id.imageLayer2)
    ResizableImageView imageLayer2;

    @BindView(R.id.pointer)
    ImageView pointer;

    @BindView(R.id.time_point)
    RippleBackground timePoint;

    @BindView(R.id.address_point)
    RippleBackground addressPoint;

    @BindView(R.id.e_frag_layout)
    FrameLayout list;

    private SharedPreferencesUtils tokenShared;

    private Bitmap image2Bitmap;
    private int mInputImageForBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escort);
        ButterKnife.bind(this);
        HideUtil.init(this);
        replaceFragment(new LoadingFragment());
        new RxTimer().timer(5000, number -> {
            if(!this.isDestroyed() && !this.isFinishing()) {
                replaceFragment(new BgFragment());
            }
        });

        tokenShared = SharedPreferencesUtils.init(this,"oauth");
        init();
    }

    public void init(){
        initTimePicker();
        getOptionData();
        initOptionPicker();
        initImageCompare();
    }

    @OnClick(R.id.btn_post)
    public void postForm(View v){
        new ElasticAnimation(v).setScaleX(0.85f).setScaleY(0.85f).setDuration(500)
                .setOnFinishListener(() -> ARouter.getInstance().build("/olife/list")
                        .withInt("action", 3)
                        .withLong("id",-1)
                        .navigation()).doAction();
        finish();
    }

    @OnClick(R.id.btn_Time)
    public void showTimePicker(View v){
        new ElasticAnimation(v).setScaleX(0.85f).setScaleY(0.85f).setDuration(500)
                .setOnFinishListener(() -> pvTime.show()).doAction();
    }

    @OnClick(R.id.btn_Options)
    public void showOptions(View v){
        new ElasticAnimation(v).setScaleX(0.85f).setScaleY(0.85f).setDuration(500)
                .setOnFinishListener(() -> {
                    pvOptions.show();
                }).doAction();
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.e_frag_layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void initTimePicker() {//Dialog 模式下，在底部弹出
        pvTime = new TimePickerBuilder(this, (date, v) -> {
            Toast.makeText(EscortActivity.this, getTime(date), Toast.LENGTH_SHORT).show();
            btn_Time.setText(getTime(date));
            tokenShared.putLong("escort_time", date.getTime());
            timePoint.startRippleAnimation();
        })
                .setTimeSelectChangeListener(date -> Log.i("pvTime", "onTimeSelectChanged"))
                .setType(new boolean[]{true, true, true, true, true, true})
                .isDialog(true) //默认设置false ，内部实现将DecorView 作为它的父控件。
                .addOnCancelClickListener(view -> Log.i("pvTime", "onCancelClickListener"))
                .setItemVisibleCount(5) //若设置偶数，实际值会加1（比如设置6，则最大可见条目为7）
                .setLineSpacingMultiplier(2.0f)
                .isAlphaGradient(true)
                .build();

        Dialog mDialog = pvTime.getDialog();
        if (mDialog != null) {

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

            params.leftMargin = 0;
            params.rightMargin = 0;
            pvTime.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
                dialogWindow.setDimAmount(0.3f);
            }
        }
    }

    private void initOptionPicker() {//条件选择器初始化
        /**
         * 注意 ：如果是三级联动的数据(省市区等)，请参照 JsonDataActivity 类里面的写法。
         */
        pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).getPickerViewText()
                        + options2Items.get(options1).get(options2)
                        /* + options3Items.get(options1).get(options2).get(options3).getPickerViewText()*/;
                btn_Options.setText(tx);
                tokenShared.putString("escort_address", tx);
                addressPoint.startRippleAnimation();
            }
        })
                .setTitleText("城市选择")
                .setContentTextSize(20)//设置滚轮文字大小
                .setDividerColor(Color.LTGRAY)//设置分割线的颜色
                .setSelectOptions(0, 1)//默认选中项
                .setBgColor(Color.BLACK)
                .setTitleBgColor(Color.DKGRAY)
                .setTitleColor(Color.LTGRAY)
                .setCancelColor(Color.YELLOW)
                .setSubmitColor(Color.YELLOW)
                .setTextColorCenter(Color.LTGRAY)
                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setLabels("省", "市", "区")
                .setOutSideColor(0x00000000) //设置外部遮罩颜色
                .setOptionsSelectChangeListener((options1, options2, options3) -> {
                    String str = "options1: " + options1 + "\noptions2: " + options2 + "\noptions3: " + options3;
                    Toast.makeText(EscortActivity.this, str, Toast.LENGTH_SHORT).show();
                })
                .build();

//        pvOptions.setSelectOptions(1,1);
        /*pvOptions.setPicker(options1Items);//一级选择器*/
        pvOptions.setPicker(options1Items, options2Items);//二级选择器
        /*pvOptions.setPicker(options1Items, options2Items,options3Items);//三级选择器*/
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    private void getOptionData() {
        /*
         * 注意：如果是添加JavaBean实体数据，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        //选项1
        options1Items.add(new ProvinceBean(0, "广东", "描述部分", "其他数据"));
        options1Items.add(new ProvinceBean(1, "湖南", "描述部分", "其他数据"));
        options1Items.add(new ProvinceBean(2, "广西", "描述部分", "其他数据"));

        //选项2
        ArrayList<String> options2Items_01 = new ArrayList<>();
        options2Items_01.add("广州");
        options2Items_01.add("佛山");
        options2Items_01.add("深圳");
        options2Items_01.add("东莞");
        options2Items_01.add("珠海");
        options2Items_01.add("阳江");
        ArrayList<String> options2Items_02 = new ArrayList<>();
        options2Items_02.add("长沙");
        options2Items_02.add("岳阳");
        options2Items_02.add("株洲");
        options2Items_02.add("衡阳");
        ArrayList<String> options2Items_03 = new ArrayList<>();
        options2Items_03.add("桂林");
        options2Items_03.add("玉林");
        options2Items.add(options2Items_01);
        options2Items.add(options2Items_02);
        options2Items.add(options2Items_03);
        /*--------数据源添加完毕---------*/
    }

    @SuppressLint("ClickableViewAccessibility")
    public void initImageCompare(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        final int screenWidth = displayMetrics.widthPixels;
        if (mInputImageForBitmap == 0 || mInputImageForBitmap == -1) {
            image2Bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.staff_bg2);
        }
        pointer.setOnTouchListener((v, event) -> {
            int sliderX = (int) event.getRawX();
            if (sliderX > 0 && sliderX < screenWidth - 80) {
                pointer.setX(sliderX);
            }
            int fX = ScaleFunction(sliderX+50, 0, screenWidth, 0, image2Bitmap.getWidth());
            final Bitmap outputBitmap = Bitmap.createBitmap(imageLayer2.getWidth(), imageLayer2.getHeight(), Bitmap.Config.ARGB_8888);
            final Canvas canvas = new Canvas(outputBitmap);
            final Paint paint = new Paint();
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            paint.setStrokeWidth(0);
            final Rect rect = new Rect(fX, 0, image2Bitmap.getWidth(), image2Bitmap.getHeight());
            final double imageWidth = image2Bitmap.getWidth();
            final double imageHeight = image2Bitmap.getHeight();
            final double imageAspect = imageWidth / imageHeight;
            final double finalImageHeight = imageLayer2.getWidth() / imageAspect;
            final double startY = (imageLayer2.getHeight() / 2.0) - (finalImageHeight / 2.0);
            final RectF rectF = new RectF(sliderX+50, (int) startY, imageLayer2.getWidth(), (int) startY + (int) finalImageHeight);
            paint.setAntiAlias(true);
            paint.setColor(Color.RED);
            canvas.drawRect(rectF, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(image2Bitmap, rect, rectF, paint);
            imageLayer2.setImageBitmap((outputBitmap));
            return true;
        });
    }

    public int ScaleFunction(int xCoordinate, double A, double B, double C, double D) {
        double resultData = 0.0;
        double data1 = xCoordinate - A;
        double data2 = B - A;
        if (data2 != 0) {
            resultData = data1 / data2;
        }
        double start = Math.round((C * (1.0 - resultData)));
        double end = D * resultData;
        double finalResult = (start + end);
        return (int) finalResult;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(timePoint.isRippleAnimationRunning() | addressPoint.isRippleAnimationRunning()) {
            timePoint.stopRippleAnimation();
            addressPoint.stopRippleAnimation();
        }
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
