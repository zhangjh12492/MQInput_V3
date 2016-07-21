package com.wfj.mq.utils;

/**
 * Created by MaYong on 2014/12/22.
 */
public class ItgConstent {

    /**
     * add by MaYong
     * 消息流转状态
     */
    public enum TxStep {
        消息存储("TX_STEP-10", "消息存储"),
        到达预定队列("TX_STEP-20", "到达预定队列"),
        从队列已获取("TX_STEP-30", "从队列已获取"),
        调用外部服务("TX_STEP-40", "调用外部服务"),
        调用外部服务返回成功("TX_STEP-41", " 调用外部服务返回成功"),
        调用外部服务返回失败("TX_STEP-42", " 调用外部服务返回失败"),
        调用外部服务异常("TX_STEP-43", " 调用外部服务异常"),
        消息处理完成("TX_STEP-90", "消息处理完成");

        private String code;

        private String value;

        private TxStep() {
        }

        private TxStep(String code, String value) {
            this.code = code;
            this.value = value;
        }

        public String getCode() {
            return this.code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getValue() {
            return this.value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    /**
     * add by MaYong
     * 返回类型
     */
    public enum ResultType {
        失败("0", "失败"),
        成功("1", "成功");

        private String code;

        private String value;

        private ResultType() {
        }

        private ResultType(String code, String value) {
            this.code = code;
            this.value = value;
        }

        public String getCode() {
            return this.code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getValue() {
            return this.value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    /**
     * add by MaYong
     * 报文类型
     */
    public enum MessageType {
        请求报文("0", "请求报文"),
        响应报文("1", "响应报文");

        private String code;

        private String value;

        private MessageType() {
        }

        private MessageType(String code, String value) {
            this.code = code;
            this.value = value;
        }

        public String getCode() {
            return this.code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getValue() {
            return this.value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    /**
     * add by MaYong
     * 差错类型
     */
    public enum ErrorType {
        系统级差错("1", "系统级差错"),
        业务级差错("2", "业务级差错");

        private String code;

        private String value;

        private ErrorType() {
        }

        private ErrorType(String code, String value) {
            this.code = code;
            this.value = value;
        }

        public String getCode() {
            return this.code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getValue() {
            return this.value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    /**
     * add by MaYong
     * 差错处理状态
     */
    public enum ErrorStatus {
        未处理("0", "未处理"),
        处理中("1", "处理中"),
        已中止差错("8", "已中止差错"),
        已处理完毕("9", "已处理完毕");

        private String code;

        private String value;

        private ErrorStatus() {
        }

        private ErrorStatus(String code, String value) {
            this.code = code;
            this.value = value;
        }

        public String getCode() {
            return this.code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getValue() {
            return this.value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    /**
     * add by MaYong
     * 交换类型
     */
    public enum ExchangeType {
        direct(0, "direct"),
        fanout(1, "fanout"),
        topic(2, "topic");

        private int code;

        private String value;

        private ExchangeType() {
        }

        private ExchangeType(int code, String value) {
            this.code = code;
            this.value = value;
        }

        public int getCode() {
            return this.code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getValue() {
            return this.value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    /**
     * add by MaYong
     * 消息状态类型
     */
    public enum TxInfoStatus {
        初始存储(0, "初始存储"),
        发送到队列(1, "发送到队列"),
        从队列取出(2, "从队列取出"),
        发送到接出系统(3, "发送到接出系统");

        private int code;

        private String value;

        private TxInfoStatus() {
        }

        private TxInfoStatus(int code, String value) {
            this.code = code;
            this.value = value;
        }

        public int getCode() {
            return this.code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getValue() {
            return this.value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }


}
