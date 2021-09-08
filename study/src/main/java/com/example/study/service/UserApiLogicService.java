package com.example.study.service;

import com.example.study.ifs.CrudInterFace;
import com.example.study.model.entity.Item;
import com.example.study.model.entity.OrderGroup;
import com.example.study.model.entity.User;
import com.example.study.model.enumClass.UserStatus;
import com.example.study.model.network.Header;
import com.example.study.model.network.Pagination;
import com.example.study.model.network.request.UserApiRequest;
import com.example.study.model.network.response.ItemApiResponse;
import com.example.study.model.network.response.OrderGroupApiResponse;
import com.example.study.model.network.response.UserApiResponse;
import com.example.study.model.network.response.UserOrderInfoApiResponse;
import com.example.study.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserApiLogicService implements CrudInterFace<UserApiRequest, UserApiResponse> {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private OrderGroupLoginService orderGroupLoginService;

	@Autowired
	private ItemApiLogicService itemApiLogicService;

	// 1. requet data
	// 2. user 생성
	// 3. 생성된 데이터 -> api 만들기
	@Override
	public Header<UserApiResponse> create(Header<UserApiRequest> request) {

		// 1. request data
		UserApiRequest userApiRequest = request.getData();

		// 2. User 생성

		User user = User.builder()
						.account(userApiRequest.getAccount())
						.password(userApiRequest.getPassword())
						.status(UserStatus.REGISTERED)
						.phoneNumber(userApiRequest.getPhoneNumber())
						.email(userApiRequest.getEmail())
						.registeredAt(LocalDateTime.now())
						.build();
		User newUser = userRepository.save(user);

		// 3. 생성된 데이터 -> 데이터리턴


		return Header.OK(response(newUser));
	}

	@Override
	public Header<UserApiResponse> read(Long id) {

		// id -> repository getOne , getById
		Optional<User> optionalUser = userRepository.findById(id);
		// user -> userAPiResponse return
		return optionalUser
						.map(user -> Header.OK(response(user)))
						.orElseGet(()-> Header.ERROR("데이터없음"));
	}

	@Override
	public Header<UserApiResponse> update(Header<UserApiRequest> request) {

		// 1. data
		UserApiRequest userApiRequest = request.getData();

		// 2. id -> user data찾기
		Optional<User> optionalUser = userRepository.findById(userApiRequest.getId());


		return	optionalUser
							.map(user -> {
								user
									.setAccount(userApiRequest.getAccount())
									.setStatus(userApiRequest.getStatus())
									.setPhoneNumber(userApiRequest.getPhoneNumber())
									.setEmail(userApiRequest.getEmail())
									.setRegisteredAt(userApiRequest.getRegisteredAt())
									.setUnregisteredAt(userApiRequest.getUnregisteredAt());

								return user;
							})
						.map(user -> userRepository.save(user))
						.map(updateUser -> Header.OK(response(updateUser)))
						.orElseGet(() -> Header.ERROR("데이터없음"));

	}

	@Override
	public Header delete(Long id) {
		Optional<User> optionalUser = userRepository.findById(id);

		return optionalUser.map(user -> {
							userRepository.delete(user);
							return Header.OK();
						})
						.orElseGet(()-> Header.ERROR("데이터없음"));
	}

	private UserApiResponse response(User user){
		// user -> userAPI 리턴
		UserApiResponse userApiResponse = UserApiResponse.builder()
						.id(user.getId())
						.account(user.getAccount())
						.password(user.getPassword()) // 암호화
						.email(user.getEmail())
						.phoneNumber(user.getPhoneNumber())
						.status(user.getStatus())
						.registeredAt(user.getRegisteredAt())
						.unRegisteredAt(user.getUnregisteredAt())
						.build();

		// Header + data

		return userApiResponse;
	}

	public Header<List<UserApiResponse>> serarch(Pageable pageable) {

		Page<User> users = userRepository.findAll(pageable);

		List<UserApiResponse> userApiResponseList = users.stream()
						.map(user -> response(user))
						.collect(Collectors.toList());


		Pagination pagination = Pagination.builder()
						.totalPages(users.getTotalPages())
						.totalElements(users.getTotalElements())
						.currentPage(users.getNumber())
						.currentElements(users.getNumberOfElements())
						.build();
		return Header.OK(userApiResponseList, pagination);
	}

	public Header<UserOrderInfoApiResponse> orderInfo(Long id) {

		// user
		User user = userRepository.getById(id);
		UserApiResponse userApiResponse = response(user);
		// orderGroup
		List<OrderGroup> orderGroupList = user.getOrderGroupList();
		List<OrderGroupApiResponse> orderGroupApiResponseList = orderGroupList.stream()
						.map(orderGroup -> {
							OrderGroupApiResponse orderGroupApiResponse = orderGroupLoginService.response(orderGroup).getData();

							List<ItemApiResponse> itemApiResponseList = orderGroup.getOrderDetailList().stream()
											.map(detail -> detail.getItem())
											.map(item -> itemApiLogicService.response(item).getData())
											.collect(Collectors.toList());

							orderGroupApiResponse.setItemApiResponseList(itemApiResponseList);
							return  orderGroupApiResponse;
						})
						.collect(Collectors.toList());

		userApiResponse.setOrderGroupApiResponseList(orderGroupApiResponseList);

		UserOrderInfoApiResponse userOrderInfoApiResponse = UserOrderInfoApiResponse.builder()
						.userApiResponse(userApiResponse)
						.build();

		return Header.OK(userOrderInfoApiResponse);

	}
}
