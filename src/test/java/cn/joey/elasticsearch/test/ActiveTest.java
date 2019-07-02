package cn.joey.elasticsearch.test;

import cn.joey.elasticsearch.utils.CommonUtils;
import cn.joey.elasticsearch.constant.DefaultConstant;

/**
 * @author dunhanson
 * @version 1.0
 * @date 2019/5/18
 * @description
 */
public class ActiveTest {
    public static void main(String[] args) {
        System.out.println(System.getenv(DefaultConstant.ENV_PROFILES_ACTIVE));
        System.out.println(CommonUtils.getConfigFileName());
    }
}
