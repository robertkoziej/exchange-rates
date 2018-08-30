package com.robertkoziej.exchangerates.list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.robertkoziej.exchangerates.R;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = RecyclerViewAdapter.class.getSimpleName();

    private List<RecyclerItem> dataList;

    private final OnItemClickListener listener;
    private OnLoadMoreListener onLoadMoreListener;

    public interface OnItemClickListener {
        void onItemClick(RecyclerItem item);
    }

    public RecyclerViewAdapter(RecyclerView recyclerView, List<RecyclerItem> dataList, OnItemClickListener listener) {
        this.dataList = dataList;
        this.listener = listener;

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if ((newState == RecyclerView.SCROLL_STATE_IDLE)
                        && (!recyclerView.canScrollVertically(1))
                        && (onLoadMoreListener != null)) {
                    onLoadMoreListener.onLoadMore();
                }
            }
        });
    }

    class RateViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        private TextView code;
        private TextView value;

        RateViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            code = mView.findViewById(R.id.code);
            value = mView.findViewById(R.id.value);
        }

        public void bind(final RateItem item, final OnItemClickListener listener) {
            code.setText(item.getCode());
            value.setText(Double.toString(item.getValue()));
            itemView.setTag(item.getCode());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

    class DateViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        private TextView date;

        DateViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            date = mView.findViewById(R.id.date);
        }

        public void bind(final DateItem item) {
            date.setText(item.getDate());
        }
    }

    class LoadMoreViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        private ProgressBar loadIndicator;

        LoadMoreViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            loadIndicator = mView.findViewById(R.id.loadIndicator);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if (viewType == ItemViewType.RATE_VIEW) {
            View view = layoutInflater.inflate(R.layout.recyclerview_item_preview, parent, false);
            view.setClickable(true);
            view.setFocusable(true);
            view.setFocusableInTouchMode(true);
            return new RateViewHolder(view);
        }
        if (viewType == ItemViewType.DATE_VIEW) {
            View view = layoutInflater.inflate(R.layout.recyclerview_item_date, parent, false);
            return new DateViewHolder(view);
        }
        if (viewType == ItemViewType.LOAD_INDICATOR_VIEW) {
            View view = layoutInflater.inflate(R.layout.recyclerview_item_load_indicator, parent, false);
            return new LoadMoreViewHolder(view);
        }
        View view = layoutInflater.inflate(R.layout.recyclerview_item_preview, parent, false);
        view.setClickable(true);
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        return new RateViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return dataList.get(position).getViewType();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == ItemViewType.RATE_VIEW) {
            RateViewHolder rateViewHolder = (RateViewHolder) holder;
            rateViewHolder.bind((RateItem) dataList.get(position), listener);
        } else if (holder.getItemViewType() == ItemViewType.DATE_VIEW) {
            DateViewHolder dateViewHolder = (DateViewHolder) holder;
            dateViewHolder.bind((DateItem) dataList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void setDataList(List<RecyclerItem> dataList) {
        this.dataList = dataList;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

}