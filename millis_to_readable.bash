m=$[$x / 60 / 1000]; s=$[x / 1000 - ($m * 60)]; ms=$[$x % 1000]; echo "$x millis: ${m}m, ${s}.${ms}s"
