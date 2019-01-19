package com.huawei.demo4mt;

import java.io.IOException;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

/**
 * 一句话功能简述
 * 功能详细描述
 * @author m00416667
 * @version [版本号, ]
 * @see  [相关类/方法]
 * @since [产品/模块版本]
 * Package Name:com.huawei.demo4mt
 */
public class Test {
  public static void main(String[] args) throws Exception {

    updateMethod();
  }

  private static void updateMethod() {
    try {
      ClassPool cPool = new ClassPool(true);
      //如果该文件引入了其它类，需要利用类似如下方式声明
      //cPool.importPackage("java.util.List");

      //设置class文件的位置
      cPool.insertClassPath("C:\\Users\\m00416667\\Desktop\\netty");

      //获取该class对象
      CtClass cClass1 = cPool.get("sun.nio.ch.SocketChannelImpl");

      //获取到对应的方法
      CtMethod cMethod = cClass1.getDeclaredMethod("read");
      CtClass etype = ClassPool.getDefault().get("java.io.IOException");

      cMethod.addCatch("{ System.out.println($e); throw $e; }", etype);

      //更改该方法的内部实现
      //需要注意的是对于参数的引用要以$开始，不能直接输入参数名称
//      cMethod.setBody("{ return $1*$1*$1+$2*$2*$2; }");

      //替换原有的文件
      cClass1.writeFile("C:\\Users\\m00416667\\Desktop\\netty");



      //设置class文件的位置
      cPool.insertClassPath("C:\\Users\\m00416667\\Desktop\\netty");

      //获取该class对象
      CtClass cClass2 = cPool.get("sun.nio.ch.SocketChannelImpl");

      //获取到对应的方法
      CtMethod cMethod2 = cClass2.getDeclaredMethod("read");
      cMethod2.setModifiers(1);
      CtClass etype2 = ClassPool.getDefault().get("java.io.IOException");

      cMethod2.addCatch("{ System.out.println($e); throw $e; }", etype2);

      //更改该方法的内部实现
      //需要注意的是对于参数的引用要以$开始，不能直接输入参数名称
//      cMethod.setBody("{ return $1*$1*$1+$2*$2*$2; }");

      //替换原有的文件
      cClass2.writeFile("C:\\Users\\m00416667\\Desktop\\netty");


      System.out.println("=======修改方法完=========");
    } catch (NotFoundException e) {
      e.printStackTrace();
    } catch (CannotCompileException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

