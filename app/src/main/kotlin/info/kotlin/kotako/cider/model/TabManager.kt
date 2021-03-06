package info.kotlin.kotako.cider.model

import android.os.Bundle
import android.support.v4.app.Fragment
import info.kotlin.kotako.cider.R
import info.kotlin.kotako.cider.model.entity.Tab
import info.kotlin.kotako.cider.model.entity.TabList
import info.kotlin.kotako.cider.view.fragment.DirectMessagesFragment
import info.kotlin.kotako.cider.view.fragment.TimelineFragment
import io.realm.Realm

class TabManager {
    companion object {
        // タグ
        val TIMELINE = "timeline"
        val DIRECT_MESSAGES = "direct_messages"
        val TAG_COLLECTIONS = "tag_collections"
        val TARGET = "Target"
        val TARGET_ID = "TargetId"
        val MENTION = "Mention"
        val USERLIST = "Userlist"
        val SEARCH = "Search"

        // デフォルトタブ
        val timelineTabDefault = Tab("Home", TIMELINE, null, R.mipmap.home_grey)
        val mentionTabDefault = Tab("Mention", MENTION, null, R.mipmap.notifications_grey)
        val dmTabDefault = Tab("Direct Messages", DIRECT_MESSAGES, null, R.mipmap.email_grey)
        val favoriteTabDefault: Tab = Tab("Favorite", null, null, R.mipmap.favorite_grey)
        fun listTabDefault(listId: Long, listName: String): Tab = Tab(listName, USERLIST, listId.toString(), R.mipmap.view_list_grey)

        fun getFragmentByTab(tab: Tab): Fragment = when (tab.target) {
            TIMELINE -> TimelineFragment.newInstance()
            MENTION -> TimelineFragment.newInstance(Bundle().apply { putString(TARGET, MENTION) })
            USERLIST -> TimelineFragment.newInstance(Bundle().apply {
                putString(TARGET, USERLIST)
                putString(TARGET_ID, tab.targetId)
            })
            SEARCH -> TimelineFragment.newInstance(Bundle().apply {
                putString(TARGET, SEARCH)
                putString(TARGET_ID, tab.targetId)
            })
            DIRECT_MESSAGES -> DirectMessagesFragment.newInstance()
            TAG_COLLECTIONS -> Fragment()
            else -> Fragment()
        }

        fun getTabList(): ArrayList<Tab> {
            val result = ArrayList<Tab>()
            Realm.getDefaultInstance().use { realm ->
                realm.where(TabList::class.java).findFirst()?.tabList?.forEach {
                    result.add(Tab(it.name, it.target, it.targetId, it.icon))
                }
            }
            return result
        }

        fun updateTabList(tabList: ArrayList<Tab>): ArrayList<Tab> {
            Realm.getDefaultInstance().use { realm ->
                realm.where(TabList::class.java).findFirst()?.let {
                    it.tabList.clear()
                    it.tabList.addAll(tabList)
                }
            }
            return Companion.getTabList()
        }
    }
}