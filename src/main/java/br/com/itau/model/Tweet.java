package br.com.itau.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.bson.types.ObjectId;

public class Tweet implements Serializable {

	private static final long serialVersionUID = 1632484229118352245L;

	private ObjectId id;
	private LocalDateTime created;
	private String tag;
	private String userID;
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
	public Tweet(LocalDateTime created, String tag, String userID, String userScreenName, String userName, String lang,
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

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((created == null) ? 0 : created.hashCode());
		result = prime * result + ((lang == null) ? 0 : lang.hashCode());
		result = prime * result + ((tag == null) ? 0 : tag.hashCode());
		result = prime * result + ((userID == null) ? 0 : userID.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Tweet))
			return false;
		Tweet other = (Tweet) obj;
		if (created == null) {
			if (other.created != null)
				return false;
		} else if (!created.equals(other.created))
			return false;
		if (lang == null) {
			if (other.lang != null)
				return false;
		} else if (!lang.equals(other.lang))
			return false;
		if (tag == null) {
			if (other.tag != null)
				return false;
		} else if (!tag.equals(other.tag))
			return false;
		if (userID == null) {
			if (other.userID != null)
				return false;
		} else if (!userID.equals(other.userID))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Tweet [Created at: " + created + ", Tag: " + tag + ", User ID: " + userID + ", Twitter Name: @"
				+ userScreenName + ", User Name: " + userName + ", Tweet Language: " + lang + ", User has "
				+ userFollowersCount + " followers.]";
	}

}
