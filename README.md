# 关于库总结大全
- 1.购物车数量加减自定义控件
    * 1.1 功能介绍
    * 1.2 使用说明
    * 1.3 出现bug以及解决思路
- 2.自定义红点控件
- 3.Loading加载窗
    * 1.1 功能介绍
    * 1.2 使用说明
    * 1.3 出现bug及解决思路
- 4.adapter封装
- 5.弹窗封装库



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


### 3.Loading加载窗
##### 1.1 功能介绍


##### 1.2 使用说明
- 展示弹窗：LoadDialog.show(this,"加载中");
- 销毁弹窗：LoadDialog.dismiss(this);


##### 1.3 出现bug及解决思路
- 1.3.1 空指针异常，getWindow()空指针异常，可以增加非空判断
- 1.3.2 Unable to add window --  is your activity running?
    * 弹出弹窗或者销毁弹窗，当宿主activity不存在时，会导致这种崩溃
    * 解决办法：增加activity是否running判断

