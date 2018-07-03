package com.example.webfluxclient.utils.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 服务器信息
 *
 * @author Lucifer
 * @date 2018／07／03 20:39
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServerInfo {

    private String url;

}
