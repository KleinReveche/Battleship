package com.revechelizarondo.battleship.core.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val DirectionsBoat: ImageVector
    get() {
        if (_DirectionsBoat != null) {
            return _DirectionsBoat!!
        }
        _DirectionsBoat = ImageVector.Builder(
            name = "DirectionsBoat",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(fill = SolidColor(Color(0xFFE3E3E3))) {
                moveTo(152f, 880f)
                horizontalLineToRelative(-32f)
                verticalLineToRelative(-80f)
                horizontalLineToRelative(32f)
                quadToRelative(48f, 0f, 91.5f, -10.5f)
                reflectiveQuadTo(341f, 756f)
                quadToRelative(38f, 19f, 66.5f, 31.5f)
                reflectiveQuadTo(480f, 800f)
                quadToRelative(44f, 0f, 72.5f, -12.5f)
                reflectiveQuadTo(619f, 756f)
                quadToRelative(53f, 23f, 97.5f, 33.5f)
                reflectiveQuadTo(809f, 800f)
                horizontalLineToRelative(31f)
                verticalLineToRelative(80f)
                horizontalLineToRelative(-31f)
                quadToRelative(-49f, 0f, -95.5f, -9f)
                reflectiveQuadTo(622f, 844f)
                quadToRelative(-40f, 19f, -73f, 27f)
                reflectiveQuadToRelative(-69f, 8f)
                quadToRelative(-36f, 0f, -68.5f, -8f)
                reflectiveQuadTo(339f, 844f)
                quadToRelative(-45f, 18f, -91.5f, 27f)
                reflectiveQuadTo(152f, 880f)
                close()
                moveTo(480f, 720f)
                quadToRelative(-60f, 0f, -105f, -40f)
                lineToRelative(-45f, -40f)
                quadToRelative(-27f, 27f, -60.5f, 46f)
                reflectiveQuadTo(198f, 713f)
                lineToRelative(-85f, -273f)
                quadToRelative(-5f, -17f, 3f, -31f)
                reflectiveQuadToRelative(25f, -19f)
                lineToRelative(59f, -16f)
                verticalLineToRelative(-134f)
                quadToRelative(0f, -33f, 23.5f, -56.5f)
                reflectiveQuadTo(280f, 160f)
                horizontalLineToRelative(100f)
                verticalLineToRelative(-80f)
                horizontalLineToRelative(200f)
                verticalLineToRelative(80f)
                horizontalLineToRelative(100f)
                quadToRelative(33f, 0f, 56.5f, 23.5f)
                reflectiveQuadTo(760f, 240f)
                verticalLineToRelative(134f)
                lineToRelative(59f, 16f)
                quadToRelative(17f, 5f, 25f, 19f)
                reflectiveQuadToRelative(3f, 31f)
                lineToRelative(-85f, 273f)
                quadToRelative(-38f, -8f, -71.5f, -27f)
                reflectiveQuadTo(630f, 640f)
                lineToRelative(-45f, 40f)
                quadToRelative(-45f, 40f, -105f, 40f)
                close()
                moveTo(482f, 640f)
                quadToRelative(31f, 0f, 55f, -20.5f)
                reflectiveQuadToRelative(44f, -43.5f)
                lineToRelative(46f, -53f)
                lineToRelative(41f, 42f)
                quadToRelative(11f, 11f, 22.5f, 20.5f)
                reflectiveQuadTo(713f, 605f)
                lineToRelative(46f, -149f)
                lineToRelative(-279f, -73f)
                lineToRelative(-278f, 73f)
                lineToRelative(46f, 149f)
                quadToRelative(11f, -10f, 22.5f, -19.5f)
                reflectiveQuadTo(293f, 565f)
                lineToRelative(41f, -42f)
                lineToRelative(46f, 53f)
                quadToRelative(20f, 24f, 45f, 44f)
                reflectiveQuadToRelative(57f, 20f)
                close()
                moveTo(280f, 353f)
                lineToRelative(200f, -53f)
                lineToRelative(200f, 53f)
                verticalLineToRelative(-113f)
                lineTo(280f, 240f)
                verticalLineToRelative(113f)
                close()
                moveTo(481f, 511f)
                close()
            }
        }.build()

        return _DirectionsBoat!!
    }

@Suppress("ObjectPropertyName")
private var _DirectionsBoat: ImageVector? = null
