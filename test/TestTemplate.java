import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

/**
 * Created by Guus & Wouter on 2015-09-01.
 */
public class TestTemplate
{
    //Deze tests als eerste en tijdens ontwikkeling van de klasse
    //1 : Een test die goed moet gaan
    @Test
    public void test1(){
        assertEquals(1,1);
    }

    //Deze tests als de klasse is gemaakt
    //2 : Een test die exception handeling opvangt

    @Test //(expected = Exception.class)
    public void test2(){
        assertEquals(1,1);
        assertNull(null);
        assertSame(null,null); //test of lijsten worden gekloond of niet
    }
    //3 : Een test die foute input geeft

    //4 : Een test die mogelijke return waardes test

    //5 : Een test die een NULL-reference als input geeft

}
