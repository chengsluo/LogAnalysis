import java.io.{BufferedReader, InputStreamReader}
import java.net.URLDecoder
import java.sql.Date
import java.util.zip.ZipInputStream
import org.apache.spark.sql.{DataFrame, SparkSession}

object MainApp {

  val date=List("2017-07-14", "2017-08-02","2017-08-21", "2017-09-09", "2017-10-22",
    "2017-11-12", "2017-07-15", "2017-08-03", "2017-08-22", "2017-09-10", "2017-10-23",
    "2017-11-13", "2017-07-16", "2017-08-04", "2017-08-23", "2017-09-11", "2017-10-24",
    "2017-11-14", "2017-07-17", "2017-08-05", "2017-08-24", "2017-09-12", "2017-10-25",
    "2017-11-15", "2017-07-18", "2017-08-06", "2017-08-25", "2017-09-13", "2017-10-26",
    "2017-11-16", "2017-07-19", "2017-08-07", "2017-08-26", "2017-09-14", "2017-10-27",
    "2017-11-17", "2017-07-20", "2017-08-08", "2017-08-27", "2017-09-15", "2017-10-28",
    "2017-11-18", "2017-07-21", "2017-08-09", "2017-08-28", "2017-09-16", "2017-10-29",
    "2017-11-19", "2017-07-22", "2017-08-10", "2017-08-29", "2017-09-17", "2017-10-30",
    "2017-11-20", "2017-07-23", "2017-08-11", "2017-08-30", "2017-09-18", "2017-10-31",
    "2017-11-24", "2017-07-24", "2017-08-12", "2017-08-31", "2017-09-19", "2017-11-01",
    "2017-11-25", "2017-07-25", "2017-08-13", "2017-09-01", "2017-09-20", "2017-11-02",
    "2017-11-26", "2017-07-26", "2017-08-14", "2017-09-02", "2017-09-21", "2017-11-03",
    "2017-11-27", "2017-07-27", "2017-08-15", "2017-09-03", "2017-09-22", "2017-11-04",
    "2017-11-28", "2017-07-28", "2017-08-16", "2017-09-04", "2017-09-23", "2017-11-05",
    "2017-11-29", "2017-07-29", "2017-08-17", "2017-09-05", "2017-11-06", "2017-07-30",
    "2017-08-18", "2017-09-06", "2017-10-19", "2017-11-07", "2017-07-31", "2017-08-19",
    "2017-09-07", "2017-10-20", "2017-11-08", "2017-08-01", "2017-08-20", "2017-09-08",
    "2017-10-21", "2017-11-11")

  case class LogDataFormat (t1:Int,t2:Int,
                            date:Date, t3:String,time:String,
                            targetUrl:String,
                            JoinWay:String, MAC:String, joinIP:String,
                            targetPort:Int, t4:Int,
                            targetIP:String, userPort:Int,
                            t5:Int, mask:String, t6:Int)

  def main(args: Array[String]) {

    if (args.length < 3) {
      println("需要3个路径参数")
    } else {
      val zipFilePath = args(0)
      val dataPath = args(1)
      val resPath =args(2)
      val spark = SparkSession.builder()
        .appName("LogAnalysis")
        .config("spark.hadoop.validateOutputSpecs", "false")
        .getOrCreate()
      val sc=spark.sparkContext
      val test = List("2017-10-26","2017-11-03","2017-10-25","2017-11-02")
      //test.foreach(day => singleFunction.AssemblyOne(day))
      //singleFunction.AssemblyTwo()
      test.foreach(day=>singleFunction.AssemblyThree(day))

      object singleFunction {

        //read the zip files to parquet
        def AssemblyOne(day:String): Unit ={
          readZipFilesToParquet(day)
        }
        //test and count the data
        def AssemblyTwo(): Unit ={
          val logsDF=readParquetFast(dataPath+"logs.parquet")
          logsDF.groupBy("date").count().show()
          logsDF.createTempView("logs")
          spark.sql("SELECT  date,count(1) FROM logs WHERE GROUP BY date").show()
        }
        //baidu search analysis
        def AssemblyThree(day:String): Unit ={
          val logsDF=readParquetFast(dataPath+"logs.parquet/date="+day)
          baiduSearchAnalysis(logsDF,day)
          //showTextResult(resPath+day+"/word")
          //showTextResult(resPath+day+"/paper")
        }

        def readZipFilesToParquet(day:String){
          // For implicit conversions like converting RDDs to DataFrames
          import spark.implicits._
          sc.binaryFiles(zipFilePath + day + "/*")
            .flatMap {
              case (_, zipContent) => val zis = new ZipInputStream(zipContent.open)
                Stream.continually(zis.getNextEntry)
                  .takeWhile(_ != null)
                  .flatMap { _ =>
                    val br = new BufferedReader(new InputStreamReader(zis))
                    Stream.continually(br.readLine()).takeWhile(_ != null)
                  }
            }.coalesce(38).map(s=>s.split("\t| "))
            //filter the error data,have about 0.05%
            .filter(s=>s.length==16)
            .map(s=>LogDataFormat(s(0).toInt,
              s(1).toInt,
              Date.valueOf(s(2)),
              s(3),
              s(4),
              URLDecoder.decode(s(5).replaceAll("%(?![0-9a-fA-F]{2})", "%25"), "utf-8"),
              s(6),
              s(7),
              s(8),
              s(9).toInt,
              s(10).toInt,
              s(11),
              s(12).toInt,
              s(13).toInt,
              s(14),
              s(15).toInt))
            .toDF().write.mode("append").partitionBy("date").format("parquet").save(dataPath+"logs.parquet")
        }
        //Read the preprocessed data from HDFS
        def readParquetFast(dirName:String):DataFrame={
          //coalesce the partition same the number of executors
          spark.read.parquet(dirName)
        }
        //Analysis baidu search
        def baiduSearchAnalysis(df:DataFrame,day:String): Unit ={
          val regex ="""(?<=/s\?(wd|word)=)[^&]+(?=&)""".r
          import spark.implicits._
          val searchDF= df.filter($"targetUrl".contains("baidu") and($"targetUrl" rlike """/s\?(wd|word)[^&]+(?=&)""")).persist()

          searchDF.write.mode("Overwrite").format("parquet").save(dataPath+day+"/search.parquet")

          searchDF.select("targetUrl").filter(!$"targetUrl".contains("d=paperuri")).rdd
            .map(s=>(URLDecoder.decode(regex.findFirstIn(s.toString)
              .toString.replaceAll("%(?![0-9a-fA-F]{2})", "%25"), "utf-8"),1))
            .filter(s=>s._1.contains("�")==false)
            .reduceByKey((a, b) => a + b)
            .map(s => (s._2, s._1))
            .sortByKey(false)
            .map(s=>"{\"value\":"+s._1+",\"name\":"+s._2.toString.substring(5,s._2.toString.length-1)+"},")
            .toDF()
            //.coalesce(1)
            .write.mode("Overwrite").format("text").save(resPath+day+"/word")

          searchDF.select("targetUrl").filter($"targetUrl".contains("d=paperuri")).rdd
            .map(s=>(URLDecoder.decode(regex.findFirstIn(s.toString).toString.replaceAll("%(?![0-9a-fA-F]{2})", "%25"), "utf-8"),1))
            .reduceByKey((a, b) => a + b)
            .map(s => (s._2, s._1))
            .sortByKey(false)
            .map(s=>"{\"value\":"+s._1+",\"name\":"+s._2.toString.substring(5,s._2.toString.length-1)+"},")
            .toDF()
            //.coalesce(1)
            .write.mode("Overwrite").format("text").save(resPath+"/"+day+"/paper")

          searchDF.unpersist()
        }

        //Read the text result
        def showTextResult(dirName:String):Unit={
          spark.read.text(dirName).foreach(s=>println(s(0)))
        }

        //Analysis the visit website
        def websiteAnalysis(df:DataFrame): Unit ={

        }

      }
    }
  }
}