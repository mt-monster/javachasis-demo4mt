package com.huawei.test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 一句话功能简述
 * 功能详细描述
 * @author m00416667
 * @version [版本号, ]
 * @see  [相关类/方法]
 * @since [产品/模块版本]
 * Package Name:com.huawei.test
 */
public class appYmlFilter extends AbstractListAllDir {

    private static final Logger logger = LoggerFactory.getLogger(appYmlFilter.class);

    @Override public List<File> process(File file, int flag, ArrayList<File> fileArrayList) {
        return listAllDir(file, flag, Constants.findFileName[1], fileArrayList);
    }
}
