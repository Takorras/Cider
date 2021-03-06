package info.kotlin.kotako.cider.contract

interface PostActivityContract {
    fun finish()
    fun makeToast(msg: String)
    fun inputText():String
    fun showProgressbar()
    fun hideProgressbar()
}