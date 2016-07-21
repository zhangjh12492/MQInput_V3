package com.wfj.mq.hessian;


import com.wfj.mq.dto.HessianDto;
import com.wfj.mq.dto.ItgCallbackDto;

/**
 * Created by MaYong on 2014/12/4.
 */
public interface HessianService {

    public ItgCallbackDto inbound(HessianDto hessianDto);
}
