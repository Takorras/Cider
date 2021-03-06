package info.kotlin.kotako.cider.contract

import info.kotlin.kotako.cider.model.entity.Tweet

interface TimelineFragmentContract {
    fun startProfileActivity()
    fun addTweet(tweet: Tweet)
    fun addTweetList(tweet: List<Tweet>)
    fun tweetListSize():Int
    fun clearTweetList()
    fun showSnackBar(msg:String)
    fun showProgressBar()
    fun hideProgressBar()
}