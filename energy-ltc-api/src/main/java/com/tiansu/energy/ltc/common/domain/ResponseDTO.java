package com.tiansu.energy.ltc.common.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统一回复结构体
 * @author yuwen
 */
@Data
@NoArgsConstructor
public class ResponseDTO<T> {
    private Integer code;

    private String message;

    private T data;

    public ResponseDTO(ResultCodeEnum resultCode) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
    }

    public ResponseDTO(ResultCodeEnum resultCode, T data) {
        this(resultCode);
        this.data = data;
    }


    public static <T> ResponseDTO<T> success() {
        return new ResponseDTO(ResultCodeEnum.SUCCESS);

    }

    public static <T> ResponseDTO<T> success(T data) {
        return new ResponseDTO(ResultCodeEnum.SUCCESS,data);
    }

    public static <T> ResponseDTO<T> failure(ResultCodeEnum resultCode) {
        return new ResponseDTO(resultCode, null);

    }

    public static <T> ResponseDTO<T> failure(Integer code, String message) {
        ResponseDTO result = new ResponseDTO();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    public static <T> ResponseDTO<T> failure(ResultCodeEnum resultCode, T data) {
        return new ResponseDTO(resultCode, data);

    }

}
