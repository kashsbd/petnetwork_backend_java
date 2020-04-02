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

import com.martersolutions.model.RNComment;
import com.martersolutions.model.RNFeed;
import com.martersolutions.service.FeedCommentService;
import com.martersolutions.service.FeedService;
import com.martersolutions.service.MediaService;
import com.martersolutions.utils.Constants;

@RestController
@RequestMapping("/owners/feeds")
public class FeedController {

	@Autowired
	private FeedService feedService;
	@Autowired
	private FeedCommentService cmtService;
	@Autowired
	private MediaService mediaService;

	// get all feeds
	@GetMapping("")
	public List<RNFeed> getAllFeeds() {

		return feedService.getAllFeeds();
	}


	// create feed
	@PostMapping("")
	public String createFeed(@RequestParam("ownerId") String ownerId, @RequestParam("activity") String activity,
			@RequestParam("hashtag") String hashtag, @RequestParam("description") String description,
			@RequestParam("petName") String petName, @RequestParam("media") MultipartFile files[])
			throws IOException, InterruptedException {

		return feedService.createFeed(ownerId, activity, hashtag, description, petName, files);
	}

	// get all comments by feedId
	@GetMapping("/{feedId}/comments")
	public List<RNComment> getAllCommentsByFeedId(@PathVariable("feedId") String feedId) {

		return cmtService.getAllFeedComments(feedId);
	}

	// get single comment by cmtId
	@GetMapping("/comments/{cmtId}")
	public RNComment getCommentById(@PathVariable("cmtId") String cmtId) {
		System.out.println("Hello");
		return cmtService.getCommentById(cmtId);
	}

	// write comment to feed
	@PostMapping("/comments")
	public String writeComment(@RequestParam("feedId") String feedId, @RequestParam("message") String message,
			@RequestParam("ownerId") String ownerId, @RequestParam("commentorName") String name) {

		return cmtService.writeComment(feedId, message, ownerId, name);
	}

	// get all replies of a comment
	@GetMapping("/comments/{cmtId}/replies")
	public List<RNComment> getAllReplies(@PathVariable("cmtId") String cmtId) {
		return cmtService.getAllReplies(cmtId);
	}

	// write reply to comment
	@PostMapping("/comments/{cmtId}/replies")
	public String writeReply(@RequestParam("message") String message, @PathVariable("cmtId") String cmtId,
			@RequestParam("ownerId") String ownerId, @RequestParam("commentorName") String name) {

		return cmtService.writeReply(message, cmtId, ownerId, name);
	}

	// like comment or reply
	@PostMapping("/comments/{cmtId}/like")
	public String likeComment(@PathVariable("cmtId") String cmtId, @RequestParam("ownerId") String ownerId) {

		return cmtService.likeComment(cmtId, ownerId);
	}

	// unlike comment or reply
	@PostMapping("/comments/{cmtId}/unlike")
	public String unlikeComment(@PathVariable("cmtId") String cmtId, @RequestParam("ownerId") String ownerId) {

		return cmtService.unlikeComment(cmtId, ownerId);
	}

	// like feed
	@PostMapping("/{feedId}/like")
	public String likeFeed(@PathVariable("feedId") String feedId, @RequestParam("ownerId") String ownerId) {

		return feedService.likeFeed(feedId, ownerId);
	}

	// unlike feed
	@PostMapping("/{feedId}/unlike")
	public String unlikeFeed(@PathVariable("feedId") String feedId, @RequestParam("ownerId") String ownerId) {

		return feedService.unlikeFeed(feedId, ownerId);
	}

	// get media by mediaId
	@GetMapping("/media/{mediaId}")
	public ResponseEntity<Resource> getFeedMedia(@PathVariable("mediaId") String mediaId) throws MalformedURLException {

		Resource resource = mediaService.getMedia(mediaId, Constants.FEEDPIC_FOLDER);

		if (resource.exists() || resource.isReadable()) {
			return ResponseEntity.ok().body(resource);
		} else {
			System.out.println("No Feed File !");
			return null;
		}
	}

	// get video thumbnail by mediaId
	@GetMapping("/media/{mediaId}/thumbnail")
	public ResponseEntity<Resource> getVideoThumbnail(@PathVariable("mediaId") String mediaId)
			throws MalformedURLException {

		Resource resource = mediaService.getVideoThumbnail(mediaId);

		if (resource.exists() || resource.isReadable()) {
			return ResponseEntity.ok().body(resource);
		} else {
			System.out.println("No Thumbnail File !");
			return null;
		}
	}

}
