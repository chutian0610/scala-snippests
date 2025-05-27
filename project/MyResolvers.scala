import sbt.*

object MyResolvers {
  val aliyunPublic    = "Aliyun Public" at "https://maven.aliyun.com/repository/public"
  val aliyunCenter  = "Aliyun Center" at "https://maven.aliyun.com/repository/central"
  val huaweicloudMaven = "Huaweicloud Maven" at "https://repo.huaweicloud.com/repository/maven/"
  val mavenCentral = "Maven" at "https://repo1.maven.org/maven2"

  val allResolver: Seq[MavenRepository] = Seq(
    Resolver.mavenLocal,
    mavenCentral.withAllowInsecureProtocol(true),
    aliyunPublic.withAllowInsecureProtocol(true),
    aliyunCenter.withAllowInsecureProtocol(true),
    huaweicloudMaven.withAllowInsecureProtocol(true)
  )
}
