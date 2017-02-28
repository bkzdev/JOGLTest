◆構成
lib
├─<dir>jogamp-all-platforms
├─gluegen-javadoc.7z
└─jogl-javadoc.7z

◆JOGL
・jogamp-all-platforms
http://jogamp.org/
のBuilds/Downloadsのzipにある7zを解凍して置く

・gluegen-javadoc.7z
・jogl-javadoc.7z
http://jogamp.org/
のBuilds/Downloadsのzipにある7zを解凍せずに置く

◆ユーザーライブラリのインポート
Eclipseのウィンドウ→設定のJava→ビルドパス→ユーザーライブラリーのインポートで
export.userlibrariesをインポートする

※インポートできない場合は以下のライブラリを作る
GlueGen
gluegen-rt.jar

JOGL
jogl-all.jar
jogl-all-noawt.jar
