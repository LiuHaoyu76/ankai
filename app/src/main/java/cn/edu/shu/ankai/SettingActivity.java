package cn.edu.shu.ankai;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import cn.edu.shu.ankai.widget.GestureFrameLayout;


/**
 *
 * Created by Administrator on 2015/3/1.
 */
public class SettingActivity extends BaseActivity1 implements SharedPreferences.OnSharedPreferenceChangeListener {

    private SettingFragment mSettingFragment;
    private GestureFrameLayout mGestureFrameLayout;  //滑动返回

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐藏状态栏
        //定义全屏参数
        int flag= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //获得当前窗体对象
        Window window=SettingActivity.this.getWindow();
        //设置当前窗体为全屏显示
        window.setFlags(flag, flag);



        setContentView(R.layout.activity_setting);

        mGestureFrameLayout = (GestureFrameLayout) findViewById(R.id.news_content_gesture_layout);
        mGestureFrameLayout.attachToActivity(this);

        mToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_white_18dp));
        setSupportActionBar(mToolbar);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingActivity.this.finish();
            }
        });

        mSettingFragment = new SettingFragment();
        replaceFragment(R.id.setting_content,mSettingFragment);
    }


    /**
     * 保存更改的设置项
     * @param sharedPreferences
     * @param key
     */
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        sharedPreferences.getString(key,"");
    }
}
