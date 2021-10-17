package com.example.tinyurl.storage;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author jinlian
 */
public class TinyUrlStorageTest {

    private TinyUrlStorage tinyUrlStorage = new TinyUrlStorage();
    @Test
    public void testPut() {
        String longUrl = "http://longurl";
        String tinyUrl = "http://shortUrl";
        tinyUrlStorage.add(tinyUrl, longUrl);
        Assert.assertEquals(tinyUrl, tinyUrlStorage.getTinyByLong(longUrl));
        Assert.assertEquals(longUrl, tinyUrlStorage.getLongByTiny(tinyUrl));
    }

    @Test
    public void testMapEvict() {
        tinyUrlStorage.setMaxSize(1);
        String longurl1 = "http://longurl1";
        String tinyurl1 = "http://tinyurl1";
        tinyUrlStorage.add(tinyurl1, longurl1);
        Assert.assertEquals(tinyUrlStorage.getLongByTiny(tinyurl1), longurl1);
        String tinyurl2 = "http://tinyurl2";
        String longurl2 = "http://longurl2";
        tinyUrlStorage.add(tinyurl2, longurl2);
        Assert.assertNull(tinyUrlStorage.getLongByTiny(tinyurl1));
        Assert.assertEquals(longurl2, tinyUrlStorage.getLongByTiny(tinyurl2));

    }
}
