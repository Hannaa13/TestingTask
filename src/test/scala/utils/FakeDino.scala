package utils

import scalatest.Dino

// https://stackoverflow.com/questions/18346214/how-do-i-create-mock-if-my-trait-uses-self-type-restriction
trait FakeDino extends Dino {
  def name = "mock"
}