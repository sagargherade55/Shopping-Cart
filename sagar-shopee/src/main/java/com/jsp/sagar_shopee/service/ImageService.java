package com.jsp.sagar_shopee.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.jsp.sagar_shopee.dto.ImageDto;
import com.jsp.sagar_shopee.model.Image;

public interface ImageService {

	Image getImageById(long id);

	void deleteImageById(long id);

	List<ImageDto> saveImages(List<MultipartFile> files, long prodId);

	void updateImage(MultipartFile file, long imageId);
}
