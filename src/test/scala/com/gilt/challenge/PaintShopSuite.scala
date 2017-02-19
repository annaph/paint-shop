package com.gilt.challenge

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

import PaintShop.mix

/**
 * This class is a test suite for the {@link PaintShop} object.
 *
 * Besides testing scenarios mentioned in Programming Challenge doc additional scenarios
 * are introduced:
 * 	mixing 0 colors with 0 customers
 *  mixing colors with customers where some colors are not wished by any customer
 *  mixing colors with customers where some customers have same color wishes
 *
 */
@RunWith(classOf[JUnitRunner])
class PaintShopSuite extends FunSuite {

  test("Mix 0 colors and 0 customers") {
    val numOfColors = 0
    val customers = List[Customer]()

    val expected = Some(List[Batch]())
    val actual = mix(numOfColors, customers)

    assert(actual.get == expected.get, "Incorrect colors and customers mixing")
  }

  test("Mix with unwished colors") {
    val numOfColors = 3
    val customers = List(
      Customer(List(1 -> Gloss)))

    val expected = Some(List(Gloss, Gloss, Gloss))
    val actual = mix(numOfColors, customers)

    assert(actual.get == expected.get, "Incorrect colors and customers mixing")
  }

  test("Mix customers with same wishes") {
    val numOfColors = 5
    val customers = List(
      Customer(List(1 -> Matte, 3 -> Gloss, 5 -> Gloss)),
      Customer(List(2 -> Gloss, 3 -> Matte, 4 -> Gloss)),
      Customer(List(1 -> Matte, 3 -> Gloss, 5 -> Gloss)),
      Customer(List(5 -> Matte)))

    val expected = Some(List(Gloss, Gloss, Gloss, Gloss, Matte))
    val actual = mix(numOfColors, customers)

    assert(actual.get == expected.get, "Incorrect colors and customers mixing")
  }

  test("Mix 5 colors and 3 customers") {
    val numOfColors = 5
    val customers = List(
      Customer(List(1 -> Matte, 3 -> Gloss, 5 -> Gloss)),
      Customer(List(2 -> Gloss, 3 -> Matte, 4 -> Gloss)),
      Customer(List(5 -> Matte)))

    val expected = Some(List(Gloss, Gloss, Gloss, Gloss, Matte))
    val actual = mix(numOfColors, customers)

    assert(actual.get == expected.get, "Incorrect colors and customers mixing")
  }

  test("Mix colors and solutions where no solution exists") {
    val numOfColors = 1
    val customers = List(
      Customer(List(1 -> Gloss)),
      Customer(List(1 -> Matte)))

    val expected = None
    val actual = mix(numOfColors, customers)

    assert(actual == expected, "Incorrect colors and customers mixing")
  }

  test("Mix 2 colors and 2 customers") {
    val numOfColors = 2
    val customers = List(
      Customer(List(1 -> Gloss, 2 -> Matte)),
      Customer(List(1 -> Matte)))

    val expected = Some(List(Matte, Matte))
    val actual = mix(numOfColors, customers)

    assert(actual.get == expected.get, "Incorrect colors and customers mixing")
  }

  test("Mix 5 colors and 14 customers") {
    val numOfColors = 5
    val customers = List(
      Customer(List(2 -> Matte)),
      Customer(List(5 -> Gloss)),
      Customer(List(1 -> Gloss)),
      Customer(List(5 -> Gloss, 1 -> Gloss, 4 -> Matte)),
      Customer(List(3 -> Gloss)),
      Customer(List(5 -> Gloss)),
      Customer(List(3 -> Gloss, 5 -> Gloss, 1 -> Gloss)),
      Customer(List(3 -> Gloss)),
      Customer(List(2 -> Matte)),
      Customer(List(5 -> Gloss, 1 -> Gloss)),
      Customer(List(2 -> Matte)),
      Customer(List(5 -> Gloss)),
      Customer(List(4 -> Matte)),
      Customer(List(5 -> Gloss, 4 -> Matte)))

    val expected = Some(List(Gloss, Matte, Gloss, Matte, Gloss))
    val actual = mix(numOfColors, customers)

    assert(actual.get == expected.get, "Incorrect colors and customers mixing")
  }

}
