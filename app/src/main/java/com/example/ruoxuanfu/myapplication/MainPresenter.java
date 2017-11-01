package com.example.ruoxuanfu.myapplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by ruoxuan.fu on 2017/11/1.
 * <p>
 * Code is far away from bug with WOW protecting.
 */

public class MainPresenter {

    private static MainPresenter instance;
    private Random rand;

    private List<String> mNameData = new ArrayList<>();
    private List<String> mClassData = new ArrayList<>();
    private List<String> mGradeData = new ArrayList<>();

    public static MainPresenter getInstance() {
        if (instance == null) {
            synchronized (MainPresenter.class) {
                instance = new MainPresenter();
            }
        }
        return instance;
    }

    public MainPresenter() {
        rand = new Random();
    }

    public void initTypeData(int type, List<String> data) {
        switch (type) {
            case MyConfig.NAME_TYPE:
                loadNameData(data);
                break;
            case MyConfig.CLASS_TYPE:
                loadClassData(data);
                break;
            case MyConfig.GRADE_TYPE:
                loadGradeData(data);
                break;
        }
    }

    public void initTypeData(int type, LoadCallBack callBack) {
        switch (type) {
            case MyConfig.NAME_TYPE:
                getNameData(callBack);
                break;
            case MyConfig.CLASS_TYPE:
                getClassData(callBack);
                break;
            case MyConfig.GRADE_TYPE:
                getGradeData(callBack);
                break;
        }
        callBack.LoadSuccessCallBack();
    }

    private void getGradeData(LoadCallBack callBack) {
        int i = rand.nextInt(100) + 30;
        mGradeData.clear();
        for (int i1 = 0; i1 < i; i1++) {
            mGradeData.add("年级 " + i1);
        }
        callBack.LoadSuccessCallBack();
    }

    private void getClassData(LoadCallBack callBack) {
        int i = rand.nextInt(100) + 30;
        mClassData.clear();
        for (int i1 = 0; i1 < i; i1++) {
            mClassData.add("班级 " + i1);
        }
        callBack.LoadSuccessCallBack();
    }

    private void getNameData(LoadCallBack callBack) {
        int i = rand.nextInt(100) + 30;
        mNameData.clear();
        for (int i1 = 0; i1 < i; i1++) {
            mNameData.add("Mick" + i1);
        }
        callBack.LoadSuccessCallBack();
    }

    private void loadNameData(List<String> data) {
        data.clear();
        data.add("全部");
        for (int i = 0; i < mNameData.size(); i++) {
            data.add(mNameData.get(i));
        }
    }

    private void loadGradeData(List<String> data) {
        data.clear();
        data.add("全部");
        for (int i = 0; i < mGradeData.size(); i++) {
            data.add(mGradeData.get(i));
        }
    }

    private void loadClassData(List<String> data) {
        data.clear();
        data.add("全部");
        for (int i = 0; i < mClassData.size(); i++) {
            data.add(mClassData.get(i));
        }
    }

}
