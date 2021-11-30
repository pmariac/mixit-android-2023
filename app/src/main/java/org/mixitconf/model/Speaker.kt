package org.mixitconf.model

import android.net.Uri
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.github.rjeschke.txtmark.Processor
import org.mixitconf.R
import org.mixitconf.model.enums.LinkType
import org.mixitconf.toHtml

@Entity
data class Speaker(
    @PrimaryKey val login: String,
    val firstname: String,
    val lastname: String,
    val company: String?,
    val descriptionFr: String?,
    val descriptionEn: String?
) {

    // These list is only populated when we want to see the speaker detail
    @Ignore
    val links: MutableList<Link> = mutableListOf()

    @Ignore
    val talks: MutableList<Talk> = mutableListOf()

    val fullname
        get() = "$firstname $lastname".trim()

    val descriptionInMarkdown
        get() = if (descriptionFr.isNullOrEmpty()) null else Processor.process(descriptionFr).toHtml

    private val socialLink: Link?
        get() = if (links.isEmpty()) null else links.firstOrNull { it.linkType == LinkType.Social }

    private val webLink: Link?
        get() = if (links.isEmpty()) null else links.firstOrNull { it.linkType != LinkType.Social }

    val hasLink: Boolean
        get() = socialLink != null || webLink != null

    val linkUri: Uri
        get() = Uri.parse(socialLink?.url ?: webLink?.url ?: "https://mixitconf.org/user/${login}")

    val linkImageResourceId: Int
        get() = socialLink?.socialType?.resourceId ?: R.drawable.mxt_icon_web

}

