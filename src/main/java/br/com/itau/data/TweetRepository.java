package br.com.itau.data;

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

	public static void saveTweet(Tweet tweet) {
		tweetCollection.insertOne(TweetUtils.toDocument(tweet));
	}
}
