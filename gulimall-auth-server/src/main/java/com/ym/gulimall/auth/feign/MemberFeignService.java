package com.ym.gulimall.auth.feign;

import com.ym.common.utils.R;
import com.ym.gulimall.auth.vo.UserLoginVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("gulimall-member")
public interface MemberFeignService {

    @PostMapping("/member/member/login")
    R login(@RequestBody UserLoginVo vo);
}
