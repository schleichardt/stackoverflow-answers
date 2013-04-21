## Question

* http://stackoverflow.com/questions/16110908/how-to-add-authbind-to-my-play-framework-start-script-generated-with-play-stage
* In short: how to override target/start script from `play stage`
* Play 2.1.0
* SBT 0.12.1

## Solution

```
play-2.1.0/play new so16110908
#enter
#1
cd so16110908
#edit project/Build.scala
#edit project/build.properties (SBT version to 0.12.1)
play-2.1.0/play stage
cat target/start
```