val scholiaType = "msA"

val corpus = CorpusSource.fromFile("data/hmt_2cols.tsv")
val scholia = corpus ~~ CtsUrn("urn:cts:greekLit:tlg5026." + scholiaType + ":")
val scholiaString = scholia.urns.map(_.toString)
val scholiaComments = scholiaString.filter(_.contains("comment"))
println("Number of scholia in " + scholiaType + ": " + scholiaComments.size)
