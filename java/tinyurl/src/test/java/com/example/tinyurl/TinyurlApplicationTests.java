package com.example.tinyurl;

import com.example.tinyurl.ctrl.TinyUrlController;
import com.example.tinyurl.ctrl.data.TinyUrlPair;
import com.example.tinyurl.service.TinyUrlService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author jinlian
 */
@SpringBootTest
class TinyurlApplicationTests {

	@Autowired
	private TinyUrlController tinyUrlController;

	@Test
	public void testGenerate() {
		String longurl = "https://longurl";
		String tinyurl = TinyUrlService.BASE_URL + "b";
		TinyUrlPair ret = tinyUrlController.generateTinyUrl(longurl);
		Assert.assertEquals(ret.getLongUrl(), longurl);
		Assert.assertEquals(ret.getTinyUrl(), tinyurl);
	}

}
