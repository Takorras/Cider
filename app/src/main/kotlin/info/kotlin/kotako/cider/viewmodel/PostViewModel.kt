package info.kotlin.kotako.cider.viewmodel

import android.view.View
import com.twitter.sdk.android.core.TwitterCore
import info.kotlin.kotako.cider.contract.PostActivityContract
import info.kotlin.kotako.cider.model.RestAPIClient
import info.kotlin.kotako.cider.model.entity.Tweet
import info.kotlin.kotako.cider.rx.DefaultObserver
import io.reactivex.schedulers.Schedulers

class PostViewModel(private val postView: PostActivityContract, private val replyTo: Tweet? = null) {

    fun post() {
        postView.showProgressbar()
        RestAPIClient(TwitterCore.getInstance().sessionManager.activeSession)
                .PostObservable()
                .post(postView.inputText(), replyTo?.id, null, null)
                .subscribeOn(Schedulers.newThread())
                .subscribe(DefaultObserver(
                        error = {
                            postView.hideProgressbar()
                            postView.makeToast("失敗した")
                        },
                        completed = {
                            postView.hideProgressbar()
                            postView.finish()
                        }))
    }

    fun onCameraClicked(view: View) {
//      TODO:写真をとる
        postView.makeToast("camera icon clicked")
    }

    fun onMediaClicked(view: View) {
//      TODO:写真を選択して追加
        postView.makeToast("media icon clicked")
    }

    fun onMusnoteClicked(view: View) {
//      TODO:nowPlayingをする
        postView.makeToast("musnonte icon clicked")
    }

    fun onLocationClicked(view: View) {
//      TODO:現在位置を送信
        postView.makeToast("location icon clicked")
    }
}
