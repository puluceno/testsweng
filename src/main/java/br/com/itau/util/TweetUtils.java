package br.com.itau.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bson.Document;
import org.pmw.tinylog.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

import br.com.itau.model.Tweet;
import twitter4j.QueryResult;

public class TweetUtils {
	private static JsonParser parser = new JsonParser();

	public static Stream<Tweet> toTweet(QueryResult queryResult) {

		return queryResult.getTweets().stream()
				.map(tweet -> new Tweet(DateUtils.toLocalDateTime(tweet.getCreatedAt()), queryResult.getQuery(),
						String.valueOf(tweet.getUser().getId()), tweet.getUser().getScreenName(),
						tweet.getUser().getName(), tweet.getLang(), tweet.getUser().getFollowersCount()))
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

	public static Object toJson(Object object) {
		if (object instanceof Tweet)
			return new Gson().toJson(object);
		if (object instanceof Document)
			return ((Document) object).toJson();
		if (object instanceof ArrayList<?>) {
			List<String> objList = new ArrayList<String>();
			((ArrayList<?>) object).forEach(document -> objList.add((String) toJson(document)));
			return objList;
		}

		return object == null ? "" : object.toString();
	}

	public static boolean isTagValid(String tag) {
		return (tag.length() > 1 && tag.startsWith("#"));
	}
}
