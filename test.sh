basedir=$(pwd)
cd $basedir/common
sbt test publish-local

cd $basedir/application
sbt test run