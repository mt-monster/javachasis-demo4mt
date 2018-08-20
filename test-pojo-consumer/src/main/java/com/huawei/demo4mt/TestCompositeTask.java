package com.huawei.demo4mt;

import java.util.ArrayList;
import java.util.List;

public class TestCompositeTask implements Runnable {

    private List<Runnable> tasklist = new ArrayList();

    public void addTask(Runnable r){
        tasklist.add(r);
    }

    @Override
    public void run() {
        for (Runnable rList:tasklist) {
            rList.run();
        }
    }
}
