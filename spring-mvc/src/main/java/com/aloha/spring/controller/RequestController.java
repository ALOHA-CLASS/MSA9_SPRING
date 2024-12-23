package com.aloha.spring.controller;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.aloha.spring.dto.Board;
import com.aloha.spring.dto.User;

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
	
	// 
	/**
	 * 요청 헤더 가져오기
	 * @return
	 * * @RequestHeader("헤더명") 타입 변수명
	 */
	@ResponseBody
	@RequestMapping(value = "/header", method = RequestMethod.GET)
	public String header(@RequestHeader("Accept") String accept
						,@RequestHeader("User-Agent") String userAgent
						,HttpServletRequest request) {
		// @RequestHeader 를 통한 헤더 정보 가져오기
		logger.info("[GET] - /request/header");
		logger.info("@RequestHeader 를 통한 헤더 정보 가져오기");
		logger.info("Accept - " + accept);
		logger.info("User-Agent - " + userAgent);
		
		// request 객체로부터 헤더 가져오기
		String requestAccept = request.getHeader("Accept");
		String requestUserAgent = request.getHeader("User-Agent");
		logger.info("request 객체로부터 헤더 가져오기");
		logger.info("Accept - " + requestAccept);
		logger.info("User-Agent - " + requestUserAgent);
		return "SUCCESS";
	}
	
	
	
	
	
	/**
	 * 요청 본문 가져오기
	 * @param board
	 * @return
	 * * @RequestBody
	 *   : HTTP 요청 메시지의 본문(body) 내용을 객체로 변환하는 어노테이션
	 *     주로, 클라이언트에서 json 형식으로 보낸 데이터를 객체로 변환하기 위해 사용한다.
	 *     * 생략가능 (주로 생략하고 쓴다.)
	 *     
	 *   415 에러 - 지원되지 않는 미디어 타입
	 *   (Unsupported Media Type)
	 *   : 클라이언트가 보낸 컨텐츠 타입의 요청을 서버가 처리할 수 없을 때 발생하는 에러
	 *   [클라이언트] ( application/x-www-form-urlencoded )
	 *       ↓
	 *   [ 서  버 ]  ( application/json )
	 *   * @RequestBody 를 쓰면, 본문의 컨텐츠 타입을 application/json 을 기본으로 지정
	 *   
	 *   * 비동기 또는 thunder client 로 테스트 가능
	 *   Content-Type : application/json
	 *   body {  "title" : "제목",  "writer" : "작성자",  "content" : "내용" }
	 */
	@ResponseBody
	@RequestMapping(value = "/body", method = RequestMethod.POST)
	public String requestBody(@RequestBody Board board) {
		logger.info("[POST] - /request/body");
		logger.info(board.toString());
		
		return "SUCCESS";
	}
 	
	/**
	 * 체크박스 데이터 가져오기
	 * @param hobbies
	 * @return
	 * * 체크박스 다중 데이터는 배열로 전달 받을 수 있다.
	 * * 같은 이름의 요청파라미터(name)들은 배열 또는 리스트로 전달 받을 수 있다.
	 */
	@ResponseBody
	@RequestMapping(value = "/check", method = RequestMethod.POST)
	public String requestCheck(@RequestParam("hobby") String[] hobbies) {
		logger.info("[POST] - /reuqest/check");
		
		for (String hobby : hobbies) {
			logger.info("hobby : " + hobby);
		}
		
		return "SUCCESS";
	}
	
	
	/**
	 * date 형식, 여러 요청 정보 가져오기
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/user", method = RequestMethod.POST)
//	public String requestUser(String birth) {
//	public String requestUser(@DateTimeFormat(pattern="yyyy-MM-dd") Date birth) {
	public String requestUser(User user) {
		logger.info("[POST] - /reuqest/user");
		
		// logger.info("birth : " + birth);   // 2024-11-07
		logger.info("user : " + user);
		
		return "SUCCESS";
	}
	
	/**
	 * Map 컬렉션 여러 요청 파라미터 가져오기
	 * 요청 경로 : /request/map?name=김조은&age=20
	 * @param map
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/map")
	public String requestMap(@RequestParam Map<String, String> map) {
		String name = map.get("name");
		String age = map.get("age");
		
		logger.info("name : " + name);
		logger.info("age : " + age);
		return "SUCCESS";
	}
	
	// 업로드 경로
//	@Autowired
//	@Qualifier("uploadPath")
	@Resource(name="uploadPath")
	private String uploadPath;
	
	/**
	 * 파일 업로드
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/file", method = RequestMethod.POST)
	public String fileUpload(MultipartFile file) throws Exception {
		logger.info("/request/file");
		logger.info("uploadPath : " + uploadPath);
		
		if( file == null ) return "FAIL";
		
		logger.info("originalFileName : " + file.getOriginalFilename());
		logger.info("size : " + file.getSize());
		logger.info("contentType : " + file.getContentType());
		
		// 파일 데이터 
		byte[] fileData = file.getBytes();
		
		// 파일 업로드
		String filePath = uploadPath;
		String fileName = file.getOriginalFilename();
		File uploadFile = new File(filePath, fileName);
		
		FileCopyUtils.copy(fileData, uploadFile);		// 파일 업로드 
		// FileCopyUtils.copy(파일 데이터, 파일 객체);
		// : 내부적으로는 InputStrea, OutputStream 을 이용하여 입력받은 파일을 출력한다.
		
		return "SUCCESS - uploadPath : " + uploadPath;
	}
	
	
	// 다중 파일 업로드
	@ResponseBody
	@RequestMapping(value = "/file/multi", method = RequestMethod.POST)
	public String fileUpload(@RequestParam("file") MultipartFile[] fileList) throws Exception {
		logger.info("/request/file/multi");
		logger.info("uploadPath : " + uploadPath);
		
		if( fileList == null ) return "FAIL";
		
		if( fileList.length > 0 ) {
			for (MultipartFile file : fileList) {
				logger.info("originalFileName : " + file.getOriginalFilename());
				logger.info("size : " + file.getSize());
				logger.info("contentType : " + file.getContentType());
				
				// 파일 데이터 
				byte[] fileData = file.getBytes();
				
				// 파일 업로드
				String filePath = uploadPath;
				String fileName = file.getOriginalFilename();
				File uploadFile = new File(filePath, fileName);
				
				FileCopyUtils.copy(fileData, uploadFile);		// 파일 업로드 
				// FileCopyUtils.copy(파일 데이터, 파일 객체);
				// : 내부적으로는 InputStrea, OutputStream 을 이용하여 입력받은 파일을 출력한다.
			}
		}
		return "SUCCESS - uploadPath : " + uploadPath;
	}
	
	// 데이터 등록 + 파일 업로드
	@ResponseBody
	@RequestMapping(value = "/file/board", method = RequestMethod.POST)
	public String fileUpload(Board board) throws Exception {
		logger.info("/request/file/board");
		logger.info("uploadPath : " + uploadPath);
		logger.info("board : " + board);
		
		// MultipartFile[] fileList = board.getFileList();
		List<MultipartFile> fileList = board.getFileList();
		if( fileList == null ) return "FAIL";
		
//		if( fileList.length > 0 ) {
		if( !fileList.isEmpty() ) {
			for (MultipartFile file : fileList) {
				logger.info("originalFileName : " + file.getOriginalFilename());
				logger.info("size : " + file.getSize());
				logger.info("contentType : " + file.getContentType());
				
				// 파일 데이터 
				byte[] fileData = file.getBytes();
				
				// 파일 업로드
				String filePath = uploadPath;
				String fileName = file.getOriginalFilename();
				File uploadFile = new File(filePath, fileName);
				
				FileCopyUtils.copy(fileData, uploadFile);		// 파일 업로드 
				// FileCopyUtils.copy(파일 데이터, 파일 객체);
				// : 내부적으로는 InputStrea, OutputStream 을 이용하여 입력받은 파일을 출력한다.
			}
		}
		return "SUCCESS";
	}
	
	
	// ajax 비동기 파일 업로드
	/**
	 * 비동기 파일 업로드 화면 
	 * @return
	 */
	@RequestMapping("/ajax")
	public String ajax() {
		return "request/ajax";
	}
	
	/**
	 * AJAX 비동기 파일 업로드
	 * @param board
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/ajax", method = RequestMethod.POST)
	public String fileUploadPost(Board board) throws Exception {
		logger.info("/request/ajax");
		logger.info("uploadPath : " + uploadPath);
		logger.info("board : " + board);
		
		// MultipartFile[] fileList = board.getFileList();
		List<MultipartFile> fileList = board.getFileList();
		if( fileList == null ) return "FAIL";
		
//		if( fileList.length > 0 ) {
		if( !fileList.isEmpty() ) {
			for (MultipartFile file : fileList) {
				logger.info("originalFileName : " + file.getOriginalFilename());
				logger.info("size : " + file.getSize());
				logger.info("contentType : " + file.getContentType());
				
				// 파일 데이터 
				byte[] fileData = file.getBytes();
				
				// 파일 업로드
				String filePath = uploadPath;
				String fileName = file.getOriginalFilename();
				File uploadFile = new File(filePath, fileName);
				
				FileCopyUtils.copy(fileData, uploadFile);		// 파일 업로드 
				// FileCopyUtils.copy(파일 데이터, 파일 객체);
				// : 내부적으로는 InputStrea, OutputStream 을 이용하여 입력받은 파일을 출력한다.
			}
		}
		return "SUCCESS";
	}
	
}













