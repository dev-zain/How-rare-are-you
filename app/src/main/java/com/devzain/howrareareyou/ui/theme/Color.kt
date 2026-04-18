package com.devzain.howrareareyou.ui.theme

import androidx.compose.ui.graphics.Color

// neon brand colors
val NeonBg = Color(0xFF0A0710) // Deep pitch black/purple
val NeonCyan = Color(0xFF00E5FF)
val NeonPink = Color(0xFFFF007A)
val NeonPurple = Color(0xFF8A2BE2)
val NeonCardBg = Color(0xFF161224)

// legacy brand definitions for compile safety
val BrandPurple = NeonPurple
val BrandPurpleLight = Color(0xFFD47CFF)
val BrandPurpleSoft = Color(0xFF8A2BE2)
val BrandPurpleBg = NeonCardBg

// accents
val AccentTeal = NeonCyan
val AccentCoral = NeonPink
val AccentGold = Color(0xFFFFD166)
val AccentPink = NeonPink
val AccentBlue = NeonCyan
val AccentOrange = Color(0xFFFF6200)

// category backgrounds (translucent overlays)
val CatPhysical = Color(0x30FFF0E0)
val CatSkills = Color(0x30E8F5E9)
val CatLife = Color(0x30E3F2FD)
val CatQuirks = Color(0x30FCE4EC)

// text
val TextDark = Color(0xFFFFFFFF) // Reused as white in dark mode
val TextMedium = Color(0xFFAAA5C0)
val TextLight = Color(0xFF807B9E)
val TextOnPurple = Color(0xFFFFFFFF)

// surfaces
val SurfaceWhite = NeonCardBg 
val SurfaceBg = NeonBg
val SurfaceCard = NeonCardBg
val SurfaceDivider = Color(0x20FFFFFF)

// rarity tiers
val TierMythic = NeonPink
val TierLegendary = AccentGold
val TierEpic = NeonPurple
val TierRare = NeonCyan
val TierUncommon = Color(0xFF378ADD)
val TierCommon = Color(0xFF757185)

// answer states
val AnswerDefault = Color(0xFF1C172E)
val AnswerSelected = NeonCyan
val AnswerSelectedBg = Color(0x2000E5FF)
