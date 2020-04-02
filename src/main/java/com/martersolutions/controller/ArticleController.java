package com.martersolutions.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.martersolutions.model.RNArticle;
import com.martersolutions.model.RNComment;
import com.martersolutions.service.ArticleCommentService;
import com.martersolutions.service.ArticleService;

@RestController
@RequestMapping("/owners/articles")
public class ArticleController {

	@Autowired
	private ArticleService articleService;
	@Autowired
	private ArticleCommentService cmtService;

	// get all articles
	@GetMapping("")
	public List<RNArticle> getAllArticles(@RequestParam(name = "page", defaultValue = "1") int pageNumber) {
		return articleService.getAllArticles(pageNumber);
	}

	// create new article
	@PostMapping("")
	public String postArticle(@RequestParam("ownerId") String ownerId, @RequestParam("title") String title,
			@RequestParam("description") String description, @RequestParam("media") MultipartFile file)
			throws IOException, InterruptedException {

		return articleService.postArticle(ownerId, title, description, file);
	}

	// get single comment by cmtId
	@GetMapping("/comments/{cmtId}")
	public RNComment getCommentById(@PathVariable("cmtId") String cmtId) {
		return cmtService.getCommentById(cmtId);
	}

	// write comment to article
	@PostMapping("/comments")
	public String writeComment(@RequestParam("articleId") String articleId, @RequestParam("message") String message,
			@RequestParam("ownerId") String ownerId, @RequestParam("ownerName") String ownerName) {

		return cmtService.writeComment(articleId, message, ownerId, ownerName);
	}

	// write reply to comment
	@PostMapping("/comments/{cmtId}/replies")
	public String writeReply(@RequestParam("message") String message, @RequestParam("cmtId") String cmtId,
			@RequestParam("ownerId") String ownerId, @RequestParam("ownerName") String ownerName) {
		return cmtService.writeReply(message, cmtId, ownerId, ownerName);
	}

	// like comment
	@PostMapping("/comments/{cmtId}/like")
	public String likeComment(@PathVariable("cmtId") String cmtId, @RequestParam("ownerId") String ownerId) {

		return cmtService.likeComment(cmtId, ownerId);
	}

	// unlike comment
	@PostMapping("/comments/{cmtId}/unlike")
	public String unlikeComment(@PathVariable("cmtId") String cmtId, @RequestParam("ownerId") String ownerId) {

		return cmtService.unlikeComment(cmtId, ownerId);
	}

	@GetMapping("/media/{mediaId}")
	public ResponseEntity<Resource> getArticleMedia(@PathVariable("mediaId") String mediaId)
			throws MalformedURLException {

		Resource resource = articleService.getArticleMedia(mediaId);

		if (resource.exists() || resource.isReadable()) {
			return ResponseEntity.ok().body(resource);
		} else {
			System.out.println("No File");
			return null;
		}
	}

}
