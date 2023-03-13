package com.ilfey.shikimori.ui.home

import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout

class HomeBehavior : CoordinatorLayout.Behavior<ImageView>() {

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: ImageView,
        dependency: View
    ): Boolean {
        return dependency is Toolbar
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: ImageView,
        dependency: View
    ): Boolean {
        modifyAvatarDependingDependencyState(parent, child, dependency)
        return true
    }

    private fun modifyAvatarDependingDependencyState(
        parent: CoordinatorLayout,
        avatar: ImageView,
        dependency: View
    ) {
        avatar.y = dependency.y
//          avatar.setBlahBlah(dependency.blah / blah)
    }
}