package br.com.itau.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.itau.data.TweetRepository;
import twitter4j.Query;
import twitter4j.Query.ResultType;
import twitter4j.QueryResult;
import twitter4j.Status;

@SuppressWarnings("static-access")
@RunWith(MockitoJUnitRunner.Silent.class)
public class TweetBusinessTest {

	private TweetBusinessImpl tweetBusiness;
	private static int MAX_RESULT = 100;

	@Mock
	private QueryResult queryResult;

	@Mock(answer = Answers.RETURNS_DEEP_STUBS)
	private Status status;

	@Mock
	private TweetRepository repo;

	@Before
	public void setup() {
		tweetBusiness = new TweetBusinessImpl();
	}

	@Test
	public void testCreateQuery_Type() {
		assertTrue(tweetBusiness.createQuery("test") instanceof Query);
	}

	@Test
	public void testCreateQuery_Count() {
		assertEquals(tweetBusiness.createQuery("test").getCount(), MAX_RESULT);
	}

	@Test
	public void testCreateQuery_ResultType() {
		assertEquals(tweetBusiness.createQuery("test").getResultType(), ResultType.recent);
	}

	@Test
	public void testIsTagValid_NoHashtag() {
		assertFalse(tweetBusiness.isTagValid("floripa"));
	}

	@Test
	public void testIsTagValid_Empty() {
		assertFalse(tweetBusiness.isTagValid(""));
	}

	@Test
	public void testIsTagValid_OneChar() {
		assertFalse(tweetBusiness.isTagValid("a"));
	}

	@Test
	public void testIsTagValid_OneChar2() {
		assertFalse(tweetBusiness.isTagValid("#"));
	}

	@Test
	public void testIsTagValid_Null() {
		assertFalse(tweetBusiness.isTagValid(null));
	}

	@Test
	public void testIsTagValid_Valid() {
		assertTrue(tweetBusiness.isTagValid("#floripa"));
	}

}
