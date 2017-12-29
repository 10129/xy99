package xy99.core.test.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by xieshuai on 2017/12/26.
 */
//@Controller
@RequestMapping("/test")
public class TestController {

    @RequestMapping(value="/query")
    public void test(HttpServletRequest request){

        String AAA="11";
    }


}
