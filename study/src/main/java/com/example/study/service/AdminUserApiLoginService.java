package com.example.study.service;

import com.example.study.ifs.CrudInterFace;
import com.example.study.model.entity.AdminUser;
import com.example.study.model.network.Header;
import com.example.study.model.network.request.AdminUserApiRequest;
import com.example.study.model.network.response.AdminUserApiResponse;
import com.example.study.repository.AdminUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AdminUserApiLoginService implements CrudInterFace<AdminUserApiRequest, AdminUserApiResponse> {

	@Autowired
	private AdminUserRepository adminUserRepository;

	@Override
	public Header<AdminUserApiResponse> create(Header<AdminUserApiRequest> request) {

		AdminUserApiRequest body = request.getData();

		AdminUser adminUser = AdminUser.builder()
						.account(body.getAccount())
						.password(body.getPassword())
						.status(body.getStatus())
						.role(body.getRole())
						.lastLoginAt(LocalDateTime.now())
						.loginFailCount(body.getLoginFailCount())
						.passwordUpdatedAt(body.getPasswordUpdatedAt())
						.registeredAt(LocalDateTime.now())
						.build();
		AdminUser newAdminUser = adminUserRepository.save(adminUser);

		return response(newAdminUser);
	}

	@Override
	public Header<AdminUserApiResponse> read(Long id) {
		return adminUserRepository.findById(id)
						.map(adminUser -> response(adminUser))
						.orElseGet(()-> Header.ERROR("데이터없음"));
	}

	@Override
	public Header<AdminUserApiResponse> update(Header<AdminUserApiRequest> request) {

		AdminUserApiRequest adminUserApiRequest = request.getData();

		return	adminUserRepository.findById(adminUserApiRequest.getId())
						.map(entityAdminUser -> {
							entityAdminUser.setStatus(adminUserApiRequest.getStatus())
											.setAccount(adminUserApiRequest.getAccount())
											.setRole(adminUserApiRequest.getRole())
											;
							return entityAdminUser;
						})
						.map(newEntityAdminUser -> adminUserRepository.save(newEntityAdminUser))
						.map(updateAdminUser -> response(updateAdminUser))
						.orElseGet(() -> Header.ERROR("데이터 없음"));
	}

	@Override
	public Header delete(Long id) {
		return	adminUserRepository.findById(id)
						.map(adminUser -> {
							adminUserRepository.delete(adminUser);
							return Header.OK();
						})
						.orElseGet(() -> Header.ERROR("데이터 없음"));
	}

	private Header<AdminUserApiResponse> response(AdminUser adminUser){
		AdminUserApiResponse adminUserApiResponse = AdminUserApiResponse.builder()
						.id(adminUser.getId())
						.account(adminUser.getAccount())
						.password(adminUser.getPassword())
						.status(adminUser.getStatus())
						.role(adminUser.getRole())
						.lastLoginAt(adminUser.getLastLoginAt())
						.loginFailCount(adminUser.getLoginFailCount())
						.passwordUpdatedAt(adminUser.getPasswordUpdatedAt())
						.registeredAt(adminUser.getRegisteredAt())
						.unregisteredAt(adminUser.getUnregisteredAt())
						.build();

		// Header + data

		return Header.OK(adminUserApiResponse);
	}
}
