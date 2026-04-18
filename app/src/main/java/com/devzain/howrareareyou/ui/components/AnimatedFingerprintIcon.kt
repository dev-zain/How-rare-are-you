package com.devzain.howrareareyou.ui.components

import androidx.compose.animation.core.*
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun AnimatedFingerprintIcon(
    modifier: Modifier = Modifier,
    size: Dp = 100.dp,
) {
    val anim = rememberInfiniteTransition(label = "fp")

    val rotation by anim.animateFloat(
        initialValue = 0f, targetValue = 360f,
        animationSpec = infiniteRepeatable(tween(16000, easing = LinearEasing), RepeatMode.Restart),
        label = "rotate"
    )

    val radarRotation by anim.animateFloat(
        initialValue = 0f, targetValue = 360f,
        animationSpec = infiniteRepeatable(tween(2500, easing = LinearEasing), RepeatMode.Restart),
        label = "radar"
    )

    val pulse by anim.animateFloat(
        initialValue = 0.8f, targetValue = 1.15f,
        animationSpec = infiniteRepeatable(tween(2000, easing = LinearEasing), RepeatMode.Reverse),
        label = "pulse"
    )

    val neonCyan = Color(0xFF00E5FF)
    val neonPink = Color(0xFFFF007A)

    Box(contentAlignment = Alignment.Center, modifier = modifier.size(size)) {
        // deep outer glow
        Canvas(modifier = Modifier.size(size * pulse).alpha(0.6f)) {
            val bgGlow = Brush.radialGradient(
                colors = listOf(neonPink.copy(alpha = 0.4f), neonCyan.copy(alpha = 0.1f), Color.Transparent),
                radius = this.size.width / 1.5f
            )
            drawCircle(brush = bgGlow)
        }

        // internal background circle
        Box(
            modifier = Modifier
                .size(size * 0.75f)
                .clip(CircleShape)
                .background(Color(0xFF0A0710)),
            contentAlignment = Alignment.Center,
        ) {
            Canvas(modifier = Modifier.size(size * 0.45f)) {
                val center = Offset(this.size.width / 2, this.size.height / 2)
                val sw = 3.5f
                val cap = StrokeCap.Round

                // sweeping gradient (moving lines effect)
                val sweepBrush = Brush.sweepGradient(
                    colors = listOf(neonCyan, neonPink, Color(0xFF8A2BE2), neonCyan),
                    center = center
                )

                // draw a radar scanner line
                rotate(degrees = radarRotation, pivot = center) {
                    drawArc(
                        brush = Brush.sweepGradient(listOf(Color.Transparent, neonCyan.copy(alpha = 0.7f))),
                        startAngle = 0f, sweepAngle = 90f, useCenter = true,
                        size = Size(this.size.width * 1.5f, this.size.height * 1.5f),
                        topLeft = Offset(-this.size.width * 0.25f, -this.size.height * 0.25f)
                    )
                }

                rotate(degrees = rotation, pivot = center) {
                    // lines
                    drawArc(brush = sweepBrush, startAngle = -60f, sweepAngle = 300f, useCenter = false, style = Stroke(width = sw, cap = cap), size = Size(this.size.width * 0.95f, this.size.height * 0.95f), topLeft = Offset(this.size.width * 0.025f, this.size.height * 0.025f))
                    drawArc(brush = sweepBrush, startAngle = -40f, sweepAngle = 260f, useCenter = false, style = Stroke(width = sw, cap = cap), size = Size(this.size.width * 0.78f, this.size.height * 0.78f), topLeft = Offset(this.size.width * 0.11f, this.size.height * 0.11f))
                    drawArc(brush = sweepBrush, startAngle = -20f, sweepAngle = 220f, useCenter = false, style = Stroke(width = sw, cap = cap), size = Size(this.size.width * 0.62f, this.size.height * 0.62f), topLeft = Offset(this.size.width * 0.19f, this.size.height * 0.19f))
                    drawArc(brush = sweepBrush, startAngle = 10f, sweepAngle = 180f, useCenter = false, style = Stroke(width = sw, cap = cap), size = Size(this.size.width * 0.47f, this.size.height * 0.47f), topLeft = Offset(this.size.width * 0.265f, this.size.height * 0.265f))
                    drawArc(brush = sweepBrush, startAngle = 30f, sweepAngle = 140f, useCenter = false, style = Stroke(width = sw, cap = cap), size = Size(this.size.width * 0.32f, this.size.height * 0.32f), topLeft = Offset(this.size.width * 0.34f, this.size.height * 0.34f))
                    
                    drawCircle(color = neonCyan, radius = 4f, center = center)
                }
            }
        }
    }
}
