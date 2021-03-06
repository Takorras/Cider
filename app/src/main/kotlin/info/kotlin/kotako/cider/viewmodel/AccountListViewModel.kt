package info.kotlin.kotako.cider.viewmodel

import com.twitter.sdk.android.core.Result
import com.twitter.sdk.android.core.TwitterCore
import com.twitter.sdk.android.core.TwitterSession
import info.kotlin.kotako.cider.contract.AccountListActivityContract
import info.kotlin.kotako.cider.model.AccountManager
import info.kotlin.kotako.cider.model.entity.Account
import io.realm.Realm

class AccountListViewModel(private val accountListActivity: AccountListActivityContract ) {

    val realm: Realm = Realm.getDefaultInstance()
    private val twitterManager: TwitterCore = TwitterCore.getInstance()

    fun setAccountView() {
//      Realmから認証したアカウントを取得し、RecyclerViewにセットする
        if (twitterManager.sessionManager.activeSession == null) return
        accountListActivity.resetAccountView()
        realm.let {
            val allAccount = it.where(Account::class.java).notEqualTo("userId", twitterManager.sessionManager.activeSession.userId).findAll()
            val activeAccount = it.where(Account::class.java).equalTo("userId", twitterManager.sessionManager.activeSession.userId).findAll().first()

            accountListActivity.setAccountView(activeAccount)
            allAccount.forEach { accountListActivity.setAccountView(it) }
        }
    }

    fun onItemClicked(account:Account) {
//      アクティブになっている以外のアカウントを選択すると、そのアカウントをアクティブに切り替える
        if (account.userId == AccountManager.currentAccount()?.userId) return
        AccountManager.changeCurrentAccount(account)
        setAccountView()
        accountListActivity.accountChanged = true
    }

    fun onTokenReceived(result: Result<TwitterSession>) {
//      Realmに追加、Viewにアカウントを示すセルを追加する（アカウント情報を取得）
//      Realmにアカウント情報を追加、既に存在していた場合は更新する
        val account = Account(result.data.userId, result.data.userName, result.data.authToken.token, result.data.authToken.secret)
        realm.let { it.executeTransaction { it.copyToRealmOrUpdate(account) } }

//      画面をリフレッシュして追加する
        accountListActivity.accountChanged = true
        accountListActivity.resetAccountView()
        setAccountView()
    }
}