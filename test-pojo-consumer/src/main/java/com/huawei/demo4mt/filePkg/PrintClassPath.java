package com.huawei.demo4mt.filePkg;

import java.net.URL;
import java.net.URLClassLoader;

public class PrintClassPath {
  public static void main(String[] args) {
    int i = 0, j = 0,k=0;
    ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
    URL[] urLs = ((URLClassLoader) systemClassLoader).getURLs();
    for (URL urL : urLs) {
      if (urL.getFile().contains("jdk")) {

        System.out.println(urL.getFile() + "[" + ++i + "]");
      }
      else if (urL.getFile().contains("target"))
        System.out.println(urL.getFile() + "[" + ++j + "]");
      else {
        System.out.println(urL.getFile() + "[" + ++k + "]");
      }
    }
  }
}
