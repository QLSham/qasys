package com.ps.qa.controller;

import com.ps.qa.domain.ResultVO;
import com.ps.qa.member.service.MemberService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:成员controller
 * @author:QL
 * @create：2019/07/18
 */
@RestController
public class MemberController {

    @Reference(version = "1.0.0")
    private MemberService memberService;

    /**
     * 查询用户
     *
     * @return
     */
    @GetMapping("/queryMember")
    public ResultVO queryMember() {
        return memberService.query();
    }

    /**
     * 注册
     *
     * @param username
     * @param password
     * @return
     */
    @GetMapping("/register")
    public ResultVO register(@RequestParam("username") String username, @RequestParam("password") String password) {
        return memberService.register(username, password);
    }

    @GetMapping("/query")
    public ResultVO query(){
        return memberService.querySort();
    }
}
