package br.com.itau.service;

import static spark.Spark.after;
import static spark.Spark.get;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.stream.Collectors;

import org.bson.Document;
import org.pmw.tinylog.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.client.MongoCollection;

import br.com.itau.business.TweetCrawler;
import br.com.itau.business.TweetCrawlerImpl;
import br.com.itau.data.TagsRepository;
import br.com.itau.resources.CorsFilter;
import br.com.itau.resources.MongoResource;
import br.com.itau.util.TweetUtils;

public class Main {
	private static final TweetCrawler tweet = new TweetCrawlerImpl();
	private static MongoCollection<Document> tweetCollection = MongoResource.getDataBase("tweet")
			.getCollection("tweet");

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
			Date begin = new Date();
			List<Document> list = tweetCollection.find().into(new ArrayList<Document>());

			Map<Object, Long> collect = list.stream().collect(Collectors.groupingBy(doc -> {
				int day = (int) ((Document) ((Document) doc.get("created")).get("date")).get("day");
				int hour = (int) ((Document) ((Document) doc.get("created")).get("time")).get("hour");
				Map<String, Integer> dayHour = new HashMap<String, Integer>();
				dayHour.put("day", day);
				dayHour.put("hour", hour);
				return dayHour;
			}, Collectors.counting()));

			Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
			System.out.println(new Date().getTime() - begin.getTime());
			return gson.toJson(collect);
		});

		get("/hourly2", (req, res) -> {
			Date begin = new Date();
			Object json = TweetUtils.toJson(tweet.getTweetsByHour());
			System.out.println(new Date().getTime() - begin.getTime());
			return json;
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

		// TweetCrawler crawl = new TweetCrawlerImpl();
		// Set<String> tags = TagsRepository.findAllTags();

		// TweetRepository.dropCollection();
		// MongoResource.generateIndexes();

		// crawl.getTweetByTag(tags).forEach(TweetRepository::saveTweet);
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
