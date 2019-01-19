package com.huawei.demo4mt.NioPakage;

import java.util.Date;

/**
 * 一句话功能简述
 * 功能详细描述
 * @author m00416667
 * @version [版本号, ]
 * @see  [相关类/方法]
 * @since [产品/模块版本]
 * Package Name:com.huawei.demo4mt.NioPakage
 */
public class Wang implements CallbackIntf {

    private Meng meng;

    public Wang(Meng meng) {
        this.meng = meng;
    }

    @Override public void calTime(long time) {
//        SimpleDateFormat sdf= new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        java.util.Date dt = new Date(time * 1000);
//        String s = sdf.format(dt);
        System.out.println("Meng tell Wang ,The time costs " + dt);
    }
    public void askTime(final String name){
        System.out.println("meng pls tell me cost Time is what ...");
        new Thread(new Runnable() {
            @Override public void run() {
                meng.answerTime(Wang.this,name);
            }
        }).start();
        System.out.println("ask over, i go to read....Zzz");

    }
}
