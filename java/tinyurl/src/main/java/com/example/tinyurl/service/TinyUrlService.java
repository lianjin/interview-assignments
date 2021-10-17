package com.example.tinyurl.service;

import com.example.tinyurl.ctrl.exception.BadRequestException;
import com.example.tinyurl.storage.TinyUrlStorage;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author jinlian
 */
@Service
public class TinyUrlService {

    public final static String BASE_URL = "https://t.cn/";//测试
    private final static String CHAR_SET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final static int CHAR_SET_SIZE = CHAR_SET.length();
    private AtomicLong id = new AtomicLong();
    private Object lock = new Object();

    @Autowired
    private TinyUrlStorage tinyUrlStorage;

    private String numberBase10to52(long num) {
        StringBuffer result = new StringBuffer();
        while (num > 0) {
            result.append(CHAR_SET.charAt((int) (num % CHAR_SET_SIZE)));
            num = num / CHAR_SET_SIZE;
        }
        return result.reverse().toString();
    }

    public String toLongUrl(String tinyUrl) {
        String tinyCode = subtractTinyCode(tinyUrl);
        return tinyUrlStorage.getLongByTiny(tinyCode);
    }

    public String genTinyUrl(String longUrl) {
        String tinyCode = tinyUrlStorage.getTinyByLong(longUrl);
        if(Strings.isNullOrEmpty(tinyCode)) {
            synchronized (lock) {
                tinyCode = tinyUrlStorage.getTinyByLong(longUrl);
                if(Strings.isNullOrEmpty(tinyCode)) {
                    long id = nextId();
                    tinyCode = numberBase10to52(id);
                    tinyUrlStorage.add(tinyCode, longUrl);
                }
            }
        }
        return tinyUrl(tinyCode);
    }

    private String tinyUrl(String code) {
        return String.format("%s%s", BASE_URL, code);
    }

    private String subtractTinyCode(String tinyUrl) {
        if (tinyUrl.startsWith(BASE_URL)) {
            return tinyUrl.substring(BASE_URL.length());
        } else {
            throw new BadRequestException("Illegal tinyUrl");
        }
    }

    private long nextId() {
        return id.incrementAndGet();
    }
}
