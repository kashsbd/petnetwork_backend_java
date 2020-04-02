/**
 * 
 */
package com.martersolutions.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.martersolutions.model.Article;
import com.martersolutions.model.Media;
import com.martersolutions.model.Owner;
import com.martersolutions.model.RNArticle;
import com.martersolutions.model.RNMedia;
import com.martersolutions.repository.ArticleRepository;
import com.martersolutions.repository.MediaRepository;
import com.martersolutions.repository.OwnerRepository;
import com.martersolutions.utils.Constants;

/**
 * @author prakash Jun 18, 2018
 */
@Service
public class ArticleService {

	@Autowired
	private ArticleRepository articleRepo;
	@Autowired
	private MediaRepository mediaRepo;
	@Autowired
	private OwnerRepository ownerRepo;

	private Process process;

	public List<RNArticle> getAllArticles(int pageNumber) {
		List<RNArticle> rnArticles = new ArrayList<RNArticle>();

		List<Article> articles = articleRepo.findAll(PageRequest.of(pageNumber - 1, 15)).getContent();

		Iterator<Article> it = articles.iterator();

		while (it.hasNext()) {
			Article article = (Article) it.next();
			// retrieve media data from article
			Media savedMedia = mediaRepo.findById(article.getMediaId()).get();
			RNMedia asset = new RNMedia(savedMedia.getContentType(), article.getMediaId());
			// retrieve owner info from article
			Owner owner = ownerRepo.findById(article.getOwnerId()).get();

			RNArticle rnArticle = new RNArticle(article.getId(), article.getOwnerId(), owner.getProfilePicId(),
					article.getTitle(), article.getDescription(), article.getCreatedDate(), article.getEditedDate(),
					asset, article.getLikes(), article.getComments());

			rnArticles.add(rnArticle);
		}
		return rnArticles;
	}

	public String postArticle(String ownerId, String title, String description, MultipartFile file)
			throws IOException, InterruptedException {
		String newDate = LocalDateTime.now().toString().replaceAll(":", "-").replace(".", "-");

		Article newArtical = new Article();
		newArtical.setOwnerId(ownerId);
		newArtical.setTitle(title);
		newArtical.setDescription(description);
		newArtical.setCreatedDate(LocalDateTime.now().toString());

		Media newMedia = new Media();
		newMedia.setCreatedDate(LocalDateTime.now().toString());
		newMedia.setContentType(file.getContentType());

		byte[] bytes = file.getBytes();
		String fullPath = Constants.ARTICAL_FOLDER + file.getOriginalFilename();
		Path path = Paths.get(fullPath);
		Files.write(path, bytes);

		process = Runtime.getRuntime().exec(new String[] { "ffmpeg", "-i", fullPath, "-vf", "scale='320:-1'",
				Constants.ARTICAL_FOLDER + ownerId + newDate + file.getOriginalFilename() });

		while (process.isAlive()) {
			Thread.sleep(1000);
		}

		newMedia.setMediaUrl(ownerId + newDate + file.getOriginalFilename());

		String mediaId = mediaRepo.save(newMedia).getId();

		Files.delete(path);

		newArtical.setMediaId(mediaId);

		String savedArticleId = articleRepo.save(newArtical).getId();
		Owner owner = ownerRepo.findById(ownerId).get();
		owner.getArticles().add(savedArticleId);
		ownerRepo.save(owner);
		return "OK";
	}

	public Resource getArticleMedia(String mediaId) throws MalformedURLException {
		Optional<Media> media = mediaRepo.findById(mediaId);

		if (!media.isPresent()) {
			throw new NullPointerException("No Article Media Found !");
		}

		Path path = Paths.get(Constants.ARTICAL_FOLDER + media.get().getMediaUrl());

		Resource resource = new UrlResource(path.toUri());
		return resource;

	}

}
