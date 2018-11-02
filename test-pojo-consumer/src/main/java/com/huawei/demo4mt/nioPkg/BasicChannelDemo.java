package com.huawei.demo4mt.nioPkg;

import java.io.RandomAccessFile;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

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
public class BasicChannelDemo {

  public static void main(String[] args) throws Exception {

    String path = "data/nio-data.txt";
    URL systemResource = BasicChannelDemo.class.getClassLoader().getResource("");
    System.out.println(systemResource + path);
    RandomAccessFile afile = new RandomAccessFile("test-pojo-consumer/target/classes/data/nio-data.txt", "rw");
    FileChannel channel = afile.getChannel();
    ByteBuffer buf = ByteBuffer.allocate(5);
    int bytesRead = channel.read(buf);
    while (bytesRead != -1) {
      System.out.println("Read" + bytesRead);
      buf.flip();
//      while (buf.hasRemaining()) {
//        System.out.println((char) buf.get());
//
//      }
//      byte b = buf.get();
      byte[] array = buf.array();
      for (int i = 0; i < array.length; i++) {
        System.out.println((char)array[i]);

      }
//      System.out.println((char) b);

//      buf.compact();
      buf.clear();
      bytesRead = channel.read(buf);
      System.out.println("Read xxx" + bytesRead);

    }
    afile.close();
  }

}
