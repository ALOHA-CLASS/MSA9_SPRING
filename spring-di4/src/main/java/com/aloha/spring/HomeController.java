package com.aloha.spring;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.aloha.spring.dao.ReplyDAO;
import com.aloha.spring.service.BoardService;
import com.aloha.spring.service.MyService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	// 의존성 자동 주입
	
	@Autowired
	@Qualifier("AServiceImpl")
	private MyService myServiceA;
	
	@Autowired
	@Qualifier("BServiceImpl")
	private MyService myServiceB;
	
	@Autowired
	@Qualifier("C")
	private MyService myServiceC;
	
	// ---
	@Autowired(required = false)   // 등록된 빈이 없을 때, null 로 가져온다
	private BoardService boardService;
	
	@Autowired
	private ReplyDAO replyDAO;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		String formattedDate = dateFormat.format(date);
		model.addAttribute("serverTime", formattedDate );
		
		myServiceA.test();
		myServiceB.test();
		myServiceC.test();
		
		// ---
		if( boardService != null ) {
			boardService.test();
			boardService.setDAO( replyDAO );
			boardService.test();
		} else {
			System.err.println("빈이 등록되지 않았습니다.");
		}
		
		
		return "home";
	}
	
}



