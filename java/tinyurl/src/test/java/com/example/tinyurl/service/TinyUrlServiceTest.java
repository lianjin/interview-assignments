package com.example.tinyurl.service;

import com.example.tinyurl.ctrl.exception.BadRequestException;
import com.example.tinyurl.storage.TinyUrlStorage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author jinlian
 */
public class TinyUrlServiceTest {

    @InjectMocks
    private TinyUrlService tinyUrlService;
    @Mock
    private TinyUrlStorage tinyUrlStorage;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testToLongUrl() {
        String tinyUrl = TinyUrlService.BASE_URL + "abc";
        String longUrl = "http://longUrl";
        when(tinyUrlStorage.getLongByTiny("abc")).thenReturn(longUrl);
        String ret = tinyUrlService.toLongUrl(tinyUrl);
        Assert.assertEquals(ret, longUrl);
    }

    @Test(expected = BadRequestException.class)
    public void testToLongUrlWithIllegalLongUrl() {
        String tinyUrl = "abc";
        tinyUrlService.toLongUrl(tinyUrl);
    }

    @Test
    public void testGenTinyUrlAlreadyExist() {
        String longUrl = "http://longUrl";
        String tinyUrl = TinyUrlService.BASE_URL + "abc";
        when(tinyUrlStorage.getTinyByLong(longUrl)).thenReturn("abc");
        String ret = tinyUrlService.genTinyUrl(longUrl);
        Assert.assertEquals(ret, tinyUrl);
        verify(tinyUrlStorage, never()).add(anyString(), anyString());
    }

    @Test
    public void testGenTinyUrl() {
        String longUrl = "http://longUrl";
        when(tinyUrlStorage.getTinyByLong(longUrl)).thenReturn(null);
        String ret = tinyUrlService.genTinyUrl(longUrl);
        String expectedRet = TinyUrlService.BASE_URL + "b";
        Assert.assertEquals(expectedRet, ret);
        //double check
        verify(tinyUrlStorage, times(2)).getTinyByLong(longUrl);
        verify(tinyUrlStorage, times(1)).add("b", longUrl);
    }
}
