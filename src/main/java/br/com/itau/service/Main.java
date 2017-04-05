package br.com.itau.service;

import static spark.Spark.after;
import static spark.Spark.get;

import java.util.Set;
import java.util.logging.Level;

import br.com.itau.business.TweetCrawler;
import br.com.itau.business.TweetCrawlerImpl;
import br.com.itau.data.TagsRepository;
import br.com.itau.data.TweetRepository;
import br.com.itau.resources.CorsFilter;

public class Main {
	public static void main(String[] args) {

		get("/tag", (req, res) -> {
			return "tag";
		});

		get("/top5", (req, res) -> {
			return "5";
		});

		get("/tagtotal", (req, res) -> {
			return "tag total";
		});

		get("/hourly", (req, res) -> {
			return "hourly";
		});

		get("tags", (req, res) -> {
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
		crawl.getTweetByTag(tags).forEach(TweetRepository::saveTweet);
		System.out.println("**************************************");
		TweetRepository.findTweetsByTags(tags.toArray(new String[tags.size()])).forEach(t -> System.out.print(t));

	}

	private static void clearLogs() {
		java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.SEVERE);
		java.util.logging.Logger.getLogger("org.apache.http.client.protocol.ResponseProcessCookies")
				.setLevel(Level.SEVERE);
		java.util.logging.Logger.getLogger("org.mongodb.driver").setLevel(Level.SEVERE);
	}
}
