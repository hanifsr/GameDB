package id.hanifsr.gamedb.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import id.hanifsr.gamedb.R

@BindingAdapter("imageUrl")
fun setImageUrl(imageView: ImageView, url: String?) {
	if (url.isNullOrEmpty()) return

	Glide.with(imageView.context)
		.load(url)
		.apply(
			RequestOptions()
				.placeholder(R.color.turquoise_400)
				.error(R.drawable.ic_baseline_broken_image_24)
		)
		.into(imageView)
}