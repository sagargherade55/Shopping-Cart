package com.jsp.sagar_shopee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import static org.springframework.http.HttpStatus.*;

import com.jsp.sagar_shopee.dto.ImageDto;
import com.jsp.sagar_shopee.exception.ResourseNotFoundException;
import com.jsp.sagar_shopee.model.Image;
import com.jsp.sagar_shopee.respone.ApiResponse;
import com.jsp.sagar_shopee.service.ImageService;

@RestController
@RequestMapping("${api.prefix}/images")
public class ImageController {

	private final ImageService imageService;

	@Autowired
	public ImageController(ImageService imageService) {
		this.imageService = imageService;
	}

	@PostMapping("upload")
	public ResponseEntity<ApiResponse> saveImages(@RequestParam List<MultipartFile> files, @RequestParam long prodId) {

		try {
			List<ImageDto> imageDto = imageService.saveImages(files, prodId);
			return ResponseEntity.ok(new ApiResponse("Upload Success ! ", imageDto));
		} catch (Exception e) {
			return ResponseEntity.status(INTERNAL_SERVER_ERROR)
					.body(new ApiResponse("Upload Failed !", e.getMessage()));
		}

	}

	@GetMapping("/download/{imageId}")
	public ResponseEntity<Resource> downLoadImage(@PathVariable long imageId) throws Exception {
		Image image = imageService.getImageById(imageId);

		ByteArrayResource resource = new ByteArrayResource(
				image.getImage().getBytes(1, (int) image.getImage().length()));

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileType()))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFileName() + "\"")
				.body(resource);
	}

	@PutMapping("image/{imageId}/update")
	public ResponseEntity<ApiResponse> updateImage(@PathVariable long imageId, @RequestParam MultipartFile file) {

		try {
			Image image = imageService.getImageById(imageId);
			if (image != null) {
				imageService.updateImage(file, imageId);
				return ResponseEntity.ok(new ApiResponse("Update Successfull ! ", null));
			}
		} catch (ResourseNotFoundException e) {

			return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}

		return ResponseEntity.status(INTERNAL_SERVER_ERROR)
				.body(new ApiResponse("Update Failed !", INTERNAL_SERVER_ERROR));
	}

	@DeleteMapping("image/{imageId}/delete")
	public ResponseEntity<ApiResponse> deleteImage(@PathVariable long imageId) {

		try {
			Image image = imageService.getImageById(imageId);
			if (image != null) {
				imageService.deleteImageById(imageId);
				return ResponseEntity.ok(new ApiResponse("Delete Successfull ! ", null));
			}
		} catch (ResourseNotFoundException e) {

			return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
		}

		return ResponseEntity.status(INTERNAL_SERVER_ERROR)
				.body(new ApiResponse("Delete Failed !", INTERNAL_SERVER_ERROR));
	}
}
