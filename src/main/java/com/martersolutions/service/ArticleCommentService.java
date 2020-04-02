/**
 * 
 */
package com.martersolutions.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.martersolutions.model.Article;
import com.martersolutions.model.Comment;
import com.martersolutions.model.RNComment;
import com.martersolutions.repository.ArticleRepository;
import com.martersolutions.repository.CommentRepository;

/**
 * @author prakash Jun 18, 2018
 */
@Service
public class ArticleCommentService {
	@Autowired
	private CommentRepository cmtRepo;
	@Autowired
	private ArticleRepository articleRepo;

	public List<RNComment> getAllArticleComments(String articleId) {

		Article article = articleRepo.findById(articleId).get();

		List<RNComment> commentsToSend = new ArrayList<RNComment>();

		List<String> allComments = article.getComments();

		Iterator<String> it = allComments.iterator();

		while (it.hasNext()) {
			String cmtId = (String) it.next();

			Comment cmt = cmtRepo.findById(cmtId).get();

			RNComment rnComment = new RNComment(cmt.getId(), cmt.getCommentorId(), cmt.getCommentorName(),
					cmt.getCreatedDate(), cmt.getEditedDate(), cmt.getMessage(), cmt.getLikes(), cmt.getReplies());
			commentsToSend.add(rnComment);
		}
		return commentsToSend;
	}

	public List<RNComment> getAllReplies(String cmtId) {
		List<RNComment> rnReplies = new ArrayList<RNComment>();

		Comment savedCmt = cmtRepo.findById(cmtId).get();

		for (String replyId : savedCmt.getReplies()) {
			Comment reply = cmtRepo.findById(replyId).get();

			RNComment rnReply = new RNComment(reply.getId(), reply.getCommentorId(), reply.getCommentorName(),
					reply.getCreatedDate(), reply.getEditedDate(), reply.getMessage(), reply.getLikes());

			rnReplies.add(rnReply);
		}

		return rnReplies;
	}

	public RNComment getCommentById(String cmtId) {
		Optional<Comment> optComment = cmtRepo.findById(cmtId);

		if (!optComment.isPresent()) {
			throw new NullPointerException("No Such Comment Id");
		}
		Comment cmt = optComment.get();
		RNComment rnComment = new RNComment(cmt.getId(), cmt.getCommentorId(), cmt.getCommentorName(),
				cmt.getCreatedDate(), cmt.getEditedDate(), cmt.getMessage(), cmt.getLikes());
		return rnComment;
	}

	public String writeComment(String articleId, String message, String ownerId, String name) {

		Comment comment = new Comment();
		comment.setCreatedDate(LocalDateTime.now().toString());
		comment.setCommentorId(ownerId);
		comment.setCommentorName(name);
		comment.setMessage(message);
		comment.setType("COMMENT_TYPE");
		String savedCmtId = cmtRepo.save(comment).getId();

		Optional<Article> article = articleRepo.findById(articleId);
		if (!article.isPresent()) {
			throw new NullPointerException("No Such Article Id");
		}
		article.get().getComments().add(savedCmtId);
		Article savedArticle = articleRepo.save(article.get());

		return savedArticle.isOK();
	}

	public String writeReply(String message, String cmtId, String ownerId, String name) {

		Comment reply = new Comment();
		reply.setCreatedDate(LocalDateTime.now().toString());
		reply.setCommentorId(ownerId);
		reply.setCommentorName(name);
		reply.setMessage(message);
		reply.setType("REPLY_TYPE");
		String savedCmtId = cmtRepo.save(reply).getId();

		Comment comment = cmtRepo.findById(cmtId).get();
		comment.getReplies().add(savedCmtId);
		Comment savedComment = cmtRepo.save(comment);

		return savedComment.isOK();
	}

	public String likeComment(String cmtId, String ownerId) {

		Comment comment = cmtRepo.findById(cmtId).get();
		comment.getLikes().add(ownerId);
		Comment savedComment = cmtRepo.save(comment);
		return savedComment.isOK();
	}

	public String unlikeComment(String cmtId, String ownerId) {

		Comment comment = cmtRepo.findById(cmtId).get();
		comment.getLikes().remove(ownerId);
		Comment savedComment = cmtRepo.save(comment);
		return savedComment.isOK();
	}

}
