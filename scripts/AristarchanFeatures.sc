import scala.io.Source
:load loadhmt.sc
:load initial-ngram-histo.sc
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

def matchingUrns(scholiaUrn: String, urnList: Vector[String]) = {

  val scholMatch = urnList.filter(_ == scholiaUrn)
  scholMatch

}

def activeGraph (scholion: String) = {

  var activeGraphPresent = false
  val graph = corpus.urnsForNGram("γράφει",1,true)
  val graphUrn = graph.map(_.toString)
  val matchingGrafei = matchingUrns(scholion, graphUrn)

  if (matchingGrafei.size > 0) {
    activeGraphPresent = true
  }

  activeGraphPresent

}

def initialHotiSchol(scholion: String) = {

  val initHot = hotiNGrams(scholiaSrc, 1)
  val hotiScholia = initHot.map(_(0))
  var initialHotiPresent: Boolean = false
  val matchingHoti = matchingUrns(scholion, hotiScholia)
  if (matchingHoti.size > 0) {
    initialHotiPresent = true
  }
  initialHotiPresent
}

//def hotiZenFeature (scholion: String) = {

//var hotiZenPresent: Boolean = false
//val hotiZen = corpus.urnsForNGram("ὅτι Ζηνόδοτος",2,true)
//val hotiZenUrn = hotiZen.map(_.toString)
//val matchingHotiZen = matchingUrns(scholion, hotiZenUrn)

//if (matchingHotiZen.size > 0) {
  //hotiZenPresent = true
//}

//hotiZenPresent

//}


def aristarchanFeatures (scholion: String) {
  val fileName = "../seniorThesis/data/theta.csv"
  val theta = Source.fromFile(fileName).getLines.toVector
  val thetaSplit = theta.map(_.split(","))
  val scoresList = thetaSplit.filter(_(1).contains(scholion)).flatten.toVector
  val topic6Score = scoresList(8)
  val topic9Score = scoresList(11)
  println(scholion + "\t" + criticalSignPres(scholion) + "\t" + activeGraph(scholion) + "\t" + initialHotiSchol(scholion) + "\t" + topic6Score + "\t" + topic9Score)
}
