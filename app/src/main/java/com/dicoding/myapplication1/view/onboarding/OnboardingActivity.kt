package com.dicoding.myapplication1.view.onboarding

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.dicoding.myapplication1.R
import com.dicoding.myapplication1.databinding.ActivityOnboardingBinding
import com.dicoding.myapplication1.view.login.LoginActivity


class OnboardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingBinding
    private lateinit var dots: Array<TextView>
    private lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewPagerAdapter = ViewPagerAdapter(this)
        binding.slideViewPager.adapter = viewPagerAdapter

        binding.nextbtn.setOnClickListener {
            if (binding.slideViewPager.currentItem < (viewPagerAdapter.count - 1)) {
                binding.slideViewPager.currentItem = getitem(1)
            } else {
                val intent = Intent(this@OnboardingActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        binding.slideViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
                //Do Nothing
            }

            override fun onPageSelected(position: Int) {
                setUpIndicator(position)
                binding.nextbtn.visibility = if (position == viewPagerAdapter.count - 1) View.VISIBLE else View.INVISIBLE
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                // Do Nothing
            }
        })

        binding.skipButton.setOnClickListener {
            val intent = Intent(this@OnboardingActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        supportActionBar?.hide()
    }

    private fun setUpIndicator(position: Int) {
        dots = Array(3) { TextView(this) }
        binding.indicatorLayout.removeAllViews()
        for (i in dots.indices) {
            dots[i] = TextView(this).apply {
                text = Html.fromHtml("&#8226")
                textSize = 35f
                setTextColor(resources.getColor(R.color.inactive, applicationContext.theme))
            }
            binding.indicatorLayout.addView(dots[i])
        }

        dots[position].setTextColor(resources.getColor(R.color.active, applicationContext.theme))
    }

    private fun getitem(i: Int): Int {
        return binding.slideViewPager.currentItem + i
    }
}