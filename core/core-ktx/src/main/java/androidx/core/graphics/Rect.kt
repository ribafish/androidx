/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@file:Suppress("NOTHING_TO_INLINE")

package androidx.core.graphics

import android.annotation.SuppressLint
import android.graphics.Matrix
import android.graphics.Point
import android.graphics.PointF
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.Region

/**
 * Returns "left", the first component of the rectangle.
 *
 * This method allows to use destructuring declarations when working with rectangles, for example:
 * ```
 * val (left, top, right, bottom) = myRectangle
 * ```
 */
public inline operator fun Rect.component1(): Int = this.left

/**
 * Returns "top", the second component of the rectangle.
 *
 * This method allows to use destructuring declarations when working with rectangles, for example:
 * ```
 * val (left, top, right, bottom) = myRectangle
 * ```
 */
public inline operator fun Rect.component2(): Int = this.top

/**
 * Returns "right", the third component of the rectangle.
 *
 * This method allows to use destructuring declarations when working with rectangles, for example:
 * ```
 * val (left, top, right, bottom) = myRectangle
 * ```
 */
public inline operator fun Rect.component3(): Int = this.right

/**
 * Returns "bottom", the fourth component of the rectangle.
 *
 * This method allows to use destructuring declarations when working with rectangles, for example:
 * ```
 * val (left, top, right, bottom) = myRectangle
 * ```
 */
public inline operator fun Rect.component4(): Int = this.bottom

/**
 * Returns "left", the first component of the rectangle.
 *
 * This method allows to use destructuring declarations when working with rectangles, for example:
 * ```
 * val (left, top, right, bottom) = myRectangle
 * ```
 */
public inline operator fun RectF.component1(): Float = this.left

/**
 * Returns "top", the second component of the rectangle.
 *
 * This method allows to use destructuring declarations when working with rectangles, for example:
 * ```
 * val (left, top, right, bottom) = myRectangle
 * ```
 */
public inline operator fun RectF.component2(): Float = this.top

/**
 * Returns "right", the third component of the rectangle.
 *
 * This method allows to use destructuring declarations when working with rectangles, for example:
 * ```
 * val (left, top, right, bottom) = myRectangle
 * ```
 */
public inline operator fun RectF.component3(): Float = this.right

/**
 * Returns "bottom", the fourth component of the rectangle.
 *
 * This method allows to use destructuring declarations when working with rectangles, for example:
 * ```
 * val (left, top, right, bottom) = myRectangle
 * ```
 */
public inline operator fun RectF.component4(): Float = this.bottom

/**
 * Performs the union of this rectangle and the specified rectangle and returns the result as a new
 * rectangle.
 */
public inline operator fun Rect.plus(r: Rect): Rect {
    return Rect(this).apply { union(r) }
}

/**
 * Performs the union of this rectangle and the specified rectangle and returns the result as a new
 * rectangle.
 */
public inline operator fun RectF.plus(r: RectF): RectF {
    return RectF(this).apply { union(r) }
}

/**
 * Returns a new rectangle representing this rectangle offset by the specified amount on both X and
 * Y axis.
 */
public inline operator fun Rect.plus(xy: Int): Rect {
    return Rect(this).apply { offset(xy, xy) }
}

/**
 * Returns a new rectangle representing this rectangle offset by the specified amount on both X and
 * Y axis.
 */
public inline operator fun RectF.plus(xy: Float): RectF {
    return RectF(this).apply { offset(xy, xy) }
}

/** Returns a new rectangle representing this rectangle offset by the specified point. */
public inline operator fun Rect.plus(xy: Point): Rect {
    return Rect(this).apply { offset(xy.x, xy.y) }
}

/** Returns a new rectangle representing this rectangle offset by the specified point. */
public inline operator fun RectF.plus(xy: PointF): RectF {
    return RectF(this).apply { offset(xy.x, xy.y) }
}

/** Returns the difference of this rectangle and the specified rectangle as a new region. */
public inline operator fun Rect.minus(r: Rect): Region {
    return Region(this).apply { op(r, Region.Op.DIFFERENCE) }
}

/**
 * Returns the difference of this rectangle and the specified rectangle as a new region. This
 * rectangle is first converted to a [Rect] using [RectF.toRect].
 */
public inline operator fun RectF.minus(r: RectF): Region {
    return Region(this.toRect()).apply { op(r.toRect(), Region.Op.DIFFERENCE) }
}

/**
 * Returns a new rectangle representing this rectangle offset by the negation of the specified
 * amount on both X and Y axis.
 */
public inline operator fun Rect.minus(xy: Int): Rect {
    return Rect(this).apply { offset(-xy, -xy) }
}

/**
 * Returns a new rectangle representing this rectangle offset by the negation of the specified
 * amount on both X and Y axis.
 */
public inline operator fun RectF.minus(xy: Float): RectF {
    return RectF(this).apply { offset(-xy, -xy) }
}

/**
 * Returns a new rectangle representing this rectangle offset by the negation of the specified
 * point.
 */
public inline operator fun Rect.minus(xy: Point): Rect {
    return Rect(this).apply { offset(-xy.x, -xy.y) }
}

/**
 * Returns a new rectangle representing this rectangle offset by the negation of the specified
 * point.
 */
public inline operator fun RectF.minus(xy: PointF): RectF {
    return RectF(this).apply { offset(-xy.x, -xy.y) }
}

/** Returns a new rectangle representing this rectangle's components each scaled by [factor]. */
public inline operator fun Rect.times(factor: Int): Rect {
    return Rect(this).apply {
        top *= factor
        left *= factor
        right *= factor
        bottom *= factor
    }
}

/** Returns a new rectangle representing this rectangle's components each scaled by [factor]. */
public inline operator fun RectF.times(factor: Int): RectF = times(factor.toFloat())

/** Returns a new rectangle representing this rectangle's components each scaled by [factor]. */
public inline operator fun RectF.times(factor: Float): RectF {
    return RectF(this).apply {
        top *= factor
        left *= factor
        right *= factor
        bottom *= factor
    }
}

/** Returns the union of two rectangles as a new rectangle. */
public inline infix fun Rect.or(r: Rect): Rect = this + r

/** Returns the union of two rectangles as a new rectangle. */
public inline infix fun RectF.or(r: RectF): RectF = this + r

/**
 * Returns the intersection of two rectangles as a new rectangle. If the rectangles do not
 * intersect, returns a copy of the left hand side rectangle.
 */
@SuppressLint("CheckResult")
public inline infix fun Rect.and(r: Rect): Rect {
    return Rect(this).apply { intersect(r) }
}

/**
 * Returns the intersection of two rectangles as a new rectangle. If the rectangles do not
 * intersect, returns a copy of the left hand side rectangle.
 */
@SuppressLint("CheckResult")
public inline infix fun RectF.and(r: RectF): RectF {
    return RectF(this).apply { intersect(r) }
}

/** Returns the union minus the intersection of two rectangles as a new region. */
public inline infix fun Rect.xor(r: Rect): Region {
    return Region(this).apply { op(r, Region.Op.XOR) }
}

/**
 * Returns the union minus the intersection of two rectangles as a new region. The two rectangles
 * are first converted to [Rect] using [RectF.toRect].
 */
public inline infix fun RectF.xor(r: RectF): Region {
    return Region(this.toRect()).apply { op(r.toRect(), Region.Op.XOR) }
}

/**
 * Returns true if the specified point is inside the rectangle. The left and top are considered to
 * be inside, while the right and bottom are not. This means that for a point to be contained: left
 * <= x < right and top <= y < bottom. An empty rectangle never contains any point.
 */
public inline operator fun Rect.contains(p: Point): Boolean = contains(p.x, p.y)

/**
 * Returns true if the specified point is inside the rectangle. The left and top are considered to
 * be inside, while the right and bottom are not. This means that for a point to be contained: left
 * <= x < right and top <= y < bottom. An empty rectangle never contains any point.
 */
public inline operator fun RectF.contains(p: PointF): Boolean = contains(p.x, p.y)

/** Returns a [RectF] representation of this rectangle. */
public inline fun Rect.toRectF(): RectF = RectF(this)

/**
 * Returns a [Rect] representation of this rectangle. The resulting rect will be sized such that
 * this rect can fit within it.
 */
public inline fun RectF.toRect(): Rect {
    val r = Rect()
    roundOut(r)
    return r
}

/** Returns a [Region] representation of this rectangle. */
public inline fun Rect.toRegion(): Region = Region(this)

/**
 * Returns a [Region] representation of this rectangle. The resulting rect will be sized such that
 * this rect can fit within it.
 */
public inline fun RectF.toRegion(): Region = Region(this.toRect())

/** Transform this rectangle in place using the supplied [Matrix] and returns this rectangle. */
public inline fun RectF.transform(m: Matrix): RectF = apply { m.mapRect(this@transform) }
