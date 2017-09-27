package info.kotlin.kotako.cider.model.entity

import com.twitter.sdk.android.core.models.Tweet
import java.io.Serializable

class Tweet(tweet: Tweet):Serializable {

    val id = tweet.id
    val createdAt = tweet.createdAt
    val favoriteCount = tweet.favoriteCount
    val favorited = tweet.favorited
    val retweetCount = tweet.retweetCount
    val retweeted = tweet.retweeted
    var retweetedStatus: info.kotlin.kotako.cider.model.entity.Tweet? = if (retweeted) Tweet(tweet.retweetedStatus) else null
    val user = tweet.user
    val user_sn = "@" + tweet.user.screenName
    val text = tweet.text
}