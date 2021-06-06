package com.example.myapplication.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.myapplication.R;
import com.example.myapplication.event.RxTimer;
import com.example.myapplication.router.RoutePath;
import com.schibsted.spain.parallaxlayerlayout.AnimatedTranslationUpdater;
import com.schibsted.spain.parallaxlayerlayout.ParallaxLayerLayout;
import com.schibsted.spain.parallaxlayerlayout.SensorTranslationUpdater;

@Route(path = "/olife/start")
public class StartActivity extends AppCompatActivity {

    private ParallaxLayerLayout parallaxLayout;
    private SensorTranslationUpdater translationUpdater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        parallaxLayout = (ParallaxLayerLayout) findViewById(R.id.parallax);
        RadioGroup updaterGroup = (RadioGroup) findViewById(R.id.updater_group);

        translationUpdater = new SensorTranslationUpdater(this);

        updaterGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.updater_sensor_button) {
                    parallaxLayout.setTranslationUpdater(translationUpdater);
                } else if (checkedId == R.id.updater_auto_button) {
                    System.out.println("auto");
                    parallaxLayout.setTranslationUpdater(new AnimatedTranslationUpdater(0.5f));
                }
            }
        });

        updaterGroup.check(R.id.updater_auto_button);

        // Resets orientation when clicked
        parallaxLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                translationUpdater.reset();
            }
        });

        RxTimer rxTimer = new RxTimer();
        rxTimer.timer(4000, number -> {
            ARouter.getInstance().build(RoutePath.HOME.toString()).navigation();
            finish();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        translationUpdater.registerSensorManager();
    }

    @Override
    protected void onPause() {
        super.onPause();
        translationUpdater.unregisterSensorManager();
    }

}
