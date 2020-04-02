/**
 * 
 */
package com.martersolutions.service;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import com.martersolutions.model.Media;
import com.martersolutions.repository.MediaRepository;
import com.martersolutions.utils.Constants;

/**
 * @author prakash Jun 11, 2018
 */
@Service
public class MediaService {

	@Autowired
	private MediaRepository mediaRepo;

	public Resource getMedia(String mediaId, String folder) throws MalformedURLException {

		Optional<Media> media = mediaRepo.findById(mediaId);

		if (!media.isPresent()) {
			throw new NullPointerException("No Feed Media Found !");
		}

		Path path = Paths.get(folder + media.get().getMediaUrl());

		Resource resource = new UrlResource(path.toUri());

		return resource;
	}

	public Resource getVideoThumbnail(String mediaId) throws MalformedURLException {

		Optional<Media> media = mediaRepo.findById(mediaId);

		if (!media.isPresent()) {
			throw new NullPointerException("No Thumbnail Media Found !");
		}

		Path path = Paths.get(Constants.THUMBNAIL_FOLDER + media.get().getThumbnailUrl());

		Resource resource = new UrlResource(path.toUri());

		return resource;
	}

}
