package com.huawei.demo4mt.nioPkg;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

/**
 * 一句话功能简述
 * 功能详细描述
 * @author m00416667
 * @version [版本号, ]
 * @see  [相关类/方法]
 * @since [产品/模块版本]
 * Package Name:com.huawei.demo4mt.nioPkg
 */
public final class Calculator {
   public static final ScriptEngine jse= new ScriptEngineManager().getEngineByName("JavaScript");
   public static Object cal(String expression) throws Exception{
       return jse.eval(expression);
   }
}
