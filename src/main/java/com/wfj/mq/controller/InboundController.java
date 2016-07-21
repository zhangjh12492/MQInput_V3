package com.wfj.mq.controller;

import com.wfj.mq.dto.ItgCallbackDto;
import com.wfj.mq.dto.MessageDto;
import com.wfj.mq.service.ItgInputService;
import com.wfj.utils.JsonUtils;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by MaYong on 2014/12/12.
 */
@Controller
@RequestMapping("itgService")
public class InboundController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource(name = "itgInputService")
    private ItgInputService itgInputService;

    public final byte[] readBytes(InputStream is, int contentLen) {
        if (contentLen > 0) {
            int readLen = 0;
            int readLengthThisTime = 0;
            byte[] message = new byte[contentLen];
            try {
                while (readLen != contentLen) {
                    readLengthThisTime = is.read(message, readLen, contentLen - readLen);
                    if (readLengthThisTime == -1) {// Should not happen.
                        break;
                    }
                    readLen += readLengthThisTime;
                }
                return message;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new byte[]{};
    }

    //http://127.0.0.1:8080/ITGInput/itgService/jsonInbound.do
    @RequestMapping(value = "/inbound", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ItgCallbackDto jsonInbound(HttpServletRequest request) {

        String message = null;
        BufferedReader br = null;
        try {
            request.setCharacterEncoding("UTF-8");
            br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream()));
            String line = null;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            message = sb.toString();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        ItgCallbackDto itgCallbackDto = null;
        try {
            itgCallbackDto = itgInputService.input(message);
        } catch (Exception e) {
            logger.error("=== 调用接入服务出错" + e.getMessage() + " ===");
            itgCallbackDto = new ItgCallbackDto();
        }
        return itgCallbackDto;
    }

    @RequestMapping(value = "/jsonTest")
    @ResponseBody
    public ItgCallbackDto jsonTest(HttpServletRequest request) {
        ItgCallbackDto itgCallbackDto = new ItgCallbackDto();
        itgCallbackDto.setRespStatus("1");
        return itgCallbackDto;
    }
}
