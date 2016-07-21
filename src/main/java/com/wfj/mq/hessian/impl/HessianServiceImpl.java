package com.wfj.mq.hessian.impl;

import com.wfj.mq.dto.HessianDto;
import com.wfj.mq.dto.ItgCallbackDto;
import com.wfj.mq.hessian.HessianService;
import com.wfj.mq.service.ItgInputService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by MaYong on 2014/12/4.
 */
@Component("hessianService")
public class HessianServiceImpl implements HessianService {
    @Resource(name = "itgInputService")
    private ItgInputService itgInputService;

    @Override
    public ItgCallbackDto inbound(HessianDto hessianDto) {
        ItgCallbackDto callbackDto = new ItgCallbackDto();
        try {
            callbackDto = itgInputService.input(hessianDto.getBizData());
        } catch (Exception e) {
            e.printStackTrace();
            callbackDto.setRespStatus("0");
            callbackDto.setBizDesc(e.getMessage());
        }
        return callbackDto;
    }
}
