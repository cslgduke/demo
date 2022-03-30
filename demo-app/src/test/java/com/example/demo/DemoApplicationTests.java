package com.example.demo;

import cn.amorou.uid.UidGenerator;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
class DemoApplicationTests {

	@Resource
	private UidGenerator uidGenerator;

	@Test
	public void test() {
		for (int i = 0; i < 100; i++) {
			System.out.println(uidGenerator.getUID());
		}
	}

}
