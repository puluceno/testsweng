package br.com.itau.business;

import java.util.List;
import java.util.Set;

import br.com.itau.model.Tweet;
import twitter4j.Query;

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
	 * Verifies if the tag is not empty and if it contains the hashtag
	 * character.
	 * 
	 * @param tag
	 *            Input tag that will be verified.
	 * @return true if the tag is valid. False otherwise.
	 */
	// boolean isTagValid(String tag);

	/**
	 * Build a query for the given tag.
	 * 
	 * @param tag
	 *            String tag (#tag).
	 * @return A Query for the given tag.
	 */
	Query createQuery(String tag);

}
