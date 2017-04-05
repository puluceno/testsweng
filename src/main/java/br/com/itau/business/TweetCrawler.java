package br.com.itau.business;

import java.util.List;
import java.util.Set;

import br.com.itau.model.Tweet;

public interface TweetCrawler {

	/**
	 * Finds the latest 100 tweets that contains the given tag.
	 * 
	 * @param tag
	 *            Hashtag tag (#tag)
	 * @return List of tweets
	 */
	public List<Tweet> getTweetByTag(Set<String> tags);

	/**
	 * Finds the top 5 users with most followers
	 * 
	 * @return List of tweets containing the information about the top 5 users.
	 */
	public List<Tweet> getTopFiveUsersByFollowersCount();

	public Object getTweetTotalsByTags(String lang);

}
