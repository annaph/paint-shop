package com.gilt.challenge

import scala.annotation.tailrec

import PaintShop.Color

/**
 * Custom extractor used to extract sequence of Color objects from input string
 * that describes a customer.
 *
 * E.g., for string '1  M  3  G  5  G' following sequence of Color object is extracted:
 * 	Color(1, M)
 * 	Color(3, G)
 * 	Color(5, G)
 *
 */
object Colors {

  def unapplySeq(whole: String): Option[Seq[Color]] = {
    val cs: List[Char] = whole.toList filter { _ != ' ' }

    @tailrec
    def loop(css: List[Char], acc: List[Color]): List[Color] = css match {
      case Nil =>
        acc
      case i :: 'M' :: t =>
        loop(t, (i.asDigit -> Matte) :: acc)
      case i :: 'G' :: t =>
        loop(t, (i.asDigit -> Gloss) :: acc)
      case _ =>
        throw new Exception("wrong input")
    }

    Some(loop(cs, List[Color]()))
  }
}
