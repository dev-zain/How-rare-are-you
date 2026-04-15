package com.devzain.howrareareyou.ui.screens

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.view.View
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import com.devzain.howrareareyou.data.*
import com.devzain.howrareareyou.ui.theme.*
import java.io.File

/**
 * The share screen.
 *
 * Shows a preview of the postcard that will be shared, plus
 * buttons to share to various platforms. The postcard is designed
 * to be eye-catching on Instagram Stories (1080x1920 aspect ratio)
 * and includes the app branding + hashtag for virality.
 */

// our branded hashtag
const val APP_HASHTAG = "#HowRareAreYou"
const val APP_TAGLINE = "howrareareyou.app"
// placeholder — replace when you publish on Play Store
const val PLAY_STORE_URL = "https://play.google.com/store/apps/details?id=com.devzain.howrareareyou"

@Composable
fun ShareScreen(
    result: RarityCalculator.RarityResult,
    answers: List<UserAnswer>,
    onBackClick: () -> Unit = {},
) {
    val context = LocalContext.current
    val topTraits = remember { ResultGenerator.getTopRarestTraits(answers) }

    Box(modifier = Modifier.fillMaxSize().background(SurfaceBg)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // header bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(listOf(BrandPurple, BrandPurpleLight)),
                        shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)
                    )
                    .padding(horizontal = 20.dp)
                    .padding(top = 48.dp, bottom = 20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        "\u2190", color = Color.White, fontSize = 22.sp,
                        modifier = Modifier
                            .clip(CircleShape)
                            .clickable { onBackClick() }
                            .padding(4.dp)
                    )
                    Spacer(Modifier.weight(1f))
                    Text(
                        "Share Your Rarity",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Spacer(Modifier.weight(1f))
                    // invisible spacer to balance the back button
                    Spacer(Modifier.width(30.dp))
                }
            }

            Spacer(Modifier.height(20.dp))

            // postcard preview
            SharePostcard(
                result = result,
                topTraits = topTraits,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
            )

            Spacer(Modifier.height(20.dp))

            // share buttons
            Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                Text(
                    "Challenge your friends!",
                    color = TextDark,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    "Share your rarity card and see who's rarer",
                    color = TextLight,
                    fontSize = 13.sp,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                )

                Spacer(Modifier.height(16.dp))

                // main share button
                Box(
                    modifier = Modifier
                        .fillMaxWidth().height(54.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Brush.horizontalGradient(listOf(BrandPurple, BrandPurpleLight)))
                        .clickable {
                            sharePostcard(context, result, topTraits)
                        },
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        "Share to Instagram / WhatsApp  \u2192",
                        color = Color.White,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                }

                Spacer(Modifier.height(10.dp))

                // copy hashtag button
                Box(
                    modifier = Modifier
                        .fillMaxWidth().height(48.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(BrandPurple.copy(alpha = 0.08f))
                        .clickable {
                            // copy hashtag to clipboard
                            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
                            val clip = android.content.ClipData.newPlainText("hashtag", APP_HASHTAG)
                            clipboard.setPrimaryClip(clip)
                            android.widget.Toast.makeText(context, "Hashtag copied!", android.widget.Toast.LENGTH_SHORT).show()
                        },
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        "Copy $APP_HASHTAG",
                        color = BrandPurple,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                    )
                }

                Spacer(Modifier.height(24.dp))

                // info text
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = BrandPurpleBg),
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            "\uD83D\uDCA1 Pro tip",
                            color = TextDark,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            "Add $APP_HASHTAG to your story so your friends can find the app and compare their scores!",
                            color = TextMedium,
                            fontSize = 12.sp,
                            lineHeight = 17.sp,
                        )
                    }
                }
            }

            Spacer(Modifier.height(32.dp))
        }
    }
}

/**
 * The actual postcard that gets shared.
 * Designed to look stunning on Instagram Stories.
 *
 * Purple gradient background with the rarity score in the center,
 * top traits listed below, and branding at the bottom.
 */
@Composable
fun SharePostcard(
    result: RarityCalculator.RarityResult,
    topTraits: List<Pair<String, Double>>,
    modifier: Modifier = Modifier,
) {
    val tierColor = when (result.tier) {
        RarityCalculator.RarityTier.MYTHIC -> TierMythic
        RarityCalculator.RarityTier.LEGENDARY -> TierLegendary
        RarityCalculator.RarityTier.EPIC -> TierEpic
        RarityCalculator.RarityTier.RARE -> TierRare
        RarityCalculator.RarityTier.UNCOMMON -> TierUncommon
        RarityCalculator.RarityTier.COMMON -> TierCommon
    }
    val tierEmoji = when (result.tier) {
        RarityCalculator.RarityTier.MYTHIC -> "\uD83C\uDF1F"
        RarityCalculator.RarityTier.LEGENDARY -> "\uD83D\uDD25"
        RarityCalculator.RarityTier.EPIC -> "\uD83D\uDC8E"
        RarityCalculator.RarityTier.RARE -> "\u2728"
        RarityCalculator.RarityTier.UNCOMMON -> "\uD83D\uDCA0"
        RarityCalculator.RarityTier.COMMON -> "\uD83D\uDD35"
    }

    Card(
        modifier = modifier.aspectRatio(9f / 16f),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(12.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color(0xFF4A1C96),  // deep purple
                            BrandPurple,
                            BrandPurpleLight,
                            Color(0xFF6B3FA0),  // mid purple
                        )
                    )
                )
                .drawBehind {
                    // decorative circles in the background
                    drawCircle(
                        color = Color.White.copy(alpha = 0.04f),
                        radius = size.width * 0.6f,
                        center = Offset(size.width * 0.8f, size.height * 0.15f),
                    )
                    drawCircle(
                        color = Color.White.copy(alpha = 0.03f),
                        radius = size.width * 0.4f,
                        center = Offset(size.width * 0.1f, size.height * 0.7f),
                    )
                    drawCircle(
                        color = AccentGold.copy(alpha = 0.06f),
                        radius = size.width * 0.3f,
                        center = Offset(size.width * 0.5f, size.height * 0.4f),
                    )
                }
                .padding(24.dp),
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(Modifier.weight(0.5f))

                // app title
                Text(
                    "How Rare Are You?",
                    color = Color.White.copy(alpha = 0.6f),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                )

                Spacer(Modifier.height(20.dp))

                // big rarity ring
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.size(160.dp),
                ) {
                    // ring drawn with drawBehind
                    Box(
                        modifier = Modifier
                            .size(150.dp)
                            .drawBehind {
                                // track ring
                                drawArc(
                                    color = Color.White.copy(alpha = 0.12f),
                                    startAngle = -90f,
                                    sweepAngle = 360f,
                                    useCenter = false,
                                    style = Stroke(width = 10f, cap = StrokeCap.Round),
                                )
                                // filled ring
                                drawArc(
                                    color = AccentGold,
                                    startAngle = -90f,
                                    sweepAngle = 360f * (result.percentile / 100f).toFloat(),
                                    useCenter = false,
                                    style = Stroke(width = 10f, cap = StrokeCap.Round),
                                )
                            },
                        contentAlignment = Alignment.Center,
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                "1 in",
                                color = Color.White.copy(alpha = 0.6f),
                                fontSize = 12.sp,
                            )
                            Text(
                                RarityCalculator.formatOneInX(result.oneInX),
                                color = Color.White,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                }

                Spacer(Modifier.height(12.dp))

                // percentile
                Text(
                    "Rarer than",
                    color = Color.White.copy(alpha = 0.5f),
                    fontSize = 12.sp,
                )
                Text(
                    "%.2f%%".format(result.percentile),
                    color = Color.White,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    "of all humans",
                    color = Color.White.copy(alpha = 0.5f),
                    fontSize = 12.sp,
                )

                Spacer(Modifier.height(10.dp))

                // tier badge
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(tierColor.copy(alpha = 0.2f))
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                ) {
                    Text(
                        "$tierEmoji ${result.tier.label}",
                        color = tierColor,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                }

                Spacer(Modifier.height(20.dp))

                // top traits
                if (topTraits.isNotEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color.White.copy(alpha = 0.08f))
                            .padding(14.dp)
                    ) {
                        Column {
                            Text(
                                "My rarest traits:",
                                color = Color.White.copy(alpha = 0.7f),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium,
                            )
                            Spacer(Modifier.height(6.dp))
                            topTraits.forEachIndexed { idx, (name, pct) ->
                                Row(
                                    modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Text(
                                        when (idx) { 0 -> "\u2B50"; 1 -> "\uD83E\uDD48"; else -> "\uD83E\uDD49" },
                                        fontSize = 12.sp,
                                    )
                                    Spacer(Modifier.width(8.dp))
                                    Text(
                                        name,
                                        color = Color.White,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Medium,
                                        modifier = Modifier.weight(1f),
                                    )
                                    Text(
                                        "%.1f%%".format(pct),
                                        color = AccentGold,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(Modifier.weight(0.3f))

                // challenge text
                Text(
                    "Can you beat my score?",
                    color = AccentGold,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                )

                Spacer(Modifier.height(8.dp))

                // divider line
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.4f)
                        .height(1.dp)
                        .background(Color.White.copy(alpha = 0.15f))
                )

                Spacer(Modifier.height(10.dp))

                // branding footer
                Text(
                    APP_HASHTAG,
                    color = Color.White.copy(alpha = 0.5f),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    "Download on Google Play",
                    color = Color.White.copy(alpha = 0.35f),
                    fontSize = 10.sp,
                )

                Spacer(Modifier.weight(0.2f))
            }
        }
    }
}

/**
 * Creates a bitmap of the postcard and shares it via Android's share sheet.
 * The share text includes the hashtag and a link to the app.
 */
private fun sharePostcard(
    context: Context,
    result: RarityCalculator.RarityResult,
    topTraits: List<Pair<String, Double>>,
) {
    // create a ComposeView to render the postcard as a bitmap
    val composeView = ComposeView(context).apply {
        setContent {
            SharePostcard(
                result = result,
                topTraits = topTraits,
                modifier = Modifier.size(width = 1080.dp, height = 1920.dp),
            )
        }
    }

    // measure and layout at Instagram story size (1080x1920 pixels)
    val widthSpec = View.MeasureSpec.makeMeasureSpec(1080, View.MeasureSpec.EXACTLY)
    val heightSpec = View.MeasureSpec.makeMeasureSpec(1920, View.MeasureSpec.EXACTLY)
    composeView.measure(widthSpec, heightSpec)
    composeView.layout(0, 0, 1080, 1920)

    // draw to bitmap
    val bitmap = Bitmap.createBitmap(1080, 1920, Bitmap.Config.ARGB_8888)
    val canvas = android.graphics.Canvas(bitmap)
    composeView.draw(canvas)

    // save to cache directory
    val sharedDir = File(context.cacheDir, "shared")
    sharedDir.mkdirs()
    val file = File(sharedDir, "rarity_card.png")
    file.outputStream().use { out ->
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
    }
    bitmap.recycle()

    // get a content URI through FileProvider
    val uri = FileProvider.getUriForFile(
        context,
        "${context.packageName}.fileprovider",
        file
    )

    // build the share text with hashtag
    val tierLabel = result.tier.label
    val shareText = buildString {
        append("I'm rarer than ${String.format("%.1f", result.percentile)}% of all humans! ")
        append("($tierLabel Rarity)\n\n")
        append("Can you beat my score?\n\n")
        append(APP_HASHTAG)
        append("\n")
        append(PLAY_STORE_URL)
    }

    // fire the share intent
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "image/png"
        putExtra(Intent.EXTRA_STREAM, uri)
        putExtra(Intent.EXTRA_TEXT, shareText)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }

    context.startActivity(Intent.createChooser(shareIntent, "Share your rarity"))
}

@Preview(showBackground = true)
@Composable
fun SharePreview() {
    HowrareAreYouTheme {
        ShareScreen(
            result = RarityCalculator.RarityResult(
                combinedProbability = 0.0000004,
                oneInX = 2380952,
                percentile = 87.5,
                tier = RarityCalculator.RarityTier.EPIC,
                rarestTraitIndex = 2,
                rarityScore = 12.5,
            ),
            answers = listOf(
                UserAnswer(1, 1, 0.10, "Left-handed"),
                UserAnswer(2, 5, 0.02, "Green eyes"),
                UserAnswer(3, 7, 0.006, "AB\u2212 blood"),
            ),
        )
    }
}
