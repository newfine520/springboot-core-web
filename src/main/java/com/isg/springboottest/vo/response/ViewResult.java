package com.isg.springboottest.vo.response;

import lombok.Data;

@Data
public class ViewResult<T> {
    private int code;

    private String msg;
    private T data;


    public static <T> ViewResult<T> build(ViewResultStatus status, T data) {
        return new ViewResult<T>(status.getCode(), status.getDesc(), data);
    }

    public static <T> ViewResult<T> build(ViewResultStatus status, String msg, T data) {
        return new ViewResult<T>(status.getCode(), msg, data);
    }


    public ViewResult(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public enum ViewResultStatus {

        SUCCESS(1000, "成功"),
        FAIL(1002, "未定义的后台错误"),
        SYSTEM_ERROR(1003, "系统错误，无法更新"),
        UPLOAD_FAIL(2001, "上传失败"),
        IO_EXCEPTION(2003, "io异常，上传失败"),
        ALREADY_LOGIN(3001, "用户已登录"),
        USER_NON_EXIST(3002, "用户不存在或者密码错误"),
        USER_DISABLED(3003, "用户被禁用"),
        CHECK_TOKEN_ERROR(3004, "访问token验证出错"),
        REFRESH_TOKEN_ERROR(3005, "Refresh-token验证出错"),
        PARAM_ERROR(4001, "输入参数验证错误"),
        TOO_MUCH_DATA(4013, "查询返回的数据量过大");


        ViewResultStatus(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        private int code;
        private String desc;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

}
