package br.com.itau.model;

import java.io.Serializable;
import java.util.Date;

import org.bson.types.ObjectId;

public class Tweet implements Serializable {

	private static final long serialVersionUID = 1632484229118352245L;

	private ObjectId id;
	private Date created;
	private String tag;
	private long userID;
	private String userScreenName;
	private String userName;
	private String lang;
	private int userFollowersCount;

	/**
	 * Empty constructor
	 */
	public Tweet() {
	}

	/**
	 * @param created
	 * @param tag
	 * @param userID
	 * @param userScreenName
	 * @param userName
	 * @param lang
	 * @param userFollowersCount
	 */
	public Tweet(Date created, String tag, long userID, String userScreenName, String userName, String lang,
			int userFollowersCount) {
		this.created = created;
		this.tag = tag;
		this.userID = userID;
		this.userScreenName = userScreenName;
		this.userName = userName;
		this.lang = lang;
		this.userFollowersCount = userFollowersCount;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public long getUserID() {
		return userID;
	}

	public void setUserID(long userID) {
		this.userID = userID;
	}

	public String getUserScreenName() {
		return userScreenName;
	}

	public void setUserScreenName(String userScreenName) {
		this.userScreenName = userScreenName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public int getUserFollowersCount() {
		return userFollowersCount;
	}

	public void setUserFollowersCount(int userFollowersCount) {
		this.userFollowersCount = userFollowersCount;
	}

	@Override
	public String toString() {
		return "Tweet [Created at: " + created + ", Tag: " + tag + ", User ID: " + userID + ", Twitter Name: @"
				+ userScreenName + ", User Name: " + userName + ", Tweet Language: " + lang + ", User has "
				+ userFollowersCount + " followers.]";
	}

}
