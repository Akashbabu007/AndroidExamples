package com.techhue.views

import android.content.Context
import android.view.View
import android.view.accessibility.AccessibilityEvent
import com.example.views.Season


class SeasonView(context: Context?) : View(context) {
    private var season: Season? = null
    /**
     * Listing 4-19: Broadcasting Accessibility Events
     */
    fun setSeason(_season: Season?) {
        season = _season
        sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED)
    }

    /**
     * Listing 4-20: Customizing Accessibility Event properties
     */
    override fun dispatchPopulateAccessibilityEvent(event: AccessibilityEvent): Boolean {
        super.dispatchPopulateAccessibilityEvent(event)
        return if (isShown) {
            var seasonStr: String = Season.valueOf(season)
            if (seasonStr.length > AccessibilityEvent.MAX_TEXT_LENGTH) seasonStr =
                seasonStr.substring(0, AccessibilityEvent.MAX_TEXT_LENGTH - 1)
            event.text.add(seasonStr)
            true
        } else false
    }
}