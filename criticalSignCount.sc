
def criticalSignCount (sign: String) {
  val sign = ""
  val iliad = CtsUrn("urn:cts:greekLit:tlg0012.tlg001:")
  val criticalSign = Cite2Urn("urn:cite2:hmt:critsign:" + sign)
  val iliadSign = orca.~~(iliad).~~(criticalSign)
  println("Number of " + sign + " : " + iliadSign.size)
}
