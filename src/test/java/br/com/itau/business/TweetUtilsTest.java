package br.com.itau.business;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.itau.model.Tweet;
import br.com.itau.util.DateUtils;
import br.com.itau.util.TweetUtils;
import twitter4j.QueryResult;
import twitter4j.Status;

@RunWith(MockitoJUnitRunner.Silent.class)
public class TweetUtilsTest {

	private static final Date CREATED_AT = new Date();
	private static final String TAG = "#tag";
	private static final long USER_ID = 123l;
	private static final String USER_SCREEN_NAME = "@test";
	private static final String USER_NAME = "Test Name";
	private static final String LANG = "pt";
	private static int FOLLOWERS_COUNT = 300;
	private static String MESSAGE = "test message";

	@Mock
	private QueryResult queryResult;

	@Mock(answer = Answers.RETURNS_DEEP_STUBS)
	private Status status;

	@Before
	public void setup() {
		when(queryResult.getTweets()).thenReturn(Collections.singletonList(status));
		when(status.getCreatedAt()).thenReturn(CREATED_AT);
		when(queryResult.getQuery()).thenReturn(TAG);
		when(status.getUser().getId()).thenReturn(USER_ID);
		when(status.getUser().getScreenName()).thenReturn(USER_SCREEN_NAME);
		when(status.getUser().getName()).thenReturn(USER_NAME);
		when(status.getLang()).thenReturn(LANG);
		when(status.getUser().getFollowersCount()).thenReturn(FOLLOWERS_COUNT);
		when(status.getText()).thenReturn(MESSAGE);
	}

	@Test
	public void testToTweet_CreatedAt() {
		Tweet tweet = TweetUtils.toTweet(queryResult).findFirst().get();
		assertEquals(DateUtils.toLocalDateTime(CREATED_AT), tweet.getCreated());
	}

	@Test
	public void testToTweet_Tag() {
		Tweet tweet = TweetUtils.toTweet(queryResult).findFirst().get();
		assertEquals(TAG, tweet.getTag());
	}

	@Test
	public void testToTweet_UserId() {
		Tweet tweet = TweetUtils.toTweet(queryResult).findFirst().get();
		assertEquals(String.valueOf(USER_ID), tweet.getUserID());
	}

	@Test
	public void testToTweet_UserScreenName() {
		Tweet tweet = TweetUtils.toTweet(queryResult).findFirst().get();
		assertEquals(USER_SCREEN_NAME, tweet.getUserScreenName());
	}

	@Test
	public void testToTweet_UserName() {
		Tweet tweet = TweetUtils.toTweet(queryResult).findFirst().get();
		assertEquals(USER_NAME, tweet.getUserName());
	}

	@Test
	public void testToTweet_Lang() {
		Tweet tweet = TweetUtils.toTweet(queryResult).findFirst().get();
		assertEquals(LANG, tweet.getLang());
	}

	@Test
	public void testToTweet_FollowersCount() {
		Tweet tweet = TweetUtils.toTweet(queryResult).findFirst().get();
		assertEquals(FOLLOWERS_COUNT, tweet.getUserFollowersCount());
	}

	@Test
	public void testToTweet_Message() {
		Tweet tweet = TweetUtils.toTweet(queryResult).findFirst().get();
		assertEquals(MESSAGE, tweet.getMessage());
	}
}
