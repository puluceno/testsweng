package br.com.itau.resources;

import org.bson.Document;
import org.pmw.tinylog.Logger;

import com.mongodb.MongoClient;
import com.mongodb.MongoCommandException;
import com.mongodb.client.MongoDatabase;

public class MongoResource {

	private static volatile MongoClient client;
	private static volatile MongoDatabase db;

	public static synchronized MongoClient getClient() {
		if (client == null)
			client = new MongoClient("localhost", 27017);

		return client;
	}

	public static synchronized MongoDatabase getDataBase(String database) {
		if (db == null) {
			if (client == null)
				getClient();
			db = client.getDatabase(database);
		}
		return db;
	}

	public static void createIndex(String collection, String field) {
		try {
			MongoResource.getDataBase("tweet").getCollection(collection).createIndex(new Document(field, 1));
			Logger.info("Index created in the colletion '{}' for field '{}'.", collection, field);
		} catch (Exception e) {
			if (e instanceof MongoCommandException)
				Logger.error("Could not create index: key too large to index.");
			else
				Logger.trace(e);
		}
	}

	public static void createCoumpoundIndex(String collection, String... fields) {
		try {
			Document index = new Document();
			for (int i = 0; i < fields.length; i++) {
				index.append(fields[i], 1);
			}
			MongoResource.getDataBase("tweet").getCollection(collection).createIndex(index);
			Logger.info("Index created in the colletion '{}' for field '{}'.", collection, index.entrySet());
		} catch (Exception e) {
			if (e instanceof MongoCommandException)
				Logger.error("Could not create index: key too large to index.");
			else
				Logger.trace(e);
		}
	}

	public static void generateIndexes() {
		createIndex("tweet", "created");
		createIndex("tweet", "tag");
		createIndex("tweet", "lang");
		createIndex("tweet", "userFollowersCount");
		createIndex("tweet", "userID");
		createIndex("tweet", "userScreenName");
		createIndex("tweet", "userName");
		createCoumpoundIndex("tweet", "created", "lang", "tag");
	}
}