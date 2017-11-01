package com.example.ruoxuanfu.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTvName;
    private TextView mTvClass;
    private TextView mTvGrade;
    private TextView mListTitle;
    private RecyclerView mListData;

    private List<String> mData;
    private DataAdapter mAdapter;

    private MainPresenter mPresenter;

    private KProgressHUD mProgress;

    private int mNamePosition;
    private int mClassPosition;
    private int mGradePosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initView() {
        mTvName = (TextView) findViewById(R.id.tvName);
        mTvClass = (TextView) findViewById(R.id.tvClass);
        mTvGrade = (TextView) findViewById(R.id.tvGrade);
        mListTitle = (TextView) findViewById(R.id.listTitle);
        mListData = (RecyclerView) findViewById(R.id.listData);

        mTvName.setOnClickListener(this);
        mTvClass.setOnClickListener(this);
        mTvGrade.setOnClickListener(this);
    }


    private void initData() {
        mPresenter = MainPresenter.getInstance();
        mData = new ArrayList<>();
        mProgress = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvName:
                loadDataView(MyConfig.NAME_TYPE);
                break;
            case R.id.tvClass:
                loadDataView(MyConfig.CLASS_TYPE);
                break;
            case R.id.tvGrade:
                loadDataView(MyConfig.GRADE_TYPE);
                break;
        }
    }

    private void loadDataView(final int type) {
        if (mAdapter == null) {
            mAdapter = new DataAdapter(mData);
            mListData.setAdapter(mAdapter);
            mListData.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            mListData.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        }

        mProgress.setLabel("Loading...")
                .setDetailsLabel("Please wait...")
                .show();

        mPresenter.initTypeData(type, new LoadCallBack() {
            @Override
            public void LoadSuccessCallBack() {
                mPresenter.initTypeData(type, mData);

                switch (type) {
                    case MyConfig.NAME_TYPE:
                        mListTitle.setText("选择您的姓名");
                        invalidSelectedItem(mNamePosition);
                        break;
                    case MyConfig.CLASS_TYPE:
                        mListTitle.setText("选择您的班级");
                        invalidSelectedItem(mClassPosition);
                        break;
                    case MyConfig.GRADE_TYPE:
                        mListTitle.setText("选择您的年级");
                        invalidSelectedItem(mGradePosition);
                        break;
                }
                mProgress.dismiss();
            }

            @Override
            public void LoadErrorCallBack() {
                mProgress.dismiss();
                Toast.makeText(MainActivity.this, "筛选数据加载失败", Toast.LENGTH_SHORT).show();
            }
        });

        mAdapter.setOnRecycleViewItemClickListener(new OnRecycleViewItemClickListener() {
            @Override
            public void onItemClickListener(int position) {
                switch (type) {
                    case MyConfig.NAME_TYPE:
                        mNamePosition = position;
                        mTvName.setText(mData.get(mNamePosition));
                        break;
                    case MyConfig.CLASS_TYPE:
                        mClassPosition = position;
                        mTvClass.setText(mData.get(mClassPosition));
                        break;
                    case MyConfig.GRADE_TYPE:
                        mGradePosition = position;
                        mTvGrade.setText(mData.get(mGradePosition));
                        break;
                }
            }
        });
    }

    private void invalidSelectedItem(int position) {
        mAdapter.notifyDataSetChanged();
    }
}
