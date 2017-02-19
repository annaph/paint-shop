package com.gilt.challenge

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

import Colors.unapplySeq
import PaintShop.Color

/**
 * This class is a test suite for the {@link Colors} custom extractor.
 *
 * Three scenarios are tested:
 * 	Extracting empty input
 * 	Extracting valid input
 * 	Extracting invalid input
 *
 */
@RunWith(classOf[JUnitRunner])
class ColorsSuite extends FunSuite {

  test("Extract empty input") {
    val str = ""

    val expected = Some(List[Color]())
    val actual = unapplySeq(str)

    assert(actual.get == expected.get, "Extracting empty string failed")
  }

  test("Extract valid input") {
    val str = "1 M 3 G 5 G"

    val expected = Some(List(
      1 -> Matte,
      3 -> Gloss,
      5 -> Gloss))
    val actual = unapplySeq(str)

    assert(actual.get.reverse == expected.get, "Extracting valid string failed")
  }

  test("Extract invalid input") {
    val str = "1 M 3"

    intercept[Exception] {
      unapplySeq(str)
    }
  }

}
