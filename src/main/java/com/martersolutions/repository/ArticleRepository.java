package com.martersolutions.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.martersolutions.model.Article;

public interface ArticleRepository extends MongoRepository<Article, String> {

}
