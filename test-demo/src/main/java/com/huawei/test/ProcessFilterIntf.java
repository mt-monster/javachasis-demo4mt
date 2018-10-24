package com.huawei.test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public interface ProcessFilterIntf {

    List<File> process(File file, int flag, ArrayList<File> fileArrayList);

}