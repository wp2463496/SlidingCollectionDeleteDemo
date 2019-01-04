package com.forty7.slidingmenu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * 2019.1.4 Forty'7
 * xiaowangboke@vip.qq.com
 */
class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<MyBean> mData;
    private Context mContext;
    private MySlidingMenu mOpenMenu;
    private MySlidingMenu mScrollingMenu;

    MySlidingMenu getScrollingMenu() {
        return mScrollingMenu;
    }

    void setScrollingMenu(MySlidingMenu scrollingMenu) {
        mScrollingMenu = scrollingMenu;
    }

    void holdOpenMenu(MySlidingMenu slidingMenu) {
        mOpenMenu = slidingMenu;
    }

    public void closeOpenMenu() {
        if (mOpenMenu != null && mOpenMenu.isOpen()) {
            mOpenMenu.closeMenu();
            mOpenMenu = null;
        }
    }

    MyAdapter(List<MyBean> data, Context context) {
        mData = data;
        mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item, parent, false));
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final MyBean info = mData.get(position);
        holder.tvTitle.setText(info.getTitle());
        if (info.isCollection()) {
            holder.menuCollection.setText("取消收藏");
            holder.content.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorCollectionItem));
        } else {
            holder.menuCollection.setText("收藏");
            holder.content.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorItem));
        }
        holder.menuCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickListener != null) {
                    mOnClickListener.onMenuCollectionClick(position);
                }
            }
        });
        holder.menuDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickListener != null) {
                    mOnClickListener.onMenuDeleteClick(position);
                }
            }
        });
        holder.slidingMenu.setCustomOnClickListener(new MySlidingMenu.CustomOnClickListener() {
            @Override
            public void onClick() {
                if (mOnClickListener != null) {
                    mOnClickListener.onContentClick(position);
                }
            }
        });

    }

    interface OnClickListener {
        void onMenuCollectionClick(int position);
        void onContentClick(int position);
        void onMenuDeleteClick(int position);
    }

    private OnClickListener mOnClickListener;

    void setOnClickListener(OnClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView menuCollection;
        TextView menuDelete;
        TextView tvTitle;
        ImageView imageView;
        LinearLayout content;
        MySlidingMenu slidingMenu;

        MyViewHolder(View itemView) {
            super(itemView);
            menuDelete = itemView.findViewById(R.id.menuDelete);
            menuCollection = itemView.findViewById(R.id.menuCollection);
            tvTitle =  itemView.findViewById(R.id.tvTitle);
            imageView =  itemView.findViewById(R.id.imageView);
            content = itemView.findViewById(R.id.content);
            slidingMenu =  itemView.findViewById(R.id.slidingMenu);
        }
    }

}
