package io.ruin.content.activities.lms

/**
 * The different mode types for a [LastManStandingSession].
 * @author Heaven
 */
enum class LastManStandingMode(val title: String, val threshold: Int) {
    COMPETITIVE("Competitive", 4),
    CASUAL("Casual", 4)
}