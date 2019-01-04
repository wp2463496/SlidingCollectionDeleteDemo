package com.forty7.slidingmenu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
/**
 * 2019.1.4 Forty'7
 * xiaowangboke@vip.qq.com
 */
public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MyAdapter myAdapter;
    private List<MyBean> mData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recyclerView);
        init();
    }

    private void init() {
        for (int i = 0; i < 20; i++) {
            mData.add(new MyBean("Forty'" + i));
        }
        myAdapter = new MyAdapter(mData, this);
        myAdapter.setOnClickListener(listener);
        mRecyclerView.setAdapter(myAdapter);
        ((SimpleItemAnimator)mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);//解决默认动画导致的闪屏问题
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                myAdapter.setScrollingMenu(null);
            }
        });
    }

    MyAdapter.OnClickListener listener = new MyAdapter.OnClickListener() {
        @Override
        public void onMenuCollectionClick(int position) {
            myAdapter.closeOpenMenu();
            mData.get(position).setCollection(!mData.get(position).isCollection());
            myAdapter.notifyItemChanged(position);//单行刷新 优化视觉效果
        }

        @Override
        public void onContentClick(int position) {
            myAdapter.closeOpenMenu();
            Toast.makeText(MainActivity.this, "click = " + mData.get(position).getTitle(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onMenuDeleteClick(int position) {
            myAdapter.closeOpenMenu();
            Toast.makeText(MainActivity.this, "delete = " + mData.get(position).getTitle(), Toast.LENGTH_SHORT).show();
            //解决删除错了及视图闪动问题 优化视觉效果
            mData.remove(position);
            myAdapter.notifyItemRemoved(position);
            myAdapter.notifyItemRangeChanged(position, myAdapter.getItemCount());
        }
    };

}
