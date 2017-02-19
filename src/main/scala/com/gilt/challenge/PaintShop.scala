package com.gilt.challenge

import scala.annotation.tailrec

import PaintShop.Color

/**
 * Presents color type.
 *
 * Color type (or batch) can be one of:
 * 	Matte,
 * 	Gloss or
 * 	Unknown
 *
 */
sealed trait Batch

object Matte extends Batch
object Gloss extends Batch
object Unknown extends Batch

/**
 * Customer is presented with a list of color wishes.
 *
 */
case class Customer(colors: List[Color]) {
  private val n = colors.length

  def numOfColors: Int = n
}

/**
 * Following algorithm is used to mix colors:
 * 	1. Sort list of customers ascending to the number of color wishes, so that customers
 *     with less wishes are processed before customers with more wishes.
 *  2. Examine all colors off every customer to find all colors that might be mattes and
 *     colors that are definitely gloss.
 *     Based on this information create initial solution with colors that are definitely gloss.
 * 	3. Take initial solution from previous step and make first customer happy by creating a
 *     list of all solutions that make first customer happy.
 * 	4. Take previous generated list of solutions and second customer to generate new
 * 		 list of solutions in which both first and second customer are happy.
 * 	5. Use same logic as in previous step for all customer.
 * 	6. Once all customers are processed, take list of all solutions that make every customer happy to
 *     find best solution. Best solution is one with the lowest number of mattes.
 *
 * Algorithm walk-through using following scenario from Programming Challenge doc:
 * 	5
 * 	1 M 3 G 5 G
 * 	2 G 3 M 4 G
 * 	5 M
 *
 * In this scenario customers are presented with following list:
 * 	Customer 1. : List(1 -> Matte, 3 -> Gloss, 5 -> Gloss)
 * 	Customer 2. : List(2 -> Gloss, 3 -> Matte, 4 -> Gloss)
 * 	Customer 3. : List(5 -> Matte)
 *
 * Algorithm steps:
 * 	1. After sorting, new list of customers is:
 * 			Customer 3. : List(5 -> Matte)
 * 			Customer 1. : List(1 -> Matte, 3 -> Gloss, 5 -> Gloss)
 * 			Customer 2. : List(2 -> Gloss, 3 -> Matte, 4 -> Gloss)
 *
 *  2. Colors that might be mattes are:
 *  		'1  3  5'
 *  	 colors that are definitely gloss are:
 *  		'2  4'
 *
 *     Base on this information initial solution is created:
 *     	'U  G  U  G  U'
 *
 *     where U here stands for unknown type.
 *
 * 	2. Make third customer happy. Expand initial solution with new solutions that
 *     guarantee third customer is happy. This results in creating a list of only one solution:
 *     	'U  G  U  G  U' maps to:
 * 				1.:		'U  G  U  G  M'
 *
 * 		where U here also stands for type irrelevant to customer happiness.
 *
 * 	3. Make first customer happy. Third customer solution is expanded with new solutions that
 * 		 guarantee both third and first customer are happy. This result in three possible solutions:
 *
 * 			'U  G  U  G  M'  maps to:
 * 				1.:  'M  G  M  G  M'
 * 				2.:  'M  G  G  G  M'
 * 				3.:  'G  G  G  G  M'
 *
 * 	4. Make second customer happy. Solutions from previous step are expanded with new solutions that
 * 		 guarantee all customers are happy. This results in same list of solution as in previous step
 *     since there are no U types and all previous solutions make second customer also happy:
 *
 * 			'M  G  M  G  M' maps to:
 * 				1.:   'M  G  M  G  M'
 *
 * 			'M  G  G  G  M' maps to:
 * 				2.:   'M  G  G  G  M'
 *
 * 			'G  G  G  G  M' maps to:
 * 				3.:   'G  G  G  G  M'
 *
 * 	5. Solution number 3, 'G  G  G  G  M', is the best since it has lowest number of M's.
 *
 */

object PaintShop {

  /**
   * Color wish is presented with Tuple2 consisting of color number and color type (or batch).
   */
  type Color = (Int, Batch)

  /**
   * Solution is presented as an array of color types.
   */
  type Solution = Array[Batch]

  /**
   * Mix colors to find a solution where for each customer at least one color wish is satisfied and
   * where solution contains as less as possible mattes.
   *
   * If solution cannot be found None is returned.
   *
   * If solution can be found then Some representing list of Batch object is returned. List size is equal to
   * numOfColors.
   *
   */
  def mix(numOfColors: Int, customers: List[Customer]): Option[List[Batch]] = {
    val sorted = customers sortWith { _.numOfColors < _.numOfColors }
    val init: Solution = initialSolution(numOfColors, customers)
    val solutions: List[Solution] =
      sorted.foldLeft(List(init)) { makeMeHappy(_, _) }

    bestSolution(solutions) map { _.toList }
  }

  private def initialSolution(numOfColors: Int, customers: List[Customer]): Solution = {
    val initSolution: Solution = Array.fill(numOfColors)(Unknown)
    val mattes: Set[Int] = maybeMattes(customers)

    for {
      i <- 0 until numOfColors
      if (!mattes.contains(i + 1))
    } {
      initSolution(i) = Gloss
    }

    initSolution
  }

  private def maybeMattes(customers: List[Customer]): Set[Int] =
    (for {
      c <- customers
    } yield {
      c.colors find { _._2 == Matte } map { _._1 } getOrElse -1
    }).toSet

  private def makeMeHappy(solutions: List[Solution], customer: Customer): List[Solution] =
    solutions flatMap { newSolutions(_, customer) }

  private def newSolutions(solution: Solution, customer: Customer): List[Solution] = {
    @tailrec
    def loop(c: List[Color], acc: List[Solution]): List[Solution] = c match {
      case Nil =>
        acc
      case (i, _) :: t if solution(i - 1) == Unknown =>
        val newSols = acc flatMap { s =>
          val a1 = s map { x => x }
          a1(i - 1) = Matte
          val a2 = s map { x => x }
          a2(i - 1) = Gloss
          List(a1, a2)
        }
        loop(t, newSols)
      case _ :: t =>
        loop(t, acc)
    }

    loop(customer.colors, List(solution)) filter { s =>
      customer.colors exists {
        case (i, b) =>
          b == s(i - 1)
      }
    }
  }

  private def bestSolution(solutions: List[Solution]): Option[Solution] = solutions match {
    case Nil =>
      None
    case _ =>
      val best = solutions.foldLeft((Int.MaxValue -> Array[Batch]())) { (acc, s) =>
        val p = countMattes(s) -> s
        if (p._1 < acc._1)
          p
        else
          acc
      }
      Some(best._2)
  }

  private def countMattes(solution: Solution): Int =
    solution count {
      case Matte =>
        true
      case _ =>
        false
    }

}
