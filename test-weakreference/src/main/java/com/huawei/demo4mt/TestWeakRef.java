package com.huawei.demo4mt;

import java.lang.ref.WeakReference;

/**
 * 一句话功能简述
 * 功能详细描述
 * @author m00416667
 * @version [版本号, ]
 * @see  [相关类/方法]
 * @since [产品/模块版本]
 * Package Name:com.huawei.demo4mt
 */
public class TestWeakRef {
    public static void main(String[] args) {
        Car redCar = new Car(500, "red");
        WeakReference<Car> carWeakReference = new WeakReference<>(redCar);
        int i = 0;
        while (true) {
            if (carWeakReference.get() != null) {
                i++;
                System.out.println("Object is alive for" + i + "loops-" + carWeakReference);
            }else {
                System.out.println("Object is been collected");
                break;
            }
        }
    }
}
