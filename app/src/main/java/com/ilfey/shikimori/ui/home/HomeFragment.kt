package com.ilfey.shikimori.ui.home

import android.graphics.Color
import android.net.Uri
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import com.bumptech.glide.Glide
import com.ilfey.shikimori.R
import com.ilfey.shikimori.base.BaseFragment
import com.ilfey.shikimori.databinding.FragmentHomeBinding
import org.koin.android.ext.android.inject

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val viewModel by inject<HomeViewModel>()

    private val customTabsIntent = CustomTabsIntent.Builder().build()

    override fun bindViewModel() {
        viewModel.user.observe(viewLifecycleOwner) {

            Glide
                .with(this)
                .load(it.image.x160)
                .circleCrop()
                .into(binding.avatar)

            binding.age.text = getAge(it.full_years)

            if (it.website != null) {
                val text = getString(R.string.website)
                val spannableString = SpannableStringBuilder(text)

                spannableString.setSpan(
                    object : ClickableSpan() {
                        override fun onClick(widget: View) {
                            customTabsIntent.launchUrl(requireContext(), Uri.parse(it.website))
                        }

                        override fun updateDrawState(ds: TextPaint) {
                            super.updateDrawState(ds)
                            // Стиль ссылки
                            ds.isUnderlineText = true
                            ds.color = Color.BLUE
                        }
                    }, 0, text.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                binding.website.text = spannableString
                binding.website.movementMethod = LinkMovementMethod.getInstance()
            } else {
                binding.website.visibility = View.GONE
            }
        }
    }

    private fun getAge(age: Int): String {
        if (age % 10 == 1) {
            return getString(R.string.age_1, age)
        } else if (age % 10 in 2..4) {
            return getString(R.string.age_2, age)
        }

        return getString(R.string.age_3, age)
    }

    override fun onInflateView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentHomeBinding.inflate(inflater)
}