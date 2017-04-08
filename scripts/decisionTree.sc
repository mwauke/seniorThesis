/*
This is a pretty crude script, but it does create a DecisionTreeClassifier from
the data in https://raw.githubusercontent.com/mwauke/seniorThesis/master/data/featureAnalysisSample.csv

The main work is done by a case class that defines the features used
in a machine learning model, and creates to values we can use in Spark ML
algorithms:  a *label* (Double), and a *features* vector.

The dataframe that is created using this case class can then be used in
generic Spark ML pipelines.

*/
val url = "https://raw.githubusercontent.com/mwauke/seniorThesis/master/data/featureAnalysisSample.csv"

import scala.io.Source
import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.classification.DecisionTreeClassificationModel
import org.apache.spark.ml.classification.DecisionTreeClassifier
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator

import org.apache.spark.ml.linalg.Vector
import org.apache.spark.ml.linalg.Vectors




////////////// A case class and some utilities.



/** Accept 11 members, and use them to make
* a label and a features vector for Spark.
*/
case class AristarchanFeatures (
  scholion: String,

  criticalSign: Boolean,
  grafei: Boolean,
  hotiInitial: Boolean,
  paraZen: Boolean,
  aristName: Boolean,
  postArist: Boolean,

  topic6: Double,
  topic9: Double,

  zone: String,
  aristarchanClass: Double
  )  {
    import org.apache.spark.ml.linalg.Vector
    import org.apache.spark.ml.linalg.Vectors


    // Machine learning in Spark needs a "label" (classification value,
    // given as a Double), and a Vector of features
    def label = aristarchanClass
    // NB:  this is a *Spark* vector, not a generic Scala Vector object!
    def features = {
      // create Vector of Doubles
      val c1 = {
        if (criticalSign) { 1.0} else {0.0}
      }
      val c2 = {
        if (grafei) { 1.0} else {0.0}
      }
      val c3 = {
        if (hotiInitial) { 1.0} else {0.0}
      }
      val c4 = {
        if (paraZen) { 1.0} else {0.0}
      }
      val c5 = {
        if (aristName) { 1.0} else {0.0}
      }
      val c6 = {
        if (postArist) { 1.0} else {0.0}
      }
      val msa = { if (zone == "main") {1.0} else {0.0} }
      val msaim = { if (zone == "intermarginal") {1.0} else {0.0}}
      val msaint = { if (zone == "interior") {1.0} else {0.0} }


      // DECIDE WHAT FEATURS YOU WANT TO INCLUDE:
      Vectors.dense(
        //c1,c2,c3,c4,c5,c6,topic6,topic9,msa,msaim,msaint
        c1,c2,c3,c4,c5,c6,topic6,topic9//,msa,msaim,msaint
      )
    }
}


/** Assign numeric value for four class names.
*/
def classForString(s: String): Double = {
  s match {
    case "Aristarchan" => 1.0
    case "paraphrase" => 2.0
    case "post" => 3.0
    case "indeterminate" => 4.0
  }
}


/** Create AristarchanFeatures object from the array of Strings
* you get by splitting up csv data on commans.
*/
def featuresFromStrings(row: Array[String]) : AristarchanFeatures = {
  AristarchanFeatures(
  row(0),

  row(1).toBoolean, row(2).toBoolean, row(3).toBoolean,
  row(4).toBoolean,row(5).toBoolean,row(6).toBoolean,

  row(7).toDouble, row(8).toDouble,

  row(9),
  classForString(row(10))
  )
}





//////////////////////////////////////////////////
// Here's where the work starts:
//
// Part 1:  get on line data, and create appropriate data
// structure for Spark machine learning.
//
// read from URL, and drop the header line
val lines = Source.fromURL(url).getLines.toVector.drop(1)
val tidy = lines.map(_.replaceAll('"'.toString,""))

// vector of AristarchanFeatures
val aristarchanVector = tidy.map{ s => featuresFromStrings(s.split(",")) }
// pull out the label and features vector from each AristarchanFeatures,
// make a data frame object, and assign the default names "label" and
// "features" to the two "columns".
val aristarchanData = aristarchanVector.map( af => (af.label, af.features))
val dataFrame = sc.parallelize(aristarchanData).toDF
val renamed = dataFrame.withColumnRenamed("_1","label").withColumnRenamed("_2","features")


//
// Part 2:  generic Spark machine learning process.
// Split data into a training and test group, fit data to a
// model, and make predictions.
///
val Array(trainingData, testData) = renamed.randomSplit(Array(0.7, 0.3))
val dt = new DecisionTreeClassifier()
val model = dt.fit(trainingData)
val predictions = model.transform(testData)
val treemodel = model.asInstanceOf[DecisionTreeClassificationModel]
// NB: treemodel.toDebugString shows you a human-readable display
// of the decision tree!

val evaluator = new MulticlassClassificationEvaluator()
val accuracy = evaluator.evaluate(predictions)
// NB: there are lots of other things we can look at: perplexity, other
// measures that might help us understand how the model works.
