package com.dicoding.myapplication1.view.onboarding

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.viewpager.widget.PagerAdapter
import com.dicoding.myapplication1.R

class ViewPagerAdapter (private val context: Context) : PagerAdapter() {

    private val images = arrayOf(
        R.drawable.ig,
        R.drawable.fb,
        R.drawable.linkedin
    )

    private val headings = arrayOf(
        R.string.heading_one,
        R.string.heading_two,
        R.string.heading_three
    )

    private val descriptions = arrayOf(
        R.string.desc_one,
        R.string.desc_two,
        R.string.desc_three
    )

    override fun getCount(): Int {
        return headings.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object` as View
    }

    @NonNull
    override fun instantiateItem(@NonNull container: ViewGroup, position: Int): Any {
        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater.inflate(R.layout.slide_layout, container, false)

        val slidetitleimage = view.findViewById<ImageView>(R.id.titleImage)
        val slideHeading = view.findViewById<TextView>(R.id.texttitle)
        val slideDescription = view.findViewById<TextView>(R.id.textdescription)

        slidetitleimage.setImageResource(images[position])
        slideHeading.setText(headings[position])
        slideDescription.setText(descriptions[position])

        container.addView(view)
        return view
    }

    override fun destroyItem(@NonNull container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}