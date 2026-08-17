package com.jsp.sagar_shopee.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jsp.sagar_shopee.dto.ImageDto;
import com.jsp.sagar_shopee.exception.ResourseNotFoundException;
import com.jsp.sagar_shopee.model.Image;
import com.jsp.sagar_shopee.model.Product;
import com.jsp.sagar_shopee.repo.ImageRepo;

import lombok.RequiredArgsConstructor;

@Service
public class ImageServiceImpl implements ImageService {

	private final ImageRepo imageRepo;
	private final ProductService productService;

	@Autowired
	public ImageServiceImpl(ImageRepo imageRepo, ProductService productService) {
		this.imageRepo = imageRepo;
		this.productService = productService;
	}

	@Override
	public Image getImageById(long id) {
		return imageRepo.findById(id)
				.orElseThrow(() -> new ResourseNotFoundException("Image not Found with id : " + id));
	}

	@Override
	public void deleteImageById(long id) {
		imageRepo.findById(id).ifPresentOrElse(imageRepo::delete, () -> {
			throw new ResourseNotFoundException("Image not Found with id : " + id);
		});
	}

	@Override
	public List<ImageDto> saveImages(List<MultipartFile> files, long prodId) {

		Product product = productService.getProductById(prodId);
		List<ImageDto> savedImageDto = new ArrayList<>();

		for (MultipartFile file : files) {
			try {
				Image image = new Image();
				image.setFileName(file.getOriginalFilename());
				image.setFileType(file.getContentType());
				image.setImage(new SerialBlob(file.getBytes()));

				image.setProduct(product);

				String buildDownLoadUrl = "/api/v1/images/download/";
				String downLoadUrl = buildDownLoadUrl + image.getId();
				image.setDownloadUrl(downLoadUrl);

				Image savedImage = imageRepo.save(image);

				savedImage.setDownloadUrl(buildDownLoadUrl + savedImage.getId());
				imageRepo.save(savedImage);

				ImageDto imageDto = new ImageDto();

				imageDto.setId(savedImage.getId());
				imageDto.setImageFileName(savedImage.getFileName());
				imageDto.setDownloadUrl(savedImage.getDownloadUrl());

				savedImageDto.add(imageDto);

			} catch (SQLException | IOException e) {
				throw new RuntimeException(e.getMessage());
			}
		}
		return savedImageDto;
	}

	@Override
	public void updateImage(MultipartFile file, long imageId) {
		Image image = getImageById(imageId);

		try {
			image.setFileName(file.getOriginalFilename());
			image.setImage(new SerialBlob(file.getBytes()));
			imageRepo.save(image);
		} catch (SQLException | IOException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
}
