package com.example.study.repository;

import com.example.study.StudyApplicationTests;
import com.example.study.model.entity.Item;
import com.example.study.model.entity.User;
import com.example.study.model.enumClass.UserStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.Optional;

public class UserRepositoryTest extends StudyApplicationTests {

	//DI
	@Autowired
	private UserRepository userRepository;

	@Test
	public void create(){
		String account = "Test03";
		String password = "Test03";
		UserStatus status = UserStatus.REGISTERED;
		String email = "Test01@gmail.com";
		String phoneNumber = "010-3333-3333";
		LocalDateTime registeredAt = LocalDateTime.now();
		LocalDateTime createdAt = LocalDateTime.now();
		String createdBy = "AdminServer";

		User user = new User();
		user.setAccount(account);
		user.setPassword(password);
		user.setStatus(status);
		user.setEmail(email);
		user.setPhoneNumber(phoneNumber);
		user.setRegisteredAt(registeredAt);

		//user u = User.builder().account(account).password(password).status(status).email(email).build();

		User newUser = userRepository.save(user);
		Assertions.assertNotNull(newUser);
		Assertions.assertEquals(newUser.getAccount(), account);
	}

	@Test
	@Transactional
	public void read(){
//		Optional<User> user = userRepository.findById(2L);
//		user.ifPresent(selectUser -> {
//			System.out.println("user : " + selectUser);
//		});

//		Optional<User> user = userRepository.findByAccount("TestUser03");
//		Optional<User> user = userRepository.findByEmail("Testuser03@gmail.com");
//		user.ifPresentOrElse(
//						selectUser -> selectUser.getOrderDetailList().stream().forEach(detail -> {
//							Item item = detail.getItem();
//							System.out.println(item);
//						}),
//						() -> System.out.println("만들 커피: ")
//		);
		String phoneNumber = "010-1111-1111";
		User user = userRepository.findFirstByPhoneNumberOrderByIdDesc(phoneNumber);

		user.getOrderGroupList().stream().forEach(orderGroup -> {
			System.out.println(orderGroup.getTotalPrice());
			System.out.println(orderGroup.getRevAddress());
			System.out.println(orderGroup.getRevName());
			System.out.println(orderGroup.getTotalQuantity());

			orderGroup.getOrderDetailList().forEach(orderDetail -> {
				System.out.println(orderDetail.getItem().getPartner().getName());
				System.out.println(orderDetail.getItem().getPartner().getCategory().getTitle());
				System.out.println(orderDetail.getItem().getName());
				System.out.println(orderDetail.getItem().getPartner().getCallCenter());
				System.out.println(orderDetail.getStatus());
				System.out.println(orderDetail.getArrivalDate());
			});
		});

		Assertions.assertNotNull(user);
	}

	@Test
	public void update(){
		Optional<User> user = userRepository.findById(5L);
		user.ifPresentOrElse(
						selectUser -> {
							selectUser.setAccount("PPPP");
							selectUser.setUpdatedAt(LocalDateTime.now());
							selectUser.setUpdatedBy("update method()");

							userRepository.save(selectUser);
							System.out.println("selectUser :" + selectUser);
						},
						() -> System.out.println("정보없음" )
		);
	}

	@Test
	@Transactional
	public void delete(){
		Optional<User> user = userRepository.findById(3L);

		//Assertions.assertTrue(user.isPresent());

		user.ifPresentOrElse(selectUser -> userRepository.delete(selectUser),
						() -> System.out.println("정보없음")
		);

	}
}
