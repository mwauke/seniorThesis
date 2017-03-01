import scala.io.Source

@main
def dottedDipleZenodotus (scholiaType: String) {

val scholiaType = "msAim"
val corpus = CorpusSource.fromFile("data/hmt_2cols.tsv")
val scholia = corpus ~~ CtsUrn("urn:cts:greekLit:tlg5026." + scholiaType + ":")


// get all scholia which contain "Zenodotus"
val tokens = TeiReader.fromCorpus(scholia)
val zenodotus = Cite2Urn("urn:cite2:hmt:pers:pers15")
val zenodotusTokens = tokens.filter(_.lexMatch(zenodotus))
val zenodotusScholia = zenodotusTokens.map(_.textNode).map(_.collapseBy(1).toString)

// get alignment of scholia to Iliad lines
val scholiaToIliad = Source.fromFile("/Users/oxygen/Desktop/hmt-twiddle/data/scholiaToIliad.tsv").getLines.toVector.map(_.split("\t"))
val zenodotusLines = zenodotusScholia.map(matchingLines(_,scholiaToIliad).flatten.toArray)

val zenCts = zenodotusLines.map( t => CtsUrn(t(1)))
val ddiple = Cite2Urn("urn:cite2:hmt:critsign:dotteddiple")
val zenDotted = orca.~~(zenCts).~~(ddiple)

println("Number of Zenodotus scholia in " + scholiaType + ": " +zenodotusLines.size)
println("Number of Zenodotus scholia with corresponding dotted diples in " + scholiaType + ": " + zenDotted.size)

}

def matchingLines(scholiaUrn: String, scholiaToIliad: Vector[Array[String]]) = {

  val scholMatch = scholiaToIliad.filter(_(0) == scholiaUrn)
  scholMatch

}
