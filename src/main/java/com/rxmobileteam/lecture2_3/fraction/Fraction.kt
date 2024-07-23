package com.rxmobileteam.lecture2_3.fraction

import java.security.InvalidParameterException
import kotlin.math.round

class Fraction private constructor(
  val numerator: Int,
  val denominator: Int,
) : Comparable<Fraction> {
  val decimal: Double = numerator.toDouble() / denominator

  init {
    if (denominator == 0) {
      throw InvalidParameterException("Denominator cannot be 0")
    }
  }

  //region unary operators
  operator fun unaryPlus(): Fraction = Fraction(numerator + denominator, denominator)

  operator fun unaryMinus(): Fraction = Fraction(numerator - denominator, denominator)
  //endregion

  //region plus operators
  operator fun plus(other: Fraction): Fraction =
    Fraction((numerator * other.denominator + other.numerator * denominator), denominator * other.denominator)

  operator fun plus(other: Int): Fraction = Fraction(numerator + other * denominator, denominator)
  //endregion

  //region times operators
  operator fun times(other: Fraction): Fraction = Fraction(numerator * other.numerator, denominator * other.denominator)

  operator fun times(number: Int): Fraction = Fraction(numerator * number, denominator)
  //endregion

  override fun compareTo(other: Fraction): Int {
    if (decimal == other.decimal) return 0

    if (decimal < other.decimal) return -1

    return 1
  }

  //region toString, hashCode, equals, copy
  override fun toString(): String = decimal.toString()

  override fun hashCode(): Int {
    var result: Int = numerator.hashCode()
    result = 31 * result + denominator.hashCode()

    return result
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other == null || javaClass != other.javaClass) return false

    val fraction = other as Fraction

    if (compareTo(fraction) != 0) return false
    if (numerator != fraction.numerator) return false
    return denominator == fraction.denominator
  }

  fun copy(
    numerator: Int = this.numerator,
    denominator: Int = this.denominator
  ): Fraction = Fraction(numerator, denominator)
  //endregion

  companion object {
    @JvmStatic
    fun ofInt(number: Int): Fraction = Fraction(number, 1)

    @JvmStatic
    fun of(numerator: Int, denominator: Int): Fraction {
      if (denominator == 0) {
        throw InvalidParameterException("Denominator cannot be 0")
      }

      val gcd = gcd(numerator, denominator)

      val reducedNumerator = numerator / gcd
      val reducedDenominator = denominator / gcd

      return Fraction(reducedNumerator, reducedDenominator)
    }

    private fun gcd(a: Int, b: Int): Int {
      if (b == 0) {
        return a
      }
      return gcd(b, a % b)
    }
  }
}

infix fun Int.over(denominator: Int): Fraction = Fraction.of(this, denominator)

//region get extensions
operator fun Fraction.component1(): Int = numerator

operator fun Fraction.component2(): Int = denominator

operator fun Fraction.get(index: Int): Int {
  if (index < 0 || index > 1) {
    throw IndexOutOfBoundsException("Index $index is out of bounds")
  }

  if (index == 0) return component1()

  return component2()
}
//endregion

//region to number extensions
fun Fraction.roundToInt(): Int = round(decimal).toInt()

fun Fraction.roundToLong(): Long = round(decimal).toLong()

fun Fraction.toFloat(): Float = decimal.toFloat()

fun Fraction.toDouble(): Double = decimal
//endregion

fun main() {
  // Creation
  println("1/2: ${Fraction.of(1, 2)}")
  println("2/3: ${Fraction.of(2, 3)}")
  println("8: ${Fraction.ofInt(8)}")
  println("2/4: ${2 over 4}")
  println("-".repeat(80))

  // Unary operators
  println("+2/4: ${+Fraction.of(2, 4)}")
  println("-2/6: ${-Fraction.of(2, 6)}")
  println("-".repeat(80))

  // Plus operators
  println("1/2 + 2/3: ${Fraction.of(1, 2) + Fraction.of(2, 3)}")
  println("1/2 + 1: ${Fraction.of(1, 2) + 1}")
  println("-".repeat(80))

  // Times operators
  println("1/2 * 2/3: ${Fraction.of(1, 2) * Fraction.of(2, 3)}")
  println("1/2 * 2: ${Fraction.of(1, 2) * 2}")
  println("-".repeat(80))

  // compareTo
  println("3/2 > 2/2: ${Fraction.of(3, 2) > Fraction.of(2, 2)}")
  println("1/2 <= 2/4: ${Fraction.of(1, 2) <= Fraction.of(2, 4)}")
  println("4/6 >= 2/3: ${Fraction.of(4, 6) >= Fraction.of(2, 3)}")
  println("-".repeat(80))

  // hashCode
  println("hashCode 1/2 == 2/4: ${Fraction.of(1, 2).hashCode() == Fraction.of(2, 4).hashCode()}")
  println("hashCode 1/2 == 1/2: ${Fraction.of(1, 2).hashCode() == Fraction.of(1, 2).hashCode()}")
  println("hashCode 1/3 == 3/5: ${Fraction.of(1, 3).hashCode() == Fraction.of(3, 5).hashCode()}")
  println("-".repeat(80))

  // equals
  println("1/2 == 2/4: ${Fraction.of(1, 2) == Fraction.of(2, 4)}")
  println("1/2 == 1/2: ${Fraction.of(1, 2) == Fraction.of(1, 2)}")
  println("1/3 == 3/5: ${Fraction.of(1, 3) == Fraction.of(3, 5)}")
  println("-".repeat(80))

  // Copy
  println("Copy 1/2: ${Fraction.of(1, 2).copy()}")
  println("Copy 1/2 with numerator 2: ${Fraction.of(1, 2).copy(numerator = 2)}")
  println("Copy 1/2 with denominator 3: ${Fraction.of(1, 2).copy(denominator = 3)}")
  println("Copy 1/2 with numerator 2 and denominator 3: ${Fraction.of(1, 2).copy(numerator = 2, denominator = 3)}")
  println("-".repeat(80))

  // Component1, Component2 operators
  val (numerator, denominator) = Fraction.of(1, 2)
  println("Component1, Component2 of 1/2: $numerator, $denominator")
  val (numerator2, _) = Fraction.of(10, 30)
  println("Component1 of 10/30: $numerator2")
  val (_, denominator2) = Fraction.of(10, 79)
  println("Component2 of 10/79: $denominator2")
  println("-".repeat(80))

  // Get operator
  println("Get 0 of 1/2: ${Fraction.of(1, 2)[0]}")
  println("Get 1 of 1/2: ${Fraction.of(1, 2)[1]}")
  println("Get 2 of 1/2: ${runCatching { Fraction.of(1, 2)[2] }}") // Should print "Failure(...)"
  println("-".repeat(80))

  // toInt, toLong, toFloat, toDouble
  println("toInt 1/2: ${Fraction.of(1, 2).roundToInt()}")
  println("toLong 1/2: ${Fraction.of(1, 2).roundToLong()}")
  println("toFloat 1/2: ${Fraction.of(1, 2).toFloat()}")
  println("toDouble 1/2: ${Fraction.of(1, 2).toDouble()}")
  println("-".repeat(80))

  // Range
  // Because we implemented Comparable<Fraction>, we can use Fraction in ranges
  val range = Fraction.of(1, 2)..Fraction.of(2, 3)
  println("1/2 in range 1/2..2/3: ${Fraction.of(1, 2) in range}") // "in" operator is "contains"
  println("2/3 in range 1/2..2/3: ${Fraction.of(2, 3) in range}")
  println("7/12 in range 1/2..2/3: ${Fraction.of(7, 12) in range}")
  println("-".repeat(80))
}
