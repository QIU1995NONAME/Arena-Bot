import java.net.URI

val kOfficialUrlJCenter = "https://jcenter.bintray.com"
val kOfficialUrlGoogle = "https://dl.google.com/dl/android/maven2"
val kOfficialUrlMaven2 = "https://repo1.maven.org/maven2"
val kOfficialUrlMaven = "https://repo.maven.apache.org/maven2"

val kAliMirrorUrlPublic = "https://maven.aliyun.com/repository/public/"
val kAliMirrorUrlJCenter = "https://maven.aliyun.com/repository/jcenter/"
val kAliMirrorUrlGoogle = "https://maven.aliyun.com/repository/google/"
val kAliMirrorUrlMaven = "https://maven.aliyun.com/repository/central/"

allprojects {
    buildscript {
        repositories {
            maven {
                url = URI(kAliMirrorUrlPublic)
            }
            all {
                if (this is MavenArtifactRepository) {
                    val currentUrl = this.url.toString()
                    if (currentUrl.startsWith(kOfficialUrlJCenter)) {
                        project.logger.lifecycle("[INIT] Repository ${this.url} replaced by ${kAliMirrorUrlJCenter}.")
                        this.url = URI(kAliMirrorUrlJCenter)
                    } else if (currentUrl.startsWith(kOfficialUrlGoogle)) {
                        project.logger.lifecycle("[INIT] Repository ${this.url} replaced by ${kAliMirrorUrlGoogle}.")
                        this.url = URI(kAliMirrorUrlGoogle)
                    } else if (currentUrl.startsWith(kOfficialUrlMaven) || currentUrl.startsWith(kOfficialUrlMaven2)) {
                        project.logger.lifecycle("[INIT] Repository ${this.url} replaced by ${kAliMirrorUrlMaven}.")
                        this.url = URI(kAliMirrorUrlMaven)
                    }
                }
            }
        }
    }
    repositories {
        maven {
            url = URI(kAliMirrorUrlPublic)
        }
        all {
            if (this is MavenArtifactRepository) {
                val currentUrl = this.url.toString()
                if (currentUrl.startsWith(kOfficialUrlJCenter)) {
                    project.logger.lifecycle("[INIT] Repository ${this.url} replaced by ${kAliMirrorUrlJCenter}.")
                    this.url = URI(kAliMirrorUrlJCenter)
                } else if (currentUrl.startsWith(kOfficialUrlGoogle)) {
                    project.logger.lifecycle("[INIT] Repository ${this.url} replaced by ${kAliMirrorUrlGoogle}.")
                    this.url = URI(kAliMirrorUrlGoogle)
                } else if (currentUrl.startsWith(kOfficialUrlMaven) || currentUrl.startsWith(kOfficialUrlMaven2)) {
                    project.logger.lifecycle("[INIT] Repository ${this.url} replaced by ${kAliMirrorUrlMaven}.")
                    this.url = URI(kAliMirrorUrlMaven)
                }
            }
        }
    }
}
