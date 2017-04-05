package br.com.itau.data;

import static com.mongodb.client.model.Sorts.descending;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.bson.Document;
import org.pmw.tinylog.Logger;

import com.mongodb.client.MongoCollection;

import br.com.itau.model.Tweet;
import br.com.itau.model.TweetUtils;
import br.com.itau.resources.MongoResource;

public class TweetRepository {

	private static MongoCollection<Document> tweetCollection = MongoResource.getDataBase("tweet")
			.getCollection("tweet");

	public static List<Tweet> findAllTweets() {
		return tweetCollection.find().into(new ArrayList<Document>()).stream().map(TweetUtils::toTweet)
				.collect(Collectors.toList());
	}

	public static List<Tweet> findTweetsByTags(String... tags) {
		List<Tweet> tweets = null;

		if (tags != null && tags.length > 0 && Arrays.asList(tags).stream().allMatch(TweetUtils::isTagValid)) {
			tweets = tweetCollection.find().into(new ArrayList<Document>()).stream().map(TweetUtils::toTweet)
					.collect(Collectors.toList());
		} else {
			Logger.error("Parameter contains invalid tag to look for");
		}
		return tweets;
	}

	public static List<Tweet> findTweetsByFollowersCount() {
		return tweetCollection.find().sort(descending("userFollowersCount")).limit(5).into(new ArrayList<Document>())
				.stream().map(TweetUtils::toTweet).collect(Collectors.toList());
	}

	public static List<Document> findTweetTotalsByTags(String lang) {
		try {
			Document match = new Document("$match", new Document("lang", lang));

			Document group = new Document("$group",
					new Document("_id", new Document("tag", "$tag").append("lang", "$lang")).append("count",
							new Document("$sum", 1)));

			return tweetCollection.aggregate(Arrays.asList(match, group)).into(new ArrayList<Document>());
		} catch (Exception e) {
			Logger.trace(e);
			List<Document> error = new ArrayList<Document>();
			error.add(new Document("error", e.getMessage()));
			return error;
		}
	}

	public static void saveTweet(Tweet tweet) {
		tweetCollection.insertOne(TweetUtils.toDocument(tweet));
	}

	public static void dropCollection() {
		tweetCollection.drop();
	}
}
