package com.wfj.mq.utils;

public class ItgConst {

	public static final int ITG_MESSAGE_MAX_LENGTH=2048000;
	public static final int ITG_MQ_MESSAGE_TIMEOUT_THRESHOLD=172800000; //两天
	public static final int ITG_MQ_MAX_AMOUNT_THRESHOLD=50000;
	public static final String ITG_MESSAGE_HEADER="ITG_MESSAGE_HEADER";
	public static final String ITG_MESSAGE_VALID_STATUS_FAILDED="0";
	public static final String ITG_MESSAGE_VALID_STATUS_SUCCESSED="1";
	
	public static final String ITG_MQ_MAX_AMOUNT_THRESHOLD_LENGTH="关联队列长度超长";
	public static final String ITG_MQ_MAX_AMOUNT_THRESHOLD_LENGTH_ERR_CODE="ITG-SYS-001";
	
	public static final String ITG_MESSAGE_ELEMENT_NAME_LENGTH="报文长度检查";
	public static final String ITG_MESSAGE_ELEMENT_NAME_LENGTH_ERR_CODE="ITG-SYS-010";
	public static final String ITG_MESSAGE_ELEMENT_NAME_VALIDATE_ERR_CODE="ITG-SYS-001";
	public static final String ITG_MESSAGE_ELEMENT_NAME_LENGTH_OVERMAX="报文长度超过2M";
	public static final String ITG_MESSAGE_LEGEL_ERR_DESC="非法报文";
	public static final String ITG_MESSAGE_LEGEL_ERR_CODE="ITG-SYS-001";
	public static final String ITG_MESSAGE_AUTHORIZATION_ERR_CODE="ITG-SYS-010";
	public static final String ITG_MESSAGE_AUTHORIZATION_ERR_DESC="报文授权不不通过，无相关配置";
	public static final String ITG_MESSAGE_AUTHENTICATION_ERR_CODE="ITG-SYS-011";
}
