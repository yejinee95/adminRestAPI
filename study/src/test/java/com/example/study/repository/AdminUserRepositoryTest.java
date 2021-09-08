package com.example.study.repository;

import com.example.study.StudyApplicationTests;
import com.example.study.model.entity.AdminUser;
import com.example.study.model.entity.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Optional;

public class AdminUserRepositoryTest extends StudyApplicationTests {

	@Autowired
	private AdminUserRepository adminUserRepository;

	@Test
	public void create(){
		String account = "AdminUser03";
		String password = "AdminUser03";
		String status = "REGISTERED";
		String role = "PARTNER";
		LocalDateTime createdAt = LocalDateTime.now();
		String createdBy = "AdminServer";

		AdminUser adminUser = new AdminUser();
		adminUser.setAccount(account);
		adminUser.setPassword(password);
		adminUser.setStatus(status);
		adminUser.setRole(role);


		AdminUser newAdminUser = adminUserRepository.save(adminUser);
		Assertions.assertNotNull(newAdminUser);
		Assertions.assertEquals(newAdminUser.getAccount(),account);

		newAdminUser.setAccount("CHANGE");
		adminUserRepository.save(newAdminUser);
	}

	@Test
	public void read(){
		String account = "Admin01";
		Optional<AdminUser> optionalAdminUser = adminUserRepository.findByAccount(account);

		optionalAdminUser.ifPresentOrElse(
						c -> {
							Assertions.assertEquals(c.getAccount(), account);
							System.out.println(c.getRole());
						},
						() -> {
							System.out.println("해당 직원은 없습니다");
						}
		);
	}
}
