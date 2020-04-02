package com.martersolutions.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.martersolutions.model.Feed;
import com.martersolutions.model.Media;
import com.martersolutions.model.Owner;
import com.martersolutions.model.RNFeed;
import com.martersolutions.model.RNMedia;
import com.martersolutions.model.Status;
import com.martersolutions.repository.FeedRepository;
import com.martersolutions.repository.MediaRepository;
import com.martersolutions.repository.OwnerRepository;
import com.martersolutions.repository.StatusRepository;
import com.martersolutions.utils.Constants;

/**
 * @author prakash Jun 11, 2018
 */
@Service
public class FeedService {

	@Autowired
	private FeedRepository feedRepo;
	@Autowired
	private OwnerRepository ownerRepo;
	@Autowired
	private StatusRepository statusRepo;
	@Autowired
	private MediaRepository mediaRepo;

	private Process process;
	private Process thumbProcess;

	public List<RNFeed> getAllFeeds() {

		List<RNFeed> rnFeeds = new ArrayList<RNFeed>();

		List<Feed> feeds = feedRepo.findAll();

		Iterator<Feed> it = feeds.iterator();

		while (it.hasNext()) {
			Feed feed = (Feed) it.next();
			List<RNMedia> rnMedia = new ArrayList<RNMedia>();

			// retrieve owner info from feed
			Owner owner = ownerRepo.findById(feed.getOwnerId()).get();
			// retrieve status info from feed
			Status status = statusRepo.findById(feed.getStatusId()).get();
			// get all media id of feed
			List<String> media = feed.getMedia();

			// media iterator
			Iterator<String> mIt = media.iterator();

			while (mIt.hasNext()) {
				String mediaId = (String) mIt.next();
				Media savedMedia = mediaRepo.findById(mediaId).get();
				RNMedia asset = new RNMedia(savedMedia.getContentType(), mediaId);
				rnMedia.add(asset);
			}

			RNFeed rnFeed = new RNFeed(feed.getId(), feed.getOwnerId(),
					owner.getFirstName() + " " + owner.getLastName(), owner.getProfilePicId(), feed.getActivity(),
					feed.getHashTags(), feed.getPetName(), status.getTextStatus(), rnMedia, feed.getLikes(),
					feed.getComments(), feed.getCreatedDate(), feed.getEditedDate());

			rnFeeds.add(rnFeed);
		}
		return rnFeeds;
	}

	public String createFeed(String ownerId, String activity, String hashtag, String description, String petName,
			MultipartFile files[]) throws IOException, InterruptedException {

		String newDate = LocalDateTime.now().toString().replaceAll(":", "-").replace(".", "-");

		Status status = new Status();
		status.setTextStatus(description);
		String statusId = statusRepo.save(status).getId();

		Feed feed = new Feed();
		feed.setOwnerId(ownerId);
		feed.setActivity(activity);
		feed.setHashTags(hashtag);
		feed.setStatusId(statusId);
		feed.setPetName(petName);
		;
		feed.setCreatedDate(LocalDateTime.now().toString());

		for (MultipartFile file : files) {

			String type = file.getContentType();
			String fullPath = Constants.FEEDPIC_FOLDER + file.getOriginalFilename();
			Media newMedia = new Media();

			newMedia.setType("FEED");
			newMedia.setContentType(type);

			byte[] bytes = file.getBytes();
			Path path = Paths.get(fullPath);
			Files.write(path, bytes);

			if (type.startsWith("video/")) {

				thumbProcess = Runtime.getRuntime().exec("ffmpeg -i " + fullPath + " -vframes 1 -an -s 320x320 -ss 15 "
						+ Constants.THUMBNAIL_FOLDER + ownerId + newDate + file.getName() + ".png");

				if (thumbProcess.isAlive()) {
					Thread.sleep(1000);
				}

				newMedia.setThumbnailUrl(ownerId + newDate + file.getName() + ".png");

				process = Runtime.getRuntime().exec(new String[] { "ffmpeg", "-i", fullPath, "-vf", "scale='320:-1'",
						Constants.FEEDPIC_FOLDER + ownerId + newDate + file.getOriginalFilename() });

				while (process.isAlive()) {
					Thread.sleep(1000);
				}

				newMedia.setMediaUrl(ownerId + newDate + file.getOriginalFilename());

			} else if (type.startsWith("image/")) {
				process = Runtime.getRuntime().exec(new String[] { "ffmpeg", "-i", fullPath, "-vf", "scale='-1:-1'",
						Constants.FEEDPIC_FOLDER + ownerId + newDate + file.getOriginalFilename() });

				while (process.isAlive()) {
					Thread.sleep(10000);
				}

				newMedia.setMediaUrl(ownerId + newDate + file.getOriginalFilename());
			}

			newMedia.setCreatedDate(LocalDateTime.now().toString());

			String mediaId = mediaRepo.save(newMedia).getId();

			feed.getMedia().add(mediaId);

			Files.delete(path);
		}

		String savedFeedId = feedRepo.save(feed).getId();
		Owner owner = ownerRepo.findById(ownerId).get();
		owner.getFeeds().add(savedFeedId);
		ownerRepo.save(owner);
		return "OK";
	}

	public String likeFeed(String feedId, String ownerId) {
		Feed feed = feedRepo.findById(feedId).get();
		feed.getLikes().add(ownerId);
		Feed savedFeed = feedRepo.save(feed);
		return savedFeed.isOK();
	}

	public String unlikeFeed(String feedId, String ownerId) {
		Feed feed = feedRepo.findById(feedId).get();
		feed.getLikes().remove(ownerId);
		Feed savedFeed = feedRepo.save(feed);
		return savedFeed.isOK();
	}

}
