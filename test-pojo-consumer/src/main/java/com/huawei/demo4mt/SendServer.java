package com.huawei.demo4mt;

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class SendServer {

  private static int num = 0, cnt = 0;
  String oldPath = "C:/Users/Administrator/Downloads/cse-java-chassis-2.3.35";//src file
  String newPath = "C:/Users/Administrator/Downloads/t1/";//dest file

  public static void main(String[] args) {
    new SendServer().process();

    new SendServer().calTreeNode();
  }

  public void calTreeNode() {
    TreeNode tn = new TreeNode();
    tn.fileName = oldPath;
    readfile(tn);
    System.out.println("\n" + cnt);
  }

  private static TreeNode readfile(TreeNode tn) {
    try {

      File file = new File(tn.fileName);
      tn.text = file.getName();
      if (!file.isDirectory()) {
        //System.out.println(file.getName());

      } else if (file.isDirectory()) {
        String[] filelist = file.list();
        for (int i = 0; i < filelist.length; i++) {
          File readfile = new File(tn.fileName + "\\" + filelist[i]);
          TreeNode tn1 = new TreeNode();
          tn1.text = readfile.getName();
          tn1.fileName = tn.fileName + "\\" + filelist[i];
          if (!readfile.isDirectory() && readfile.getName().contains("jar") && !readfile.getName().contains("source")) {
            cnt++;
            System.out.println(tn1.fileName);
            tn.children.add(tn1);
          } else if (readfile.isDirectory()) {
            tn.children.add(readfile(tn1));
          }
        }
      }

    } catch (Exception e) {
      System.out.println("readfile()   Exception:" + e.getMessage());
    }
    return tn;
  }

  public static class TreeNode {

    /**
     * 节点名称
     **/
    private String text;
    /**
     * 节点路径
     **/
    private String fileName;
    /**
     * 子节点
     **/
    private List<TreeNode> children = new ArrayList<TreeNode>();
  }


  public void process() {

    try {

//      while (true) {
      System.out.println("复制 " + oldPath + " 目录开始");
      long t1 = System.currentTimeMillis();

      num = 0;
      copyFolder(oldPath, newPath);

      long t2 = System.currentTimeMillis();
      System.out.println("复制目录结束，用时：" + (t2 - t1) + "ms,共复制：" + num + "文件");
//      }

    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public void copyFolder(String oldPath, String newPath) {

    try {
      File mFile = new File(newPath);
      if (!mFile.exists()) {
        (new File(newPath)).mkdirs(); // 如果文件夹不存在 则建立新文件夹
      }
      File a = new File(oldPath);
      String[] file = a.list();
      File temp = null;
      for (int i = 0; i < file.length; i++) {
        if (oldPath.endsWith(File.separator)) {
          temp = new File(oldPath + file[i]);
        } else {
          temp = new File(oldPath + File.separator + file[i]);
//          temp = new File(oldPath);
        }

        if (temp.isFile() && temp.getName().contains("jar") && !temp.getName().contains("sources")) {
          String fileName = newPath + "/" + (temp.getName());
          File testFile = new File(fileName);
          if (!testFile.exists()) {
            FileInputStream input = new FileInputStream(temp);
            FileOutputStream output = new FileOutputStream(fileName);
            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = input.read(b)) != -1) {
              output.write(b, 0, len);
            }
            output.flush();
            output.close();
            input.close();
            if (testFile.getName().contains("jar")) {
              System.out.println(testFile.getName() + "\n");
              num++;
            }
          }
        }
        if (temp.isDirectory()) {// 如果是子文件夹
//          copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
          copyFolder(oldPath + "/" + file[i], newPath);
//          continue;
        }
      }
    } catch (Exception e) {
      System.out.println("复制整个文件夹内容操作出错");
      e.printStackTrace();

    }
  }

  private String getTimeString(String time) {
    if (time.length() < 2) {
      return "0" + time;
    } else {
      return time;
    }
  }
}
