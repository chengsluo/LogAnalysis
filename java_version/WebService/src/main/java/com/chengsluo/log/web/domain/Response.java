package com.chengsluo.log.web.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Response {
    public Integer code;
    public String msg;
    public Object data;

    public static Response ok(Object data) {
        return new Response(200, "success", data);
    }
}