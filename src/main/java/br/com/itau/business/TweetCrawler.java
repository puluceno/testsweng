package br.com.itau.business;

import java.util.List;
import java.util.Set;

import org.bson.Document;

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

	/**
	 * Finds the number of tweets for each tag for the given language.
	 * 
	 * @param lang
	 *            String language to be searched for.
	 * @return List of objects containing tag, language and count.
	 */
	public List<Document> getTweetTotalsByTags(String lang);

	/**
	 * Groups and counts the tweets by hour.
	 * 
	 * @return List of objects containing the day of the month and the number of
	 *         tweets on that day.
	 */
	public List<Document> getTweetsByHour();

}
