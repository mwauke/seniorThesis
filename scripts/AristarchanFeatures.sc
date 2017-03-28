import scala.io.Source
:load loadhmt.sc
:load ../seniorThesis/scripts/hmt-ngrams-revised.sc

def criticalSignPres (scholion: String) = {

  val scholiaToIliad = Source.fromFile("/Users/oxygen/Desktop/hmt-twiddle/data/scholiaToIliad.tsv").getLines.toVector.map(_.split("\t"))
  val commentUrn = scholion.replace(".comment","")

  def matchingLines(scholiaUrn: String, scholiaToIliad: Vector[Array[String]]) = {
    val scholMatch = scholiaToIliad.filter(_(0) == scholiaUrn)
    scholMatch
  }

  val scholLine = matchingLines(commentUrn, scholiaToIliad).flatten.toArray

  val scholCts = CtsUrn(scholLine(1))
  val diple = Cite2Urn("urn:cite2:hmt:critsign:diple")
  val ddiple = Cite2Urn("urn:cite2:hmt:critsign:dotteddiple")
  val obelos = Cite2Urn("urn:cite2:hmt:critsign:obelos")
  val asteriskos = Cite2Urn("urn:cite2:hmt:critsign:asteriskos")

  var criticalSign: Boolean = false

  val dipleSchol = orca.~~(scholCts).~~(diple).size
  val ddipleSchol = orca.~~(scholCts).~~(ddiple).size
  val obelosSchol = orca.~~(scholCts).~~(obelos).size
  val asteriskosSchol = orca.~~(scholCts).~~(asteriskos).size

  if ((dipleSchol + ddipleSchol + obelosSchol + asteriskosSchol) > 0) {
    criticalSign = true
  }

  criticalSign

}

def activeGraph (scholion: String) = {

  var activeGraphPresent = false
  val graph = corpus.urnsForNGram("γράφει",1,true)
  val graphUrn = graph.map(_.collapseBy(1).toString)
  val commentUrn = scholion.replace(".comment","")

  def matchingUrns(scholiaUrn: String, grafei: Vector[String]) = {

    val scholMatch = grafei.filter(_ == scholiaUrn)
    scholMatch

  }

  val matchingGrafei = matchingUrns(commentUrn, graphUrn)

  if (matchingGrafei.size > 0) {
    activeGraphPresent = true
  }

  activeGraphPresent

}

def hotiZenFeature (scholion: String) = {

var hotiZenPresent: Boolean = false
val hotiZen = corpus.urnsForNGram("ὅτι Ζηνόδοτος",2,true)
val hotiZenUrn = hotiZen.map(_.collapseBy(1).toString)
val commentUrn = scholion.replace(".comment","")

def matchingUrns(scholiaUrn: String, zenod: Vector[String]) = {

 val scholMatch = zenod.filter(_ == scholiaUrn)
 scholMatch

}

val matchingHotiZen = matchingUrns(commentUrn, hotiZenUrn)

if (matchingHotiZen.size > 0) {
  hotiZenPresent = true
}

hotiZenPresent

}

def nineToPanScore (scholion: String) = {

val fileName = "../seniorThesis/data/theta.csv"
val theta = Source.fromFile(fileName).getLines.toVector
val thetaSplit = theta.map(_.split(","))
val scoresList = thetaSplit.filter(_(1).contains(scholion)).flatten.toVector
val topic9Score = scoresList(11)
topic9Score

}

def sixToPanScore (scholion: String) = {

val fileName = "../seniorThesis/data/theta.csv"
val theta = Source.fromFile(fileName).getLines.toVector
val thetaSplit = theta.map(_.split(","))
val scoresList = thetaSplit.filter(_(1).contains(scholion)).flatten.toVector
val topic6Score = scoresList(8)
topic6Score

}

def aristarchanFeatures (scholion: String) {
  println(scholion + "\t" + criticalSignPres(scholion) + "\t" + activeGraph(scholion) + "\t" + hotiZenFeature(scholion) + "\t" + nineToPanScore(scholion) + "\t" + sixToPanScore(scholion))
}
