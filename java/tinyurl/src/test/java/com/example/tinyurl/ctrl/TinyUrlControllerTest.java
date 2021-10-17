package com.example.tinyurl.ctrl;

import com.example.tinyurl.ctrl.data.TinyUrlPair;
import com.example.tinyurl.ctrl.exception.BadRequestException;
import com.example.tinyurl.service.TinyUrlService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * @author jinlian
 */
public class TinyUrlControllerTest {

    @InjectMocks
    private TinyUrlController tinyUrlController;
    @Mock
    private TinyUrlService tinyUrlService;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGenerate() {
        String tinyUrl = "http://tinyurl";
        String longUrl = "http://longurl";
        when(tinyUrlService.genTinyUrl(any())).thenReturn(tinyUrl);
        TinyUrlPair ret = tinyUrlController.generateTinyUrl(longUrl);
        Assert.assertEquals(tinyUrl, ret.getTinyUrl());
        Assert.assertEquals(longUrl, ret.getLongUrl());
    }

    @Test(expected = BadRequestException.class)
    public void testRestoreWithNoExistTinyUrl() {
        String tinyUrl = "http://tinyurl";
        when(tinyUrlService.toLongUrl(tinyUrl)).thenReturn(null);
        tinyUrlController.restore(tinyUrl);
    }

    @Test
    public void testRestore() {
        String tinyUrl = "http://tinyurl";
        String longUrl = "http://longurl";
        when(tinyUrlService.toLongUrl(tinyUrl)).thenReturn(longUrl);
        TinyUrlPair ret = tinyUrlController.restore(tinyUrl);
        Assert.assertEquals(longUrl, ret.getLongUrl());
        Assert.assertEquals(tinyUrl, ret.getTinyUrl());
    }
}
