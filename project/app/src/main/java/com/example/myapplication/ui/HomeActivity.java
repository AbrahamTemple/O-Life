package com.example.myapplication.ui;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.example.myapplication.view.items.LocalImageHolderView;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Toast;

import com.example.myapplication.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = "/olife/home")
public class HomeActivity extends AppCompatActivity implements OnItemClickListener {

    @BindView(R.id.convenientBanner)
    ConvenientBanner banner;

    private ArrayList<Integer> localImages = new ArrayList<Integer>();
    private List<String> networkImages;
    private String[] images = {"http://img2.imgtn.bdimg.com/it/u=3093785514,1341050958&fm=21&gp=0.jpg",
            "http://img2.3lian.com/2014/f2/37/d/40.jpg",
            "http://d.3987.com/sqmy_131219/001.jpg",
            "http://img2.3lian.com/2014/f2/37/d/39.jpg",
            "http://www.8kmm.com/UploadFiles/2012/8/201208140920132659.jpg",
            "http://f.hiphotos.baidu.com/image/h%3D200/sign=1478eb74d5a20cf45990f9df460b4b0c/d058ccbf6c81800a5422e5fdb43533fa838b4779.jpg",
            "http://f.hiphotos.baidu.com/image/pic/item/09fa513d269759ee50f1971ab6fb43166c22dfba.jpg"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
    }

    private void init() {
//        initImageLoader();
        loadTestDatas();
        //本地图片例子
        banner.setPages(
                new CBViewHolderCreator() {
                    @Override
                    public Holder createHolder(View itemView) {
                        return new LocalImageHolderView(itemView);
                    }

                    @Override
                    public int getLayoutId() {
                        return R.layout.item_localimage;
                    }
                }, localImages)
                .setOnItemClickListener(this);

        //网络加载例子
//        networkImages= Arrays.asList(images);
//        banner.setPages(new CBViewHolderCreator<NetWorkImageHolderView>() {
//            @Override
//            public NetWorkImageHolderView createHolder() {
//                return new NetWorkImageHolderView();
//            }
//        },networkImages);
    }

    private void loadTestDatas() {
        //本地图片集合
        for (int position = 0; position < 7; position++)
            localImages.add(getResId("ic_test_" + position, R.drawable.class));
//        //各种翻页效果
//        transformerList.add(DefaultTransformer.class.getSimpleName());
//        transformerList.add(AccordionTransformer.class.getSimpleName());
//        transformerList.add(BackgroundToForegroundTransformer.class.getSimpleName());
//        transformerList.add(CubeInTransformer.class.getSimpleName());
//        transformerList.add(CubeOutTransformer.class.getSimpleName());
//        transformerList.add(DepthPageTransformer.class.getSimpleName());
//        transformerList.add(FlipHorizontalTransformer.class.getSimpleName());
//        transformerList.add(FlipVerticalTransformer.class.getSimpleName());
//        transformerList.add(ForegroundToBackgroundTransformer.class.getSimpleName());
//        transformerList.add(RotateDownTransformer.class.getSimpleName());
//        transformerList.add(RotateUpTransformer.class.getSimpleName());
//        transformerList.add(StackTransformer.class.getSimpleName());
//        transformerList.add(ZoomInTransformer.class.getSimpleName());
//        transformerList.add(ZoomOutTranformer.class.getSimpleName());

//        transformerArrayAdapter.notifyDataSetChanged();
    }

    public static int getResId(String variableName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(variableName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    // 开始自动翻页
    @Override
    protected void onResume() {
        super.onResume();
//        //开始自动翻页
        banner.startTurning();
    }

    // 停止自动翻页
    @Override
    protected void onPause() {
        super.onPause();
//        //停止翻页
        banner.stopTurning();
    }


    @Override
    public void onItemClick(int position) {
        Toast.makeText(this,"点击了第"+position+"个", Toast.LENGTH_SHORT).show();
    }
}
