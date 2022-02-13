package com.zjq.community.dto;

import lombok.Data;

/**
 * @date 2022-01-14 16:43
 */
@Data
public class AccessTokenDTO {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
}
