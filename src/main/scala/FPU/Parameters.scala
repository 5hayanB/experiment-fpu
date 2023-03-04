package FPU

trait Parameters {
  val flen          : Int = 32
  val regAddrLen    : Int = 5
  val bias          : Int = 127
  val biasLen       : Int = 8
  val significandLen: Int = 23
  val signLen       : Int = 1
}
