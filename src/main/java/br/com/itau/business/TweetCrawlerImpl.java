package br.com.itau.business;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.bson.Document;

import br.com.itau.data.TweetRepository;
import br.com.itau.model.Tweet;
import br.com.itau.util.TweetUtils;
import twitter4j.Query;
import twitter4j.Query.ResultType;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class TweetCrawlerImpl implements TweetCrawler {

	private static int MAX_RESULT = 100;

	@Override
	public List<Tweet> getTweetByTag(Set<String> tags) {
		Twitter twitter = TwitterFactory.getSingleton();

		return tags.stream().filter(TweetUtils::isTagValid).map(this::createQuery).map(query -> {
			try {
				return twitter.search(query);
			} catch (TwitterException e) {
				throw new RuntimeException(e);
			}
		}).flatMap(TweetUtils::toTweet).collect(Collectors.toList());
	}

	@Override
	public List<Tweet> getTopFiveUsersByFollowersCount() {
		return TweetRepository.findTweetsByFollowersCount();
	}

	@Override
	public List<Document> getTweetTotalsByTags(String lang) {
		return TweetRepository.findTweetTotalsByTags(lang);
	}

	@Override
	public List<Document> getTweetsByHour() {
		return TweetRepository.findTweetGroupedByHour();
	}

	private Query createQuery(String tag) {
		Query query = new Query(tag);
		query.setCount(MAX_RESULT);
		query.setResultType(ResultType.recent);
		return query;
	}
}
