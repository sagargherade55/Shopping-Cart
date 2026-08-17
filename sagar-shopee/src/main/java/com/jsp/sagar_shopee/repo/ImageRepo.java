package com.jsp.sagar_shopee.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jsp.sagar_shopee.model.Image;

@Repository
public interface ImageRepo extends JpaRepository<Image, Long>{

}
