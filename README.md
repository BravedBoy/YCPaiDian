# 关于库总结大全
- 1.购物车数量加减自定义控件
    * 1.1 功能介绍
    * 1.2 使用说明
    * 1.3 出现bug以及解决思路
- 2.自定义红点控件
    * 2.1 功能介绍
    * 2.2 使用说明
- 3.Loading加载窗
    * 1.1 功能介绍
    * 1.2 使用说明
    * 1.3 出现bug及解决思路
- 4.adapter封装
- 5.弹窗封装库
- 6.banner轮播图
- 7.版本更新说明
    * 7.1 关于库导入
    * 7.2 使用说明
    * 7.3 在代码中安装APK文件
    * 7.4 7.4 清单文件
- 8.状态切换loadLayout
- 9.其他待补充




### 1.购物车数量加减自定义控件
##### 1.1 功能介绍
- 可以限制购物车最小值和最大值
- 当处于最小值的时候，设置不可点击；当处于最大值的时候，设置不可点击
- 最小值或者最大值图片是灰色的，处于最大值和最小值之间是深色的
- 可以设置控件监听listener方法，监听购物车数量是否发生了变化等等
- 购物车数量可以直接输入，也可以设置不可点击

##### 1.2 使用说明

```
AmountView avView = findViewById(R.id.av_view);
//avView.setAmountNum(10);
avView.setAmountNum(2,10,1);
//设置是否可以点击
avView.setEtClickable(false);
int amount = avView.getAmount();
avView.setOnAmountChangeListener(new AmountView.OnAmountChangeListener() {
    @Override
    public void onAmountChange(boolean isChange, boolean isMaxOrMin, int amount) {
        Log.e("change",isChange+"-------"+isMaxOrMin+"----------"+amount);
    }
});
```

##### 1.3 出现bug以及解决思路
- 1.3.1 StackOverflowError: stack size 8MB
    * 在设置editText的addTextChangedListener时，afterTextChanged方法中设置mEtAmount.setText出现该异常
    * 当et控件获取焦点后，当文本频繁更改后，会造成这个问题
    * 解决办法：a.在该方法添加和移除监听;b.设置控件不可点击


### 2.自定义红点控件
##### 2.1 功能介绍
- 支持依附view设置红点，灵活方便
- 当红点大于99时，就显示99+
- 支持设置tabLayout
- 支持设置红点的位置，还有设置上下左右的间距


##### 2.2 使用说明
- 如下所示
```
//创建对象
BadgeView badgeView = new BadgeView(this);
//设置依附的view
badgeView.setTargetView(tv_1);
//设置红点数量
badgeView.setBadgeCount(10);
//设置边距
badgeView.setBadgeMargin(0,0,0,0);
//设置位置
badgeView.setRedHotViewGravity(Gravity.RIGHT);
```

### 3.Loading加载窗
##### 1.1 功能介绍
- 支持全局loading，可以设置loading文字，可以设置自定义布局



##### 1.2 使用说明
- 展示弹窗：LoadDialog.show(this,"加载中");
- 销毁弹窗：LoadDialog.dismiss(this);


##### 1.3 出现bug及解决思路
- 1.3.1 空指针异常，getWindow()空指针异常，可以增加非空判断
- 1.3.2 Unable to add window --  is your activity running?
    * 弹出弹窗或者销毁弹窗，当宿主activity不存在时，会导致这种崩溃
    * 解决办法：增加activity是否running判断

### 7.版本更新说明
- 详细可以见：https://github.com/yangchong211/YCUpdateApp
- 在build.gradle中直接导入：compile 'cn.yc:YCUpdateLib:1.0.1'

#### 7.1 关于库导入
- 在build.gradle中直接导入：compile 'cn.yc:YCUpdateLib:1.0.1'

#### 7.2 使用说明

```
/*
 * @param isForceUpdate             是否强制更新
 * @param desc                      更新文案
 * @param url                       下载链接
 * @param apkFileName               apk下载文件路径名称
 * @param packName                  包名
 */
UpdateFragment updateFragment = new UpdateFragment(false,desc,firstUrl,"apk1",pathName);
//弹出弹窗
updateFragment.show(MainActivity.this , getSupportFragmentManager());
//获取下载文件的路径
String filePath = updateFragment.getFilePath();
//弹窗销毁监听listener
updateFragment.setLoadFinishListener(new BaseDialogFragment.onLoadFinishListener() {
    @Override
    public void listener(boolean isSuccess) {

    }
});
```

#### 7.3 在代码中安装 APK 文件
- 直接调用工具类UpdateUtils.installNormal方法
- 关于在代码中安装 APK 文件，在 Android N 以后，为了安卓系统为了安全考虑，不能直接访问软件，需要使用 fileProvider 机制来访问、打开 APK 文件。里面if 语句，就是区分软件运行平台，来对 intent 设置不同的属性。

```
/**
 * 关于在代码中安装 APK 文件，在 Android N 以后，为了安卓系统为了安全考虑，不能直接访问软件
 * 需要使用 fileProvider 机制来访问、打开 APK 文件。
 * 普通安装
 * @param context                   上下文
 * @param apkPath                   path，文件路径
 * @param pathName                  你的包名
 */
public static void installNormal(Context context, String apkPath , String pathName) {
    if(apkPath==null || pathName==null){
        return;
    }
    Intent intent = new Intent(Intent.ACTION_VIEW);
    File apkFile = new File(apkPath);
    // 由于没有在Activity环境下启动Activity,设置下面的标签
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    //版本在7.0以上是不能直接通过uri访问的
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
        Uri apkUri = FileProvider.getUriForFile(context, pathName+".fileProvider", apkFile);
        //添加这一句表示对目标应用临时授权该Uri所代表的文件
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
    } else {
        Uri uri = Uri.fromFile(apkFile);
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
    }
    context.startActivity(intent);
}
```

#### 7.4 清单文件
- 7.4.1 清单文件添加代码如下所示：

```
<application>
    <provider
        android:name="android.support.v4.content.FileProvider"
        android:authorities="你的包名.fileprovider"
        android:exported="false"
        android:grantUriPermissions="true">
        <meta-data
            android:name="android.support.FILE_PROVIDER_PATHS"
            android:resource="@xml/file_paths" />
    </provider>
</application>    
```

- 7.4.2 在res/xml下增加文件：file_paths.xml

```
<?xml version="1.0" encoding="utf-8"?>
<paths xmlns:android="http://schemas.android.com/apk/res/android">
        <external-path path="." name="external_files"/>
        <external-path path="." name="download"/>
</paths>
```




