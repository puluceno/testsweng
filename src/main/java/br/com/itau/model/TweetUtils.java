package br.com.itau.model;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bson.Document;
import org.pmw.tinylog.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

import twitter4j.QueryResult;

public class TweetUtils {
	private static JsonParser parser = new JsonParser();

	public static Stream<Tweet> toTweet(QueryResult queryResult) {
		return queryResult.getTweets().stream()
				.map(tweet -> new Tweet(tweet.getCreatedAt(), queryResult.getQuery(), tweet.getUser().getId(),
						tweet.getUser().getScreenName(), tweet.getUser().getName(), tweet.getLang(),
						tweet.getUser().getFollowersCount()))
				.collect(Collectors.toList()).stream();
	}

	public static Tweet toTweet(Document doc) {
		if (doc != null) {
			try {
				JsonObject obj = parser.parse(doc.toJson()).getAsJsonObject();
				return (new Gson()).fromJson(obj, Tweet.class);
			} catch (Exception e) {
				Logger.error("Failed to parse Tweet with id {}", doc.get("_id"));
				Logger.trace(e);
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static Document toDocument(Tweet tweet) {
		String json = new Gson().toJson(tweet);
		DBObject parse = (DBObject) JSON.parse(json);
		return new Document(parse.toMap());
	}

	public static boolean isTagValid(String tag) {
		return (tag.length() > 1 && tag.startsWith("#"));
	}
}
