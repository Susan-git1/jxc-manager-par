package com.lzj.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 乐字节  踏实教育 用心服务
 *扫描持久层的接口
 * @author 乐字节--老李
 * @version 1.0
 */
@SpringBootApplication
@MapperScan("com.lzj.admin.mapper")
public class JxcAdminApplication {

    public static void main(String[] args) {

        SpringApplication.run(JxcAdminApplication.class,args);
   //输出密文例子
//        System.out.println(new BCryptPasswordEncoder().encode("123456"));
    }
}
