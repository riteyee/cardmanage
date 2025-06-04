package com.xdd.init.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(prefix = "common")
public class CommonConfig {

    private static String profile;

    public static String getProfile() {
        return profile;
    }

    public  void setProfile(String profile) {
        CommonConfig.profile = profile;
    }

    /**
     * 获取头像上传路径
     */
    public static String getAvatarPath() {
        return profile + "/avatar";
    }

    public static String getCardPath() {
        return profile + "/card";
    }
}
