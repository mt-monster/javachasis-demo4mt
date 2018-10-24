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
public abstract class AbstractListAllDir implements ProcessFilterIntf {

    private static final Logger logger = LoggerFactory.getLogger(AbstractListAllDir.class);

    public List<File> listAllDir(File file, int flag, String nameFilter, ArrayList<File> fileArrayList) {
        String str = ".";
        for (int i = 0; i < flag; i++) {
            str += ".";
        }
        if (file.isFile()) {
            if (file.getName().contains(nameFilter)) {
                logger.debug(file.getAbsolutePath());
                fileArrayList.add(file);
            }
            logger.debug(str + file);
        } else {
            try {
                File[] temp = file.listFiles();
                for (int i = 0; i < temp.length; i++) {
                    if (temp[i].getName().contains(nameFilter)) {
                        logger.debug(temp[i].getAbsolutePath());
                        fileArrayList.add(temp[i]);
                    }
                    if (!temp[i].isFile()) {
                        listAllDir(temp[i], flag + 1, nameFilter, fileArrayList);
                    }
                }
            } catch (NullPointerException e) {
                logger.error("not find replacefile ", e.getMessage());
            }
        }
        return fileArrayList;
    }
}

