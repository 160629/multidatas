package com.example.demo.controller;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.service.AsyncService;

import io.swagger.annotations.Api;
@Api(tags = "多线程测试")
@RestController
public class ThreadController  {
 
    private static final Logger logger = LoggerFactory.getLogger(ThreadController.class);
 
    @Autowired
    private AsyncService asyncService;
 
    @GetMapping("/executeAsync")
    public String executeAsync(){
 
        //调用service层的任务
        asyncService.executeAsync();
        asyncService.executeAsync();
        asyncService.executeAsync();
        asyncService.executeAsync();
 
        return "OK";
    }
 
 
}
