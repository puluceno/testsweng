package br.com.itau.service;

import static spark.Spark.after;
import static spark.Spark.get;

import java.util.Set;
import java.util.logging.Level;

import org.pmw.tinylog.Logger;

import br.com.itau.business.TweetCrawler;
import br.com.itau.business.TweetCrawlerImpl;
import br.com.itau.data.TagsRepository;
import br.com.itau.data.TweetRepository;
import br.com.itau.resources.CorsFilter;
import br.com.itau.resources.MongoResource;
import br.com.itau.util.TweetUtils;

public class Main {
	private static final TweetCrawler tweet = new TweetCrawlerImpl();

	public static void main(String[] args) {

		get("/tag", (req, res) -> {
			return TweetUtils.toJson(tweet.getTweetByTag(req.queryParams()));
		});

		get("/top5", (req, res) -> {
			return TweetUtils.toJson(tweet.getTopFiveUsersByFollowersCount());
		});

		get("/tagtotal", (req, res) -> {
			return TweetUtils.toJson(tweet.getTweetTotalsByTags("pt"));
		});

		get("/hourly", (req, res) -> {
			return TweetUtils.toJson(tweet.getTweetsByHour());
		});

		get("/tags", (req, res) -> {
			return TagsRepository.findAllTags();
		});

		after((request, response) -> {
			response.header("Content-Encoding", "gzip");
		});

		init();
	}

	private static void init() {
		clearLogs();
		CorsFilter.apply();

		TweetCrawler crawl = new TweetCrawlerImpl();
		Set<String> tags = TagsRepository.findAllTags();

		TweetRepository.dropCollection();
		MongoResource.generateIndexes();

		crawl.getTweetByTag(tags).forEach(TweetRepository::saveTweet);
		Logger.info("Initialization successful!");
	}

	/**
	 * Prevent MongoDB for creating log entries in every request
	 */
	private static void clearLogs() {
		java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.SEVERE);
		java.util.logging.Logger.getLogger("org.apache.http.client.protocol.ResponseProcessCookies")
				.setLevel(Level.SEVERE);
		java.util.logging.Logger.getLogger("org.mongodb.driver").setLevel(Level.SEVERE);
	}
}
