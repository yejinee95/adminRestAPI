package com.example.study.repository;

import com.example.study.model.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail,Long> {
	@Query(value = "select * from study.order_detail where id = ?1" , nativeQuery = true)
	Optional<OrderDetail> findByTest(Long id);

}
