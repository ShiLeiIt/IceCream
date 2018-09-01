package com.smart.qiushi.icecream.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.smart.qiushi.icecream.R;
import com.smart.qiushi.icecream.adpter.LeftMenuAdapter;
import com.smart.qiushi.icecream.adpter.PopupFoodAdapter;
import com.smart.qiushi.icecream.adpter.RightFoodAdapter;
import com.smart.qiushi.icecream.imp.ShopCartImp;
import com.smart.qiushi.icecream.model.Food;
import com.smart.qiushi.icecream.model.FoodMenu;
import com.smart.qiushi.icecream.model.ShopCart;
import com.smart.qiushi.icecream.view.FakeAddImageView;
import com.smart.qiushi.icecream.view.PointFTypeEvaluator;
import com.smart.qiushi.icecream.view.ShopCartDialog;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by shilei on 2018/8/24
 * 购买冰淇淋页面
 */

public class BuyActivity extends AppCompatActivity implements LeftMenuAdapter.onItemSelectedListener, ShopCartImp, ShopCartDialog.ShopCartDialogImp {
    private final static String TAG = "BuyActivity";
    @Bind(R.id.rl_buy_layout)
    LinearLayout mRlBuyLayout;
    private RecyclerView leftMenu;//左侧菜单栏
    private RecyclerView rightMenu;//右侧菜单栏
    private TextView headerView;
    private LinearLayout headerLayout;//右侧菜单栏最上面的菜单
    private FoodMenu headMenu;
    private LeftMenuAdapter leftAdapter;
    private RightFoodAdapter rightAdapter;
    private ArrayList<FoodMenu> mFoodMenuList;//数据源
    private boolean leftClickType = false;//左侧菜单点击引发的右侧联动
    private ShopCart shopCart;
    //    private FakeAddImageView fakeAddImageView;
    private ImageView shoppingCartView;
    private FrameLayout shopingCartLayout;
    private TextView totalPriceTextView;
    private TextView totalPriceNumTextView;
    private ImageView mIvCommitOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
        ButterKnife.bind(this);

        initData();
        initView();
        initAdapter();
    }

    private void initView() {
        leftMenu = (RecyclerView) findViewById(R.id.left_menu);
        rightMenu = (RecyclerView) findViewById(R.id.right_menu);
        headerView = (TextView) findViewById(R.id.right_menu_tv);
        headerLayout = (LinearLayout) findViewById(R.id.right_menu_item);
//        fakeAddImageView = (FakeAddImageView)findViewById(R.id.right_dish_fake_add);
        shoppingCartView = (ImageView) findViewById(R.id.shopping_cart);//购物车图标
        shopingCartLayout = (FrameLayout) findViewById(R.id.shopping_cart_layout);//购物车帧布局
        totalPriceTextView = (TextView) findViewById(R.id.shopping_cart_total_tv);//购物车总的价格
        totalPriceNumTextView = (TextView) findViewById(R.id.shopping_cart_total_num);//右上角添加个数
        mIvCommitOrder = (ImageView) findViewById(R.id.iv_commit);//提交订单按钮

        leftMenu.setLayoutManager(new LinearLayoutManager(this));
        rightMenu.setLayoutManager(new LinearLayoutManager(this));

        rightMenu.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (recyclerView.canScrollVertically(1) == false) {//无法下滑
                    showHeadView();
                    return;
                }
                View underView = null;
                if (dy > 0)
                    underView = rightMenu.findChildViewUnder(headerLayout.getX(), headerLayout.getMeasuredHeight() + 1);
                else
                    underView = rightMenu.findChildViewUnder(headerLayout.getX(), 0);
                if (underView != null && underView.getContentDescription() != null) {
                    int position = Integer.parseInt(underView.getContentDescription().toString());
                    FoodMenu menu = rightAdapter.getMenuOfMenuByPosition(position);

                    if (leftClickType || !menu.getMenuName().equals(headMenu.getMenuName())) {
                        if (dy > 0 && headerLayout.getTranslationY() <= 1 && headerLayout.getTranslationY() >= -1 * headerLayout.getMeasuredHeight() * 4 / 5 && !leftClickType) {// underView.getTop()>9
                            int dealtY = underView.getTop() - headerLayout.getMeasuredHeight();
                            headerLayout.setTranslationY(dealtY);
//                            Log.e(TAG, "onScrolled: "+headerLayout.getTranslationY()+"   "+headerLayout.getBottom()+"  -  "+headerLayout.getMeasuredHeight() );
                        } else if (dy < 0 && headerLayout.getTranslationY() <= 0 && !leftClickType) {
                            headerView.setText(menu.getMenuName());
                            int dealtY = underView.getBottom() - headerLayout.getMeasuredHeight();
                            headerLayout.setTranslationY(dealtY);
//                            Log.e(TAG, "onScrolled: "+headerLayout.getTranslationY()+"   "+headerLayout.getBottom()+"  -  "+headerLayout.getMeasuredHeight() );
                        } else {
                            headerLayout.setTranslationY(0);
                            headMenu = menu;
                            headerView.setText(headMenu.getMenuName());
                            for (int i = 0; i < mFoodMenuList.size(); i++) {
                                if (mFoodMenuList.get(i) == headMenu) {
                                    leftAdapter.setSelectedNum(i);
                                    break;
                                }
                            }
                            if (leftClickType) leftClickType = false;
                            Log.e(TAG, "onScrolled: " + menu.getMenuName());
                        }
                    }
                }
            }
        });

        shopingCartLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCart(view);
            }
        });
    }

    private void initData() {
        shopCart = new ShopCart();
        mFoodMenuList = new ArrayList<>();
        ArrayList<Food> dishs1 = new ArrayList<>();
        dishs1.add(new Food("夏季清爽", 15.0, 15));
        dishs1.add(new Food("夏季凉爽", 15.0, 15));
        dishs1.add(new Food("夏季之歌", 15.0, 15));
        dishs1.add(new Food("夏季靓丽", 15.0, 15));
        dishs1.add(new Food("夏季裙摆", 15.0, 15));
        dishs1.add(new Food("夏季摇摆", 15.0, 15));
        dishs1.add(new Food("夏季动人", 15.0, 15));
        FoodMenu breakfast = new FoodMenu("当季主打", dishs1);

        ArrayList<Food> dishs2 = new ArrayList<>();
        dishs2.add(new Food("一爽到底", 16.0, 10));
        dishs2.add(new Food("爽到翻", 16.0, 10));
        dishs2.add(new Food("冰之翼", 1.0, 10));
        dishs2.add(new Food("凉之心", 1.0, 10));
        dishs2.add(new Food("行之甜", 1.0, 10));
        FoodMenu launch = new FoodMenu("猜你喜欢", dishs2);

        ArrayList<Food> dishs3 = new ArrayList<>();
        dishs3.add(new Food("格罗林根", 16.0, 10));
        dishs3.add(new Food("格罗林根", 16.0, 10));
        dishs3.add(new Food("格罗林根", 16.0, 10));
        dishs3.add(new Food("格罗林根", 16.0, 10));
        dishs3.add(new Food("格罗林根", 16.0, 10));
        dishs3.add(new Food("格罗林根", 16.0, 10));
        FoodMenu evening = new FoodMenu("格罗林", dishs3);

        ArrayList<Food> dishs4 = new ArrayList<>();
        dishs4.add(new Food("郁金香", 18.0, 10));
        dishs4.add(new Food("郁金香", 18.0, 10));
        dishs4.add(new Food("郁金香", 18.0, 10));
        dishs4.add(new Food("郁金香", 18.0, 10));
        dishs4.add(new Food("郁金香", 18.0, 10));
        dishs4.add(new Food("郁金香", 18.0, 10));
        FoodMenu menu1 = new FoodMenu("郁金香", dishs3);

        mFoodMenuList.add(breakfast);
        mFoodMenuList.add(launch);
        mFoodMenuList.add(evening);
        mFoodMenuList.add(menu1);
    }

    private void initAdapter() {
        leftAdapter = new LeftMenuAdapter(this, mFoodMenuList);
        rightAdapter = new RightFoodAdapter(this, mFoodMenuList, shopCart);
        rightMenu.setAdapter(rightAdapter);
        leftMenu.setAdapter(leftAdapter);
        leftAdapter.addItemSelectedListener(this);
        rightAdapter.setShopCartImp(this);
        initHeadView();
    }

    private void initHeadView() {
        headMenu = rightAdapter.getMenuOfMenuByPosition(0);
        headerLayout.setContentDescription("0");
        headerView.setText(headMenu.getMenuName());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        leftAdapter.removeItemSelectedListener(this);
    }

    private void showHeadView() {
        headerLayout.setTranslationY(0);
        View underView = rightMenu.findChildViewUnder(headerView.getX(), 0);
        if (underView != null && underView.getContentDescription() != null) {
            int position = Integer.parseInt(underView.getContentDescription().toString());
            FoodMenu menu = rightAdapter.getMenuOfMenuByPosition(position + 1);
            headMenu = menu;
            headerView.setText(headMenu.getMenuName());
            for (int i = 0; i < mFoodMenuList.size(); i++) {
                if (mFoodMenuList.get(i) == headMenu) {
                    leftAdapter.setSelectedNum(i);
                    break;
                }
            }
        }
    }

    @Override
    public void onLeftItemSelected(int position, FoodMenu menu) {
        int sum = 0;
        for (int i = 0; i < position; i++) {
            sum += mFoodMenuList.get(i).getFoodList().size() + 1;
        }
        LinearLayoutManager layoutManager = (LinearLayoutManager) rightMenu.getLayoutManager();
        layoutManager.scrollToPositionWithOffset(sum, 0);
        leftClickType = true;
    }

    @Override
    public void add(View view, int position) {
        int[] addLocation = new int[2];
        int[] cartLocation = new int[2];
        int[] recycleLocation = new int[2];
        view.getLocationInWindow(addLocation);
        shoppingCartView.getLocationInWindow(cartLocation);
        rightMenu.getLocationInWindow(recycleLocation);

        PointF startP = new PointF();
        PointF endP = new PointF();
        PointF controlP = new PointF();

        startP.x = addLocation[0];
        startP.y = addLocation[1] - recycleLocation[1];
        endP.x = cartLocation[0];
        endP.y = cartLocation[1] - recycleLocation[1];
        controlP.x = endP.x;
        controlP.y = startP.y;

        final FakeAddImageView fakeAddImageView = new FakeAddImageView(this);
        mRlBuyLayout.addView(fakeAddImageView);
        fakeAddImageView.setImageResource(R.drawable.buy_add_circle);
        fakeAddImageView.getLayoutParams().width = getResources().getDimensionPixelSize(R.dimen.dp_16);
        fakeAddImageView.getLayoutParams().height = getResources().getDimensionPixelSize(R.dimen.dp_16);
        fakeAddImageView.setVisibility(View.VISIBLE);
        ObjectAnimator addAnimator = ObjectAnimator.ofObject(fakeAddImageView, "mPointF",
                new PointFTypeEvaluator(controlP), startP, endP);
        addAnimator.setInterpolator(new AccelerateInterpolator());
        addAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                fakeAddImageView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                fakeAddImageView.setVisibility(View.GONE);
                mRlBuyLayout.removeView(fakeAddImageView);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        ObjectAnimator scaleAnimatorX = new ObjectAnimator().ofFloat(shoppingCartView, "scaleX", 0.6f, 1.0f);
        ObjectAnimator scaleAnimatorY = new ObjectAnimator().ofFloat(shoppingCartView, "scaleY", 0.6f, 1.0f);
        scaleAnimatorX.setInterpolator(new AccelerateInterpolator());
        scaleAnimatorY.setInterpolator(new AccelerateInterpolator());
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(scaleAnimatorX).with(scaleAnimatorY).after(addAnimator);
        animatorSet.setDuration(300);
        animatorSet.start();

        showTotalPrice();
    }

    @Override
    public void remove(View view, int position) {
        showTotalPrice();
    }

    private void showTotalPrice() {
        if (shopCart != null && shopCart.getShoppingTotalPrice() > 0) {
            totalPriceTextView.setVisibility(View.VISIBLE);
            totalPriceTextView.setText("￥ " + shopCart.getShoppingTotalPrice());
            totalPriceNumTextView.setVisibility(View.VISIBLE);
            totalPriceNumTextView.setText("" + shopCart.getShoppingAccount());
            mIvCommitOrder.setVisibility(View.VISIBLE);
            mIvCommitOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //跳转提交订单页面
                    Intent intent = new Intent(BuyActivity.this, BuyCommitOrderActivity.class);
                    intent.putExtra("shopCart", shopCart);
                    startActivityForResult(intent, 0x000);
                }
            });

        } else {
            totalPriceTextView.setVisibility(View.GONE);
            totalPriceNumTextView.setVisibility(View.GONE);
            mIvCommitOrder.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) switch (requestCode) {
            case 0x000:
                ShopCart shopCart1 = (ShopCart) data.getSerializableExtra("shopcart");
                if (!shopCart1.equals(shopCart)) {
                    if (shopCart1 != null && shopCart1.getShoppingTotalPrice() > 0) {
                        totalPriceTextView.setVisibility(View.VISIBLE);
                        totalPriceTextView.setText("￥ " + shopCart1.getShoppingTotalPrice());
                        totalPriceNumTextView.setVisibility(View.VISIBLE);
                        totalPriceNumTextView.setText("" + shopCart1.getShoppingAccount());
                    }

                }
                break;
        }
    }


    private void showCart(View view) {
        if (shopCart != null && shopCart.getShoppingAccount() > 0) {
            ShopCartDialog dialog = new ShopCartDialog(this, shopCart, R.style.cartdialog);
            Window window = dialog.getWindow();
            dialog.setShopCartDialogImp(this);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(true);
            dialog.show();
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            params.gravity = Gravity.BOTTOM;
            params.dimAmount = 0.5f;
            window.setAttributes(params);
        }
    }

    @Override
    public void dialogDismiss() {
        showTotalPrice();
        rightAdapter.notifyDataSetChanged();
    }
}
