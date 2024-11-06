package com.aloha.spring.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller		// Controller 로 지정하고 빈 등록
@RequestMapping("/request")
public class RequestController {
	// log 객체 
	private static final Logger logger = LoggerFactory.getLogger(RequestController.class);
	
	/**
	 * @RequestMapping : 요청 경로 매핑
	 * - /request/board 로 요청
	 * - /request/board.jsp 응답 
	 * @return
	 */
	@RequestMapping( value = "/board", method = RequestMethod.GET)
	// @RequestMapping("/request/board")
	// @RequestMapping("/board")
	public String request() {
		logger.info("[GET] - /request/board");
		return "request/board";
	}
	
	/**
	 * 경로 패턴 매핑
	 * @param no
	 * @return
	 */
	@RequestMapping(value = "/board/{no}", method = RequestMethod.GET)
	public String requestPath(@PathVariable("no") int no) {
		logger.info("[GET] - /request/board/{no}");
		logger.info("no : " + no);
		
		return "request/board";
	}
	
	/**
	 * 요청 메소드 매핑
	 * @return
	 */
	@ResponseBody // 메소드의 반환 값을, 응답 본문에 직접 담도록 지정
	@RequestMapping(value = "/board", method = RequestMethod.POST)
	public String requestPost(@RequestParam("no") int no) {
		logger.info("[POST] - /request/board");
		logger.info("no : " + no);

		return "SUCCESS - no (글번호)  : " + no;
	}
	
	
	/**
	 * 파라미터 매핑
	 * @param id
	 * @return
	 * * params 속성으로 요청 파라미터가 id가 있는 경우를 매핑 조건으로 지정한다.
	 * * /request/board?id=aloha
	 */
	@RequestMapping(value = "/board", method = RequestMethod.GET, params = "id")
	public String requestParams(@RequestParam("id") String id) {
		logger.info("[GET] - /request/board?id=" + id);
		logger.info("id : " + id);
		return "request/board";
	}
	
	/**
	 * 헤더 매핑
	 * @return
	 * * headers = "헤더명=값" 으로 지정하여 헤더를 매핑조건으로 지정한다
	 */
	@ResponseBody
	@RequestMapping(value = {"/board", "/board2"}, method = RequestMethod.POST
			       ,headers = "Content-Type=application/json") 
			  //   ,headers = {"Content-Type=application/json", "헤더2", "헤더3"}) 
	public String requestHeader() {
		logger.info("[POST] - /request/board");
		logger.info("헤더 매핑...");
		return "SUCCESS";
	}
	
	
	
	/**
	 * PUT 매핑	
	 * @return
	 * 
	 * @ResponseBody  O : return "데이터";  	--> 응답 메시지( 본문 : 데이터 )
	 * @ResponseBody  X : return "화면이름";  --> 뷰 리졸버가 jsp 선택 -> 렌더링 -> html 응답 
	 */
	@RequestMapping(value = "/board", method = RequestMethod.PUT)
	public String requestPut() {
		logger.info("[PUT] - /request/board");
		return "redirect:/";			// 메인 화면으로 리다이렉트
	}
	
	
	/**
	 * 컨텐츠 타입 매핑
	 * - Content-Type 헤더의 값으로 매핑
	 * - consumes = "컨텐츠타입값" 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/board", method = RequestMethod.POST
				   ,consumes = "application/xml")
	public String requestContentType() {
		logger.info("[POST] - /board/request");
		logger.info("컨텐츠 타입 매핑");
		return "SUCCESS - xml";
	}
	
	
	/**
	 * Accept 매핑
	 * - Accept 헤더의 값으로 매핑
	 * - Accept 헤더 ? 
	 *   : 응답 받을 컨텐츠 타입을 서버에게 알려주는 헤더
	 * - produces = "컨텐츠 타입"
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/board", method = RequestMethod.POST
				   ,produces = "application/json")	
	public String requestAccept() {
		logger.info("[POST] - /request/board");
		logger.info("Accept 매핑...");
		return "SUCCESS - Accept 매핑...";
	}

	/* --------------------------- [ 요청 경로 매핑 ]  --------------------------- */
	/* ------------------------------------------------------------------------ */
	/* --------------------------- [ 요청 처리 ]  --------------------------- */
}













