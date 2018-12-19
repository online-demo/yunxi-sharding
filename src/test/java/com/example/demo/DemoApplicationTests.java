package com.example.demo;

import com.alibaba.fastjson.JSON;
import com.example.demo.entity.UserInfo;
import com.example.demo.service.UserService;
import io.shardingjdbc.core.api.HintManager;
import io.shardingjdbc.core.hint.HintManagerHolder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

	@Resource
	UserService userService;

	public static Long userId = 0L;

	/**
	 * 模拟保存用户L
	 */
	@Test
	public void saveUser() {
		//保存10个用户
		for (int i = 0; i < 10; i++) {
			UserInfo userInfo = new UserInfo();
			userInfo.setUserId(userId++);
			userInfo.setAccount("Account" + i);
			userInfo.setPassword("pass" + i);
			userInfo.setUserName("name" + i);
			//强制修改路由规则
			if(i == 3){
				HintManagerHolder.clear();
				HintManager hintManager = HintManager.getInstance();
				hintManager.addDatabaseShardingValue("user_info", "user_id", 2L);
				hintManager.addTableShardingValue("user_info", "user_id", 2L);
			}
			userService.saveUser(userInfo);
		}
	}

	/**
	 * 模拟查询用户
	 */
	@Test
	public void queryUser() {
		UserInfo userInfo = userService.queryUser(1L);
		System.out.printf("用户信息是=%s%n", JSON.toJSONString(userInfo));
	}
}
