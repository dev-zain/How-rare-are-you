package com.devzain.howrareareyou.ui.screens

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
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
    topTraits: List<ResultGenerator.RareTrait>,
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
        modifier = modifier.aspectRatio(4f / 5f),
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
                Spacer(Modifier.weight(0.3f))

                // app title
                Text(
                    "How Rare Are You?",
                    color = Color.White.copy(alpha = 0.6f),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                )

                Spacer(Modifier.height(14.dp))

                // big rarity ring
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.size(140.dp),
                ) {
                    // ring drawn with drawBehind
                    Box(
                        modifier = Modifier
                            .size(130.dp)
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

                Spacer(Modifier.height(8.dp))

                // percentile
                Text(
                    "Rarer than",
                    color = Color.White.copy(alpha = 0.5f),
                    fontSize = 12.sp,
                )
                Text(
                    "%.2f%%".format(result.percentile),
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    "of all humans",
                    color = Color.White.copy(alpha = 0.5f),
                    fontSize = 12.sp,
                )

                Spacer(Modifier.height(6.dp))

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

                Spacer(Modifier.height(12.dp))

                // top traits with category context
                if (topTraits.isNotEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color.White.copy(alpha = 0.08f))
                            .padding(10.dp)
                    ) {
                        Column {
                            Text(
                                "My rarest traits:",
                                color = Color.White.copy(alpha = 0.7f),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium,
                            )
                            Spacer(Modifier.height(6.dp))
                            topTraits.forEachIndexed { idx, trait ->
                                Row(
                                    modifier = Modifier.fillMaxWidth().padding(vertical = 3.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Text(
                                        when (idx) { 0 -> "\u2B50"; 1 -> "\uD83E\uDD48"; else -> "\uD83E\uDD49" },
                                        fontSize = 12.sp,
                                    )
                                    Spacer(Modifier.width(6.dp))
                                    Text(
                                        trait.traitName,
                                        color = Color.White,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Medium,
                                        modifier = Modifier.weight(1f),
                                    )
                                    Text(
                                        "%.1f%%".format(trait.percentage),
                                        color = AccentGold,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(Modifier.weight(0.15f))

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
 * Creates a bitmap of the postcard using Android Canvas API and shares it.
 * This approach is 100% reliable — no ComposeView rendering issues.
 */
private fun sharePostcard(
    context: Context,
    result: RarityCalculator.RarityResult,
    topTraits: List<ResultGenerator.RareTrait>,
) {
    try {
        val width = 1080
        val height = 1350
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = android.graphics.Canvas(bitmap)
        val w = width.toFloat()
        val h = height.toFloat()

        // --- gradient background ---
        val bgPaint = android.graphics.Paint(android.graphics.Paint.ANTI_ALIAS_FLAG).apply {
            shader = android.graphics.LinearGradient(
                0f, 0f, 0f, h,
                intArrayOf(0xFF4A1C96.toInt(), 0xFF7C3AED.toInt(), 0xFF9B5DE5.toInt(), 0xFF6B3FA0.toInt()),
                null, android.graphics.Shader.TileMode.CLAMP
            )
        }
        canvas.drawRect(0f, 0f, w, h, bgPaint)

        // --- decorative circles ---
        val circlePaint = android.graphics.Paint(android.graphics.Paint.ANTI_ALIAS_FLAG)
        circlePaint.color = 0x0AFFFFFF
        canvas.drawCircle(w * 0.8f, h * 0.15f, w * 0.6f, circlePaint)
        circlePaint.color = 0x07FFFFFF
        canvas.drawCircle(w * 0.1f, h * 0.7f, w * 0.4f, circlePaint)
        circlePaint.color = 0x0FD4A843.toInt()
        canvas.drawCircle(w * 0.5f, h * 0.4f, w * 0.3f, circlePaint)

        // --- "How Rare Are You?" title ---
        val titlePaint = android.graphics.Paint(android.graphics.Paint.ANTI_ALIAS_FLAG).apply {
            color = 0x99FFFFFF.toInt()
            textSize = 42f
            textAlign = android.graphics.Paint.Align.CENTER
            typeface = android.graphics.Typeface.create(android.graphics.Typeface.DEFAULT, android.graphics.Typeface.NORMAL)
        }
        canvas.drawText("How Rare Are You?", w / 2f, 120f, titlePaint)

        // --- rarity ring ---
        val ringCx = w / 2f
        val ringCy = 350f
        val ringR = 150f

        // ring track
        val trackPaint = android.graphics.Paint(android.graphics.Paint.ANTI_ALIAS_FLAG).apply {
            style = android.graphics.Paint.Style.STROKE
            strokeWidth = 14f
            color = 0x1FFFFFFF
            strokeCap = android.graphics.Paint.Cap.ROUND
        }
        canvas.drawCircle(ringCx, ringCy, ringR, trackPaint)

        // ring fill arc
        val fillPaint = android.graphics.Paint(android.graphics.Paint.ANTI_ALIAS_FLAG).apply {
            style = android.graphics.Paint.Style.STROKE
            strokeWidth = 14f
            color = 0xFFD4A843.toInt()
            strokeCap = android.graphics.Paint.Cap.ROUND
        }
        val ringRect = android.graphics.RectF(ringCx - ringR, ringCy - ringR, ringCx + ringR, ringCy + ringR)
        val sweep = 360f * (result.percentile / 100.0).toFloat()
        canvas.drawArc(ringRect, -90f, sweep, false, fillPaint)

        // "1 in" text
        val smallWhite = android.graphics.Paint(android.graphics.Paint.ANTI_ALIAS_FLAG).apply {
            color = 0x99FFFFFF.toInt()
            textSize = 34f
            textAlign = android.graphics.Paint.Align.CENTER
        }
        canvas.drawText("1 in", ringCx, ringCy - 15f, smallWhite)

        // big number
        val bigWhite = android.graphics.Paint(android.graphics.Paint.ANTI_ALIAS_FLAG).apply {
            color = 0xFFFFFFFF.toInt()
            textSize = 58f
            textAlign = android.graphics.Paint.Align.CENTER
            typeface = android.graphics.Typeface.create(android.graphics.Typeface.DEFAULT, android.graphics.Typeface.BOLD)
        }
        canvas.drawText(RarityCalculator.formatOneInX(result.oneInX), ringCx, ringCy + 45f, bigWhite)

        // --- "Rarer than" + percentage ---
        canvas.drawText("Rarer than", w / 2f, 540f, smallWhite)

        val pctPaint = android.graphics.Paint(android.graphics.Paint.ANTI_ALIAS_FLAG).apply {
            color = 0xFFFFFFFF.toInt()
            textSize = 80f
            textAlign = android.graphics.Paint.Align.CENTER
            typeface = android.graphics.Typeface.create(android.graphics.Typeface.DEFAULT, android.graphics.Typeface.BOLD)
        }
        canvas.drawText("%.2f%%".format(result.percentile), w / 2f, 630f, pctPaint)
        canvas.drawText("of all humans", w / 2f, 680f, smallWhite)

        // --- tier badge ---
        val tierText = "${getTierEmojiText(result.tier)} ${result.tier.label}"
        val tierColor = getTierColorInt(result.tier)
        val badgePaint = android.graphics.Paint(android.graphics.Paint.ANTI_ALIAS_FLAG).apply {
            color = (tierColor and 0x00FFFFFF) or 0x33000000 // 20% alpha of tier color
        }
        val badgeTextPaint = android.graphics.Paint(android.graphics.Paint.ANTI_ALIAS_FLAG).apply {
            color = tierColor
            textSize = 38f
            textAlign = android.graphics.Paint.Align.CENTER
            typeface = android.graphics.Typeface.create(android.graphics.Typeface.DEFAULT, android.graphics.Typeface.BOLD)
        }
        val badgeWidth = badgeTextPaint.measureText(tierText) + 80f
        val badgeLeft = (w - badgeWidth) / 2f
        canvas.drawRoundRect(badgeLeft, 715f, badgeLeft + badgeWidth, 770f, 30f, 30f, badgePaint)
        canvas.drawText(tierText, w / 2f, 755f, badgeTextPaint)

        // --- traits box ---
        val boxTop = 810f
        val boxPadding = 40f
        val traitLineHeight = 70f
        val boxHeight = 80f + topTraits.size * traitLineHeight

        val boxPaint = android.graphics.Paint(android.graphics.Paint.ANTI_ALIAS_FLAG).apply {
            color = 0x14FFFFFF
        }
        canvas.drawRoundRect(boxPadding, boxTop, w - boxPadding, boxTop + boxHeight, 40f, 40f, boxPaint)

        // "My rarest traits:" label
        val labelPaint = android.graphics.Paint(android.graphics.Paint.ANTI_ALIAS_FLAG).apply {
            color = 0xB3FFFFFF.toInt()
            textSize = 32f
            textAlign = android.graphics.Paint.Align.LEFT
        }
        canvas.drawText("My rarest traits:", boxPadding + 30f, boxTop + 45f, labelPaint)

        // each trait
        val traitPaint = android.graphics.Paint(android.graphics.Paint.ANTI_ALIAS_FLAG).apply {
            color = 0xFFFFFFFF.toInt()
            textSize = 34f
            textAlign = android.graphics.Paint.Align.LEFT
        }
        val pctTraitPaint = android.graphics.Paint(android.graphics.Paint.ANTI_ALIAS_FLAG).apply {
            color = 0xFFD4A843.toInt()
            textSize = 34f
            textAlign = android.graphics.Paint.Align.RIGHT
            typeface = android.graphics.Typeface.create(android.graphics.Typeface.DEFAULT, android.graphics.Typeface.BOLD)
        }
        val medals = listOf("\u2B50", "\uD83E\uDD48", "\uD83E\uDD49")
        topTraits.forEachIndexed { idx, trait ->
            val y = boxTop + 80f + (idx + 0.7f) * traitLineHeight
            val medal = medals.getOrElse(idx) { "" }
            canvas.drawText("$medal  ${trait.traitName}", boxPadding + 30f, y, traitPaint)
            canvas.drawText("%.1f%%".format(trait.percentage), w - boxPadding - 30f, y, pctTraitPaint)
        }

        // --- "Can you beat my score?" ---
        val challengePaint = android.graphics.Paint(android.graphics.Paint.ANTI_ALIAS_FLAG).apply {
            color = 0xFFD4A843.toInt()
            textSize = 42f
            textAlign = android.graphics.Paint.Align.CENTER
            typeface = android.graphics.Typeface.create(android.graphics.Typeface.DEFAULT, android.graphics.Typeface.BOLD)
        }
        canvas.drawText("Can you beat my score?", w / 2f, 1170f, challengePaint)

        // divider line
        val linePaint = android.graphics.Paint().apply { color = 0x26FFFFFF; strokeWidth = 2f }
        canvas.drawLine(w * 0.3f, 1210f, w * 0.7f, 1210f, linePaint)

        // hashtag
        val hashPaint = android.graphics.Paint(android.graphics.Paint.ANTI_ALIAS_FLAG).apply {
            color = 0x80FFFFFF.toInt()
            textSize = 36f
            textAlign = android.graphics.Paint.Align.CENTER
        }
        canvas.drawText(APP_HASHTAG, w / 2f, 1270f, hashPaint)

        // "Download on Google Play"
        val dlPaint = android.graphics.Paint(android.graphics.Paint.ANTI_ALIAS_FLAG).apply {
            color = 0x59FFFFFF
            textSize = 30f
            textAlign = android.graphics.Paint.Align.CENTER
        }
        canvas.drawText("Download on Google Play", w / 2f, 1315f, dlPaint)

        // --- save to cache and share ---
        val sharedDir = File(context.cacheDir, "shared")
        sharedDir.mkdirs()
        val file = File(sharedDir, "rarity_card.png")
        file.outputStream().use { out ->
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
        }
        bitmap.recycle()

        val uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            file
        )

        val shareText = buildString {
            append("I'm rarer than ${String.format("%.1f", result.percentile)}% of all humans! ")
            append("(${result.tier.label} Rarity)\n\n")
            append("Can you beat my score?\n\n")
            append(APP_HASHTAG)
            append("\n")
            append(PLAY_STORE_URL)
        }

        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "image/png"
            putExtra(Intent.EXTRA_STREAM, uri)
            putExtra(Intent.EXTRA_TEXT, shareText)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        context.startActivity(Intent.createChooser(shareIntent, "Share your rarity"))

    } catch (e: Exception) {
        // fallback: share text only if image rendering fails
        val shareText = buildString {
            append("I'm rarer than ${String.format("%.1f", result.percentile)}% of all humans! ")
            append("(${result.tier.label} Rarity)\n\n")
            append("Can you beat my score?\n\n")
            append(APP_HASHTAG)
            append("\n")
            append(PLAY_STORE_URL)
        }
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, shareText)
        }
        context.startActivity(Intent.createChooser(shareIntent, "Share your rarity"))
    }
}

// helper functions for Canvas-based rendering
private fun getTierColorInt(tier: RarityCalculator.RarityTier): Int {
    return when (tier) {
        RarityCalculator.RarityTier.MYTHIC -> 0xFFF5C518.toInt()
        RarityCalculator.RarityTier.LEGENDARY -> 0xFFFF6B35.toInt()
        RarityCalculator.RarityTier.EPIC -> 0xFFE040FB.toInt()
        RarityCalculator.RarityTier.RARE -> 0xFF26C6DA.toInt()
        RarityCalculator.RarityTier.UNCOMMON -> 0xFF66BB6A.toInt()
        RarityCalculator.RarityTier.COMMON -> 0xFF90A4AE.toInt()
    }
}

private fun getTierEmojiText(tier: RarityCalculator.RarityTier): String {
    return when (tier) {
        RarityCalculator.RarityTier.MYTHIC -> "\uD83C\uDF1F"
        RarityCalculator.RarityTier.LEGENDARY -> "\uD83D\uDD25"
        RarityCalculator.RarityTier.EPIC -> "\uD83D\uDC8E"
        RarityCalculator.RarityTier.RARE -> "\u2728"
        RarityCalculator.RarityTier.UNCOMMON -> "\uD83D\uDCA0"
        RarityCalculator.RarityTier.COMMON -> "\uD83D\uDD35"
    }
}

@Preview(showBackground = true)
@Composable
fun SharePreview() {
    HowrareAreYouTheme {
        ShareScreen(
            result = RarityCalculator.RarityResult(
                combinedProbability = 0.0000004,
                oneInX = 27,
                percentile = 96.27,
                tier = RarityCalculator.RarityTier.LEGENDARY,
                rarestTraitIndex = 2,
                rarityScore = 12.5,
            ),
            answers = listOf(
                UserAnswer(1, 1, 0.10, "Left-handed", "Handedness"),
                UserAnswer(2, 5, 0.02, "Green eyes", "Eye Color"),
                UserAnswer(3, 7, 0.006, "AB\u2212 blood type", "Blood Type"),
            ),
        )
    }
}
