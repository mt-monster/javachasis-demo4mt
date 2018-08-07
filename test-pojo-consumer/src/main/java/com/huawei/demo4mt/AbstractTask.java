package com.huawei.demo4mt;

import com.google.common.eventbus.EventBus;
import org.apache.servicecomb.serviceregistry.task.TaskStatus;

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
