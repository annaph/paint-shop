package com.gilt.challenge

import scala.io.Source
import scala.util.Failure
import scala.util.Success
import scala.util.Try

/**
 * Application entry point.
 *
 * Reads an input file as a command line argument, process data and
 * prints a result to standard out.
 *
 */
object Main extends App {

  val lines: Try[Iterator[String]] = Try {
    Source.fromFile(args(0)).getLines
  }

  lines match {
    case Success(v) =>
      processFile(v)
    case Failure(e) =>
      println("Cannot read input file: " + e.getMessage)
  }

  private def processFile(lines: Iterator[String]): Unit = {
    val numOfColors: Try[Int] = Try {
      lines.next.toInt
    }

    val customers: Try[List[Customer]] = Try {
      (for {
        line <- lines
      } yield {
        line match {
          case Colors(cs @ _*) =>
            Customer(cs.toList)
          case _ =>
            throw new Exception("wrong input")
        }
      }).toList
    }

    val msg: Try[String] = for {
      n <- numOfColors
      c <- customers
    } yield {
      PaintShop.mix(n, c) match {
        case Some(l) if !l.isEmpty =>
          (l map {
            case Matte =>
              'M'
            case _ =>
              'G'
          }).mkString(" ")
        case _ =>
          "No solution exists"
      }
    }

    msg match {
      case Success(v) =>
        println(v)
      case Failure(e) =>
        println("Exception occurred: " + e.getMessage)
    }
  }

}
