package cn.edu.shu.ankai;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.ViewConfiguration;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVUser;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;


/**
 *基础Activity
 */
public class BaseActivity1 extends ActionBarActivity {

    protected Toolbar mToolbar;
    SystemBarTintManager mTintManager;


    public BaseActivity1 activity;
    private String userId;

    protected final int CURRENT_VERSION = Build.VERSION.SDK_INT;
    protected final int VERSION_KITKAT = Build.VERSION_CODES.KITKAT;
    protected final int VERSION_LOLLIPOP = Build.VERSION_CODES.LOLLIPOP;
//    protected NewsItemBiz mNewsItemBiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        mNewsItemBiz = new NewsItemBiz(this);

        activity = this;
        userId = null;
        AVUser currentUser = AVUser.getCurrentUser();
        if (currentUser != null) {
            userId = currentUser.getObjectId();
        }


  /*
        //使用tintManager设置状态栏的颜色
        mTintManager= new SystemBarTintManager(this);
        // enable status bar tint
        //mTintManager.setStatusBarTintEnabled(true);
       if (isNavBarTransparent()) {
            mTintManager.setStatusBarTintEnabled(true);
            // 有虚拟按键时
            if (isHasNavigationBar()) {
                mTintManager.setNavigationBarTintEnabled(true);
            }else{
                mTintManager.setNavigationBarTintEnabled(false);
            }
        }
        // set a custom tint color for all system bars
        mTintManager.setTintColor(getResources().getColor(R.color.dark_primary_color));

*/


//        SystemBarTintManager.SystemBarConfig config = tintManager.getConfig();
    }






    public String getUserId() {
        return userId;
    }

    protected void showError(String errorMessage) {
        showError(errorMessage, activity);
    }

    public void showError(String errorMessage, Activity activity) {
        new AlertDialog.Builder(activity)
                .setTitle(
                        activity.getResources().getString(
                                R.string.dialog_message_title))
                .setMessage(errorMessage)
                .setNegativeButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.dismiss();
                            }
                        }).show();
    }








    @Override
    protected void onResume() {
        super.onResume();
        AVAnalytics.onResume(this);

    }


    @Override
    protected void onPause() {
        super.onPause();
        AVAnalytics.onPause(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }





    /**
     * 获得当前系统版本号
     * @return
     */
   protected int getVersionNumber(){
       return Build.VERSION.SDK_INT;
   }

    protected static int dp2px(float dpValue){
        return (int)(dpValue * Resources.getSystem().getDisplayMetrics().density);
    }

    protected static int px2dp(float pxValue){
        return (int)(pxValue / Resources.getSystem().getDisplayMetrics().density);
    }

    protected int getScreenWidth(){
        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    protected int getScreenHeight(){
        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }





    /**
     * 检查是否有虚拟按键
     * @return
     */
    protected boolean isHasNavigationBar(){
        //通过是否有物理按键来确定是否有虚拟按键
        boolean hasMenuKey = ViewConfiguration.get(this).hasPermanentMenuKey();
        boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
        return !(hasBackKey && hasMenuKey);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    protected void replaceFragment(int viewId, android.app.Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(viewId, fragment).commit();
    }

    protected int getActionBarSize() {
        TypedValue typedValue = new TypedValue();
        int[] textSizeAttr = new int[]{R.attr.actionBarSize};
        int indexOfAttrTextSize = 0;
        TypedArray a = obtainStyledAttributes(typedValue.data, textSizeAttr);
        int actionBarSize = a.getDimensionPixelSize(indexOfAttrTextSize, -1);
        a.recycle();
        return actionBarSize;
    }

    /**
     * 分享
     */
    protected void showShare(Context context,String text) {
        ShareSDK.initSDK(this);

        String appHomePage = getString(R.string.app_home_page);
        String shareText = text != "" ? text : "\n分享自实验室安全平台-上海大学版本："+appHomePage;

        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字

   //            oks.setNotification(R.drawable.profile, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(getString(R.string.share));
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(appHomePage);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(shareText);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath(getPackageResourcePath()+"/drawable/ic_suesnews.png");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(appHomePage);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        //oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(appHomePage);

        // 启动分享GUI
        oks.show(this);
    }

}
