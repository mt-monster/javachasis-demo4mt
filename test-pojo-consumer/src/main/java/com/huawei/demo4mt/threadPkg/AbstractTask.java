package com.huawei.demo4mt.threadPkg;

import org.apache.servicecomb.serviceregistry.task.TaskStatus;

import com.google.common.eventbus.EventBus;

public abstract class AbstractTask implements Runnable {

    protected TaskStatus taskStatus = TaskStatus.INIT;

    protected EventBus eventBus;

    public AbstractTask(EventBus eventBus) {
        this.eventBus = eventBus;
        this.eventBus.register(this);
    }

    @Override
    public void run() {
        if (taskStatus == TaskStatus.READY) {
            doRun();
            eventBus.post(this);
        }

    }

    abstract protected void doRun();
}
