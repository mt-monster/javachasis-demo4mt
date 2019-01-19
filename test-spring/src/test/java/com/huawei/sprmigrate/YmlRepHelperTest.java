package com.huawei.sprmigrate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.hamcrest.core.AnyOf;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.yaml.snakeyaml.Yaml;

class YmlRepHelperTest {

    private YmlRepHelper helper ;
    @Mock
    private File file;

    @BeforeEach
    void setUp() {
        helper = new YmlRepHelper();
        MockitoAnnotations.initMocks(this);
        when(file.getAbsolutePath()).thenReturn("sss.html");
    }

    @Test
    void yaml2Properties() {
        Map<String, Object> properties = helper.yaml2Properties(file.getAbsolutePath());
        assertNotNull(properties);
    }

    @Test
    void addProperties2Yaml() {
        Map<String, Object> map = new TreeMap<>();
        helper.addProperties2Yaml(file.getAbsolutePath(),"xxxx",map);
        Assert.assertTrue(((Map) map.get("xxxx")).containsKey("ribbon"));
    }

    @Test
    void addCseProp2Yaml() {
        Map<String, Object> map = new TreeMap<>();

        helper.addCseProp2Yaml(file.getAbsolutePath(),map);

    }

    @Test
    void buildCusYaml() {
        Yaml yaml = helper.buildCusYaml();
        assertNotNull(yaml);
    }
}