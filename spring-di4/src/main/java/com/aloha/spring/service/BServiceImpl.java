package com.aloha.spring.service;

import org.springframework.stereotype.Service;

@Service 		// 서비스 빈으로 등록
				// 빈이름 지정하지 않으면, "BServiceImpl" 빈이름으로 지정
public class BServiceImpl implements MyService {

	@Override
	public void test() {
		System.out.println("BServiceImpl...");
	}

}
