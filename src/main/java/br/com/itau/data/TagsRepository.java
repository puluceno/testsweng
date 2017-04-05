package br.com.itau.data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bson.Document;

import com.mongodb.client.MongoCollection;

import br.com.itau.resources.MongoResource;

public class TagsRepository {
	private static MongoCollection<Document> tagsCollection = MongoResource.getDataBase("tweet").getCollection("tags");

	@SuppressWarnings("unchecked")
	public static Set<String> findAllTags() {
		return new HashSet<String>(tagsCollection.find().first().get("tags", List.class));
	}
}
