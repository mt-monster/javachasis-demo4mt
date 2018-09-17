package com.huawei.demo4mt;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

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
public class FileCopyHelper {

  public static void main(String[] args) throws Exception {
    String url1 = "C:/Users/m00416667/Downloads/cse-java-chassis-2.3.39.1RC";//src file
    String url2 = "C:/Users/m00416667/Downloads/copy/";//dest file
    File file2 = new File(url2);
    file2.mkdir();
    File file1 = new File(url1);
    File[] files = file1.listFiles();
    for (File file : files) {
      if (file.isFile() && file.getName().contains("jar")) {
        String type = file.getName().substring(file.getName().lastIndexOf(".") + 1);
        copyFile(file, new File(url2 + file.getName()));
      }
      if (file.isDirectory()) {
        String sourceDir = url1 + File.separator + file.getName();
        String targetDir = url2 + File.separator + file.getName();
        copyDirectory(sourceDir, targetDir);
      }
    }

  }

  private static void copyDirectory(String sourceDir, String targetDir) throws IOException {

    (new File(targetDir)).mkdirs();

    File[] file = (new File(sourceDir)).listFiles();
    for (int i = 0; i < file.length; i++) {
      if (file[i].isFile()) {

        File sourceFile = file[i];

        File targetFile = new File(new File(targetDir).getAbsolutePath() + File.separator + file[i].getName());
        copyFile(sourceFile, targetFile);//递归调用
      }
      if (file[i].isDirectory()) {
        String dir1 = sourceDir + "/" + file[i].getName();

        String dir2 = targetDir + "/" + file[i].getName();
        copyDirectory(dir1, dir2);
      }
    }
  }

  // 复制文件
  public static void copyFile(File sourceFile, File targetFile) throws IOException {
    BufferedInputStream inBuff = null;
    BufferedOutputStream outBuff = null;
    try {

      inBuff = new BufferedInputStream(new FileInputStream(sourceFile));

      outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));

      byte[] b = new byte[1024 * 5];
      int len;
      while ((len = inBuff.read(b)) != -1) {
        outBuff.write(b, 0, len);
      }

      outBuff.flush();
    } finally {

      if (inBuff != null) {
        inBuff.close();
      }
      if (outBuff != null) {
        outBuff.close();
      }
    }
  }

}
