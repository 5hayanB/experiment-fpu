package FPU

trait Parameters {
  val flen            : Int = 32
  val regAddrWidth    : Int = 5
  val bias            : Int = 127
  val exponentWidth   : Int = 8
  val significandWidth: Int = 23
  val signWidth       : Int = 1
  val lenOpFp         : Int = 7
  val lenF5           : Int = 5
  val lenFmt          : Int = 2
}
