package com.huawei.demo4mt;

import static org.hamcrest.core.Is.is;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PoJoIT {
  @Before
  public void before() {
    TestMgr.errors().clear();
  }

  @Test
  public void pojoITEntry() throws Exception {
    PojoConsumer.main(new String[0]);
    Assert.assertThat(TestMgr.errors().isEmpty(), is(true));
  }
}
