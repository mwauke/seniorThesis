import scala.io.Source


def critSignEditor (scholiaType: String, editorURN: String, signType: String) {

//val scholiaType = "msAim"
//val editorURN = "16"
//val signType = "diple"
val corpus = CorpusSource.fromFile("data/hmt_2cols.tsv")
val scholia = corpus ~~ CtsUrn("urn:cts:greekLit:tlg5026." + scholiaType + ":")


// get all scholia which contain editor in question
val tokens = TeiReader.fromCorpus(scholia)
val editor = Cite2Urn("urn:cite2:hmt:pers:pers" + editorURN)
val editorTokens = tokens.filter(_.lexMatch(editor))
val editorScholia = editorTokens.map(_.textNode).map(_.collapseBy(1).toString)
val distinctScholia = editorScholia.distinct

// get alignment of scholia to Iliad lines
val scholiaToIliad = Source.fromFile("/Users/oxygen/Desktop/hmt-twiddle/data/scholiaToIliad.tsv").getLines.toVector.map(_.split("\t"))
val editorLines = distinctScholia.map(matchingLines(_,scholiaToIliad).flatten.toArray)

val editorCts = editorLines.map( t => CtsUrn(t(1)))
val criticalSign = Cite2Urn("urn:cite2:hmt:critsign:" + signType)
val editorSigns = orca.~~(editorCts).~~(criticalSign)

println("Number of pers " + editorURN + " scholia in " + scholiaType + ": " + editorLines.size)
println("Number of editor " + editorURN + " scholia with corresponding " + signType + " in " + scholiaType + ": " + editorSigns.size)

}

def matchingLines(scholiaUrn: String, scholiaToIliad: Vector[Array[String]]) = {

  val scholMatch = scholiaToIliad.filter(_(0) == scholiaUrn)
  scholMatch

}
