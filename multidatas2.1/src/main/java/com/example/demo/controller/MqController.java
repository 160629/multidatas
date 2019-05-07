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
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.service.FileService;
import com.example.demo.util.ExcelSheetCell;
import com.example.demo.util.ExcelSheetDataTable;
import com.example.demo.util.ExcelSheetLocation;
import com.example.demo.util.ExcelUtils;

import io.swagger.annotations.Api;
@Api(tags = "消息队列操作")
@RestController
public class MqController {
	private final static Logger logger = LoggerFactory.getLogger(MqController.class);
	@Autowired
    private RabbitTemplate rabbitTemplate;
	
	//直接模式
	@GetMapping("directSend")
    public String directSend(){
        // 多个消费者只有一个消费者接收到消息
        rabbitTemplate.convertAndSend("hello","直接模式测试");
 
        // 多个接收者  均匀的分布到消费者中
        for (int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend("hello","直接模式测试 ：" + i);
        }
        
        return "发送成功";
    }
	
	//分列模式
	@GetMapping("fanoutSend")
    public String fanoutSend(){
		rabbitTemplate.convertAndSend("fanoutExchange","", "分列模式发送的消息");
        return "发送成功";
    }
	
	//订阅模式
	@GetMapping("topicSend")
	public String topicSend(){
		rabbitTemplate.convertAndSend("topicExchange","topic.ron","主题模式1");
		rabbitTemplate.convertAndSend("topicExchange","topic.coolron","主题模式2");
		rabbitTemplate.convertAndSend("topicExchange","topic.cool.ron","主题模式3");		
		return "发送成功";
	}

}
