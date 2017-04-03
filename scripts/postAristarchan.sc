import scala.io.Source
:load loadhmt.sc
:load initial-ngram-histo.sc
:load ../seniorThesis/scripts/hmt-ngrams-revised.sc

def matchingUrns(scholiaUrn: String, urnList: Vector[String]) = {

  val scholMatch = urnList.filter(_ == scholiaUrn)
  scholMatch

}

def paraZenod(scholion: String) = {

  var paraZenPresent: Boolean = false
  val paraZen = corpus.urnsForNGram("παρά Ζηνοδότῳ",2,true)
  val paraZenUrn = paraZen.map(_.toString)
  val matchingParaZen = matchingUrns(scholion, paraZenUrn)

  if (matchingParaZen.size > 0) {
    paraZenPresent = true
  }

  paraZenPresent

}

/* def aristarchusScholia(scholion: String) = {

  var aristarchusPresent: Boolean = false
  val corpus = CorpusSource.fromFile("data/hmt_2cols.tsv")
  val mainScholia = corpus ~~ CtsUrn("urn:cts:greekLit:tlg5026.msA:")
  val imScholia = corpus ~~ CtsUrn("urn:cts:greekLit:tlg5026.msAim:")
  val intScholia = corpus ~~ CtsUrn("urn:cts:greekLit:tlg5026.msAint:")
  val ilScholia = corpus ~~ CtsUrn("urn:cts:greekLit:tlg5026.msAil:")
  val extScholia = corpus ~~ CtsUrn("urn:cts:greekLit:tlg5026.msAext:")
  val allScholia = mainScholia ++ imScholia ++ intScholia
  val tokens = TeiReader.fromCorpus(allScholia)
  val aristarchus = Cite2Urn("urn:cite2:hmt:pers:pers16")
  val aristarchusTokens = tokens.filter(_.lexMatch(aristarchus))
  val aristarchusScholia = aristarchusTokens.map(_.textNode)
  val distinctAristarchus = aristarchusScholia.distinct.map(_.toString)
  val matchingAristarchus = matchingUrns(scholion,distinctAristarchus)
  if (matchingAristarchus.size > 0){
    aristarchusPresent = true
  }

  aristarchusPresent
}
*/

def postArist(scholion: String, postArScholia: Vector[String]) = {
//  val nameFile = "../mainPostAristarchan.txt"
//  val mainNameList = Source.fromFile(nameFile).getLines.toVector
  var postArNamePresent: Boolean = false
  val matchingNames = matchingUrns(scholion, postArScholia)
  if (matchingNames.size > 0) {
    postArNamePresent = true
  }
  postArNamePresent
}

def postAristarchanFeatures (scholiaType: String, postArUrns: String) = {
  val file = "../seniorThesis/data/aristarchanFeaturesData/" + scholiaType + "-Aristarchan.tsv"
  val features = Source.fromFile(file).getLines.toVector
  val featList = features.map(_.split("\t"))
  val urnList = "../" + postArUrns
  val postArScholia = Source.fromFile(urnList).getLines.toVector
  for (t <- featList) {
  println(t(0) + "\t" + t(1) + "\t" + t(2) + "\t" + t(3) + "\t" + paraZenod(t(0)) + "\t" + postArist(t(0),postArScholia) + "\t" + t(4) + "\t" + t(5))
  }
}
