package com.example.tinyurl.ctrl;

import com.example.tinyurl.ctrl.data.TinyUrlPair;
import com.example.tinyurl.ctrl.exception.BadRequestException;
import com.example.tinyurl.service.TinyUrlService;
import com.google.common.base.Strings;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jinlian
 */
@Api("短链接接口")
@RestController
@RequestMapping("tiny-urls")
public class TinyUrlController {

    @Autowired
    private TinyUrlService tinyUrlService;

    @ApiOperation("生成短链接")
    @PostMapping("/create")
    public TinyUrlPair generateTinyUrl(@RequestParam("long-url") String longUrl) {
        return new TinyUrlPair(tinyUrlService.genTinyUrl(longUrl), longUrl);
    }
    @ApiOperation("还原长链接")
    @ApiResponses({
            @ApiResponse(code = 400, message = "非法短链接:不存在 或 已过期 或 不合法")
    })
    @GetMapping("/restore")
    public TinyUrlPair restore(@RequestParam("tiny-url") String tinyUrl) {
        String ret = tinyUrlService.toLongUrl(tinyUrl);
        if(Strings.isNullOrEmpty(ret)) {
            throw new BadRequestException();
        }
        return new TinyUrlPair(tinyUrl, ret);
    }

}
