package br.com.itau.business;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import br.com.itau.model.Tweet;
import br.com.itau.model.TweetUtils;
import twitter4j.Query;
import twitter4j.Query.ResultType;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class TweetCrawlerImpl implements TweetCrawler {

	private static int MAX_RESULT = 100;

	public static void main(String[] args) {
		TweetCrawler crawl = new TweetCrawlerImpl();
		Set<String> h = new HashSet<String>(Arrays.asList("#floripa", "#nature"));
		crawl.getTweetByTag(h);
	}

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
	public Query createQuery(String tag) {
		Query query = new Query(tag);
		query.setCount(MAX_RESULT);
		query.setResultType(ResultType.recent);
		return query;
	}

}
