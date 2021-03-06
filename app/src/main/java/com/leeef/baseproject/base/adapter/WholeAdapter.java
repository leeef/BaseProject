package com.leeef.baseproject.base.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;


import com.leeef.baseproject.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.LayoutRes;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/**
 * 加载更多适配器 可添加头布局
 */

public abstract class WholeAdapter<T> extends BaseListAdapter<T> {
    private static final String TAG = "WholeAdapter";
    private static final int TYPE_ITEM = 0;
    public static final int TYPE_HEAD = -1;
    public static final int TYPE_FOOT = -2;

    //刷新类
    private LoadMoreDelegate mLoadDelegate = null;

    public final ArrayList<ItemView> mHeaderList = new ArrayList<>();
    private final ArrayList<ItemView> mFooterList = new ArrayList<>();


    public WholeAdapter(Context context) {
        mLoadDelegate = new LoadMoreDelegate(context, new Options());
        mFooterList.add(mLoadDelegate);
//        addFooterView(mLoadDelegate);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType != TYPE_HEAD && viewType != TYPE_FOOT) {
            return super.onCreateViewHolder(parent, viewType);
        } else {
            return createOtherViewHolder(parent, viewType);
        }
    }

    private RecyclerView.ViewHolder createOtherViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        for (int i = 0; i < mHeaderList.size(); ++i) {
            WholeAdapter.ItemView itemView = mHeaderList.get(i);
            if (viewType == TYPE_HEAD) {
                view = itemView.onCreateView(parent);
            }
        }
        for (int i = 0; i < mFooterList.size(); ++i) {
            WholeAdapter.ItemView itemView = mFooterList.get(i);
            if (viewType == TYPE_FOOT) {
                view = itemView.onCreateView(parent);
            }
        }
        RecyclerView.ViewHolder viewHolder = new RecyclerView.ViewHolder(view) {
        };
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position < mHeaderList.size()) {
            mHeaderList.get(position).onBindView(holder.itemView);
        } else if (position < mHeaderList.size() + getItemSize()) {
            super.onBindViewHolder(holder, position - mHeaderList.size());
        } else {
            int pos = position - mHeaderList.size() - getItemSize();
            mFooterList.get(pos).onBindView(holder.itemView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        int type = 0;
        if (position < mHeaderList.size()) {
            type = TYPE_HEAD;
        } else if (position < mHeaderList.size() + getItemSize()) {
            type = position;
        } else {
            type = TYPE_FOOT;
        }
        return type;
    }

    @Override
    public final int getItemCount() {
        return mHeaderList.size() + getItemSize() + mFooterList.size();
    }

    public void addHeaderView(ItemView itemView) {
        mHeaderList.add(itemView);
    }

    public void addFooterView(ItemView itemView) {
        if (mLoadDelegate != null) {
            int count = mFooterList.size() - 1;
            mFooterList.add(count, itemView);
        } else {
            mFooterList.add(itemView);
        }
    }

    @Override
    public void addItems(List<T> values) {
        if (values.size() == 0 && mLoadDelegate != null) {
            mLoadDelegate.setLoadMoreStatus(LoadMoreView.TYPE_NO_MORE);
        }
        super.addItems(values);
    }

    //用于StaggeredGridLayoutManager header footer 占据整行
    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        if (layoutParams != null && layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) layoutParams;
            int position = holder.getLayoutPosition();
            if (getItemViewType(position) == TYPE_FOOT) {
                params.setFullSpan(false);
            }
        }
    }

    @Override
    public void refreshItems(List<T> list) {
        if (mLoadDelegate != null) {
            mLoadDelegate.setLoadMoreStatus(LoadMoreView.TYPE_LOAD_MORE);
        }
        super.refreshItems(list);
    }

    public void showLoadError() {
        mLoadDelegate.setLoadMoreStatus(LoadMoreView.TYPE_LOAD_ERROR);
    }

    public void showLoadMore() {
        mLoadDelegate.setLoadMoreStatus(LoadMoreView.TYPE_LOAD_MORE);
    }

    public void showNoMore() {
        mLoadDelegate.setLoadMoreStatus(LoadMoreView.TYPE_NO_MORE);
    }

    public void hide() {
        mLoadDelegate.setLoadMoreStatus(LoadMoreView.TYPE_HIDE);
    }


    public static class Options {
        @LayoutRes
        public int loadMoreId = R.layout.base_view_load_more;
        @LayoutRes
        public int errorId = R.layout.base_view_error;
        @LayoutRes
        public int noMoreId = R.layout.base_view_nomore;
    }

    public interface ItemView {
        View onCreateView(ViewGroup parent);

        void onBindView(View view);
    }
}
