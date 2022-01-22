package com.jfeat.am.power.base.ut;

import com.jfeat.am.power.base.naming.UniversalName;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by vincent on 2017/11/30.
 */
public class PowerBusinessExceptionTest {
    @Test
    public void testUniversalName(){
        String name = new UniversalName("Alice", "Lee").toString();
        String chineseName = new UniversalName("德全", "李").toString();
        String middleName = new UniversalName("Alice","Z", "Lee").toString();

        Assert.assertTrue(name.compareTo("Alice Lee")==0);
        Assert.assertTrue(chineseName.compareTo("李德全")==0);
        Assert.assertTrue(middleName.compareTo("Alice Lee Z")==0);
    }
}
