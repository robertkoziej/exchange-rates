package com.robertkoziej.exchangerates.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.robertkoziej.exchangerates.R;
import com.robertkoziej.exchangerates.fragments.views.ListFragmentView;
import com.robertkoziej.exchangerates.list.OnLoadMoreListener;
import com.robertkoziej.exchangerates.list.RecyclerItem;
import com.robertkoziej.exchangerates.list.RecyclerViewAdapter;
import com.robertkoziej.exchangerates.presenters.ListFragmentPresenter;
import com.robertkoziej.exchangerates.tools.ErrorDialog;
import com.robertkoziej.exchangerates.tools.RxBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListFragment extends Fragment implements ListFragmentView {

    public static final String TAG = ListFragment.class.getSimpleName();

    private RecyclerViewAdapter adapter;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    AlertDialog alertDialog;

    private ListFragmentPresenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ListFragmentPresenter(getActivity(), this);
        presenter.onCreate();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.onActivityCreated();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void setView(final List<RecyclerItem> dataList) {
        if (adapter == null) {
            adapter = new RecyclerViewAdapter(recyclerView, dataList, new RecyclerViewAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(RecyclerItem rateItem) {
                    RxBus.publish(rateItem);
                }
            });
            final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);

            adapter.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    RxBus.publish(ListFragmentPresenter.LOAD_NEXT_COMMAND);
                }
            });
        }
    }

    @Override
    public void updateView(List<RecyclerItem> dataList) {
        adapter.setDataList(dataList);
        adapter.notifyDataSetChanged();
        recyclerView.scrollToPosition(dataList.size() - 1);
    }

    @Override
    public void showErrorDialog(int textId) {
        alertDialog = ErrorDialog.getErrorDialog(getActivity(), textId);
        alertDialog.show();
    }

    @Override
    public void dismissErrorDialog() {
        alertDialog.dismiss();
    }
}
