package com.example.study.service;

import com.example.study.ifs.CrudInterFace;
import com.example.study.model.network.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import javax.swing.text.html.parser.Entity;

@Component
public abstract class BaseService<Req,Res,Entity> implements CrudInterFace<Req,Res> {

	@Autowired(required = false)
	protected JpaRepository<Entity,Long> baseRepository;
}
