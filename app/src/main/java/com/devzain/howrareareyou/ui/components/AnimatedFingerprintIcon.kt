package com.devzain.howrareareyou.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * The animated fingerprint/radar icon used as the app logo.
 *
 * Draws multiple concentric arcs at different angles and opacities
 * to create a fingerprint-like pattern. The whole thing slowly rotates
 * and the outer glow ring pulses in and out.
 *
 * Uses Canvas drawing instead of vector assets so we have full control
 * over the animation without needing lottie or external files.
 */
@Composable
fun AnimatedFingerprintIcon(
    modifier: Modifier = Modifier,
    size: Dp = 100.dp,
    iconColor: Color = Color.White,
    bgColor: Color = Color.White.copy(alpha = 0.2f),
) {
    val anim = rememberInfiniteTransition(label = "fp")

    // slow rotation of the whole fingerprint pattern
    val rotation by anim.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 12000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
        ),
        label = "rotate"
    )

    // pulsing glow around the icon
    val glowScale by anim.animateFloat(
        initialValue = 0.85f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(2500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "glow"
    )

    // shimmer effect - makes certain arcs brighter then dimmer
    val shimmer by anim.animateFloat(
        initialValue = 0.4f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1800, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "shimmer"
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.size(size)
    ) {
        // outer pulsing glow ring
        Canvas(
            modifier = Modifier
                .size(size * glowScale)
                .alpha(0.15f)
        ) {
            drawCircle(
                color = iconColor,
                radius = this.size.minDimension / 2,
            )
        }

        // second glow layer
        Canvas(
            modifier = Modifier
                .size(size * 0.95f)
                .alpha(shimmer * 0.1f)
        ) {
            drawCircle(
                color = iconColor,
                radius = this.size.minDimension / 2,
            )
        }

        // main icon circle background
        Box(
            modifier = Modifier
                .size(size * 0.72f)
                .clip(CircleShape)
                .background(bgColor),
            contentAlignment = Alignment.Center,
        ) {
            // the actual fingerprint lines drawn with canvas
            Canvas(modifier = Modifier.size(size * 0.4f)) {
                val center = Offset(this.size.width / 2, this.size.height / 2)
                val sw = 2.2f
                val cap = StrokeCap.Round

                // rotate the whole fingerprint pattern slowly
                rotate(degrees = rotation, pivot = center) {

                    // --- outermost ring (full) ---
                    drawArc(
                        color = iconColor.copy(alpha = 0.9f),
                        startAngle = -60f,
                        sweepAngle = 300f,
                        useCenter = false,
                        style = Stroke(width = sw, cap = cap),
                        size = Size(
                            this.size.width * 0.95f,
                            this.size.height * 0.95f
                        ),
                        topLeft = Offset(
                            this.size.width * 0.025f,
                            this.size.height * 0.025f
                        ),
                    )

                    // --- second ring ---
                    drawArc(
                        color = iconColor.copy(alpha = shimmer * 0.9f),
                        startAngle = -40f,
                        sweepAngle = 260f,
                        useCenter = false,
                        style = Stroke(width = sw, cap = cap),
                        size = Size(
                            this.size.width * 0.78f,
                            this.size.height * 0.78f
                        ),
                        topLeft = Offset(
                            this.size.width * 0.11f,
                            this.size.height * 0.11f
                        ),
                    )

                    // --- third ring ---
                    drawArc(
                        color = iconColor.copy(alpha = 0.8f),
                        startAngle = -20f,
                        sweepAngle = 220f,
                        useCenter = false,
                        style = Stroke(width = sw, cap = cap),
                        size = Size(
                            this.size.width * 0.62f,
                            this.size.height * 0.62f
                        ),
                        topLeft = Offset(
                            this.size.width * 0.19f,
                            this.size.height * 0.19f
                        ),
                    )

                    // --- fourth ring (smaller) ---
                    drawArc(
                        color = iconColor.copy(alpha = shimmer * 0.85f),
                        startAngle = 10f,
                        sweepAngle = 180f,
                        useCenter = false,
                        style = Stroke(width = sw, cap = cap),
                        size = Size(
                            this.size.width * 0.47f,
                            this.size.height * 0.47f
                        ),
                        topLeft = Offset(
                            this.size.width * 0.265f,
                            this.size.height * 0.265f
                        ),
                    )

                    // --- fifth ring (innermost arc) ---
                    drawArc(
                        color = iconColor.copy(alpha = 0.7f),
                        startAngle = 30f,
                        sweepAngle = 140f,
                        useCenter = false,
                        style = Stroke(width = sw, cap = cap),
                        size = Size(
                            this.size.width * 0.32f,
                            this.size.height * 0.32f
                        ),
                        topLeft = Offset(
                            this.size.width * 0.34f,
                            this.size.height * 0.34f
                        ),
                    )

                    // --- small accent arc on the other side ---
                    drawArc(
                        color = iconColor.copy(alpha = shimmer * 0.6f),
                        startAngle = 200f,
                        sweepAngle = 80f,
                        useCenter = false,
                        style = Stroke(width = 1.5f, cap = cap),
                        size = Size(
                            this.size.width * 0.55f,
                            this.size.height * 0.55f
                        ),
                        topLeft = Offset(
                            this.size.width * 0.225f,
                            this.size.height * 0.225f
                        ),
                    )

                    // center dot
                    drawCircle(
                        color = iconColor,
                        radius = 3f,
                        center = center,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF7B5EE0)
@Composable
fun FingerprintPreview() {
    AnimatedFingerprintIcon()
}
