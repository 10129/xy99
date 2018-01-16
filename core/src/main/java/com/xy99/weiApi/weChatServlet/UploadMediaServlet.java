package com.xy99.weiApi.weChatServlet;

/**
 * Created by xieshuai on 2018/1/16.
 */
import com.alibaba.fastjson.JSONObject;
import com.xy99.weiApi.util.UploadMediaApiUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
/**
 * 上传素材servlet
 */
@WebServlet(name = "UploadMediaServlet")
public class UploadMediaServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
    } @
            Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        UploadMediaApiUtil uploadMediaApiUtil = new UploadMediaApiUtil();
        String appId = "wxd135f4cb0d7346bf";
        String appSecret = "7e55c0ed3b6e16d4ea36c7365f410d88";
        String accessToken = uploadMediaApiUtil.getAccessToken(appId,appSecret);
        String filePath = "F:\\1237.png";
        File file = new File(filePath);
        String type = "IMAGE";
        JSONObject jsonObject = uploadMediaApiUtil.uploadMedia(file,accessToken,type);
        System.out.println(jsonObject.toString());
    }
}
