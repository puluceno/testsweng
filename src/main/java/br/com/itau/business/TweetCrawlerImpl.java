package br.com.itau.business;

import java.util.List;
import java.util.Set;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class TweetCrawlerImpl implements TweetCrawler {

	public static void main(String[] args) {
		Twitter twitter = TwitterFactory.getSingleton();
		Query query = new Query(
				"#brasil OR #brazil OR #brasil2017 OR #brazil2017 OR #carnaval OR #tourism OR #bahia OR #riodejaneiro OR #saopaulo");
		query.setCount(100);
		query.setLang("pt");
		QueryResult result = null;
		try {
			result = twitter.search(query);
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		result.getTweets().stream().forEach(status -> System.out.println(
				status.getCreatedAt() + " : " + "@" + status.getUser().getScreenName() + ":" + status.getText()));

	}

	@Override
	public List<Object> getTweetByTag(Set<String> tags) {
		tags.stream().filter(this::isValidTag).map(this::createQueryForTag).map(query -> {
			try {
				LOGGER.info("Performing search for tag: {}.", query.getQuery());
				return twitter.search().search(query);
			} catch (TwitterException e) {
				throw new RuntimeException(e);
			}
		}).flatMap(this::convertStatusToSimpleTweet).forEach(repository::saveTweet);
	}

	@Override
	public boolean isTagValid(String tag) {

	}

}
