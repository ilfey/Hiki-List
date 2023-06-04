package com.ilfey.shikimori.ui.profile.friends

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.ilfey.shikimori.base.BaseBottomSheetDialogFragment
import com.ilfey.shikimori.databinding.SheetListBinding
import com.ilfey.shikimori.di.network.models.Friend
import com.ilfey.shikimori.ui.profile.ProfileViewModel
import com.ilfey.shikimori.utils.dp
import com.ilfey.shikimori.utils.stringArgument
import com.ilfey.shikimori.utils.toast
import com.ilfey.shikimori.utils.widgets.VerticalSpaceItemDecorator
import com.ilfey.shikimori.utils.withArgs
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class FriendsBottomSheet : BaseBottomSheetDialogFragment<SheetListBinding>() {

    private val username by stringArgument(ARG_USERNAME)
    private val viewModel by activityViewModel<ProfileViewModel>()
    private val listAdapter = ListAdapter {
        context?.toast()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (username != null) {
            viewModel.getFriends(username!!)
        } else {
            Log.e(TAG, "onCreate: username cannot be null")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding.recycler) {
            adapter = listAdapter
            addItemDecoration(
                VerticalSpaceItemDecorator(context.dp(16))
            )
        }
    }

    override fun bindViewModel() {
        viewModel.friends.observe(viewLifecycleOwner, this::onFriendsUpdate)
    }

    private fun onFriendsUpdate(l: List<Friend>) {
        listAdapter.setList(l)
    }

    override fun onInflateView(inflater: LayoutInflater, container: ViewGroup?) =
        SheetListBinding.inflate(inflater, container, false)

    companion object {
        private const val TAG = "[FriendsBottomSheet]"

        private const val ARG_USERNAME = "username"

        fun show(fm: FragmentManager, username: String) = FriendsBottomSheet().withArgs(1) {
            putString(ARG_USERNAME, username)
        }.show(fm, TAG)
    }
}