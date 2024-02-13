package chiagb.works.randomQuoteGenerator.model

import com.google.gson.annotations.SerializedName

data class UnsplashPhoto(
    val id: String,
    val slug: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String,
    @SerializedName("promoted_at") val promotedAt: String?,
    val width: Int,
    val height: Int,
    val color: String,
    @SerializedName("blur_hash") val blurHash: String,
    val description: String?,
    @SerializedName("alt_description") val altDescription: String,
    val breadcrumbs: List<String>,
    val urls: Urls,
    val links: Links,
    val likes: Int,
    @SerializedName("liked_by_user") val likedByUser: Boolean,
    @SerializedName("current_user_collections") val currentUserCollections: List<String>,
    val sponsorship: Any?, // You can create a Sponsorship class if needed
    val topicSubmissions: TopicSubmissions
) {
    data class Urls(
        val raw: String,
        val full: String,
        val regular: String,
        val small: String,
        val thumb: String,
        @SerializedName("small_s3") val smallS3: String
    )

    data class Links(
        val self: String,
        val html: String,
        val download: String,
        @SerializedName("download_location") val downloadLocation: String
    )

    data class TopicSubmissions(
        @SerializedName("current-events") val currentEvents: TopicSubmission
    )

    data class TopicSubmission(
        val status: String,
        @SerializedName("approved_on") val approvedOn: String?
    )
}