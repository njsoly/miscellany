_timestamp () 
{ 
_ts_time="$(date +%H:%M:%S.%N)";
_ts_seconds=$(date +%s)
_ts_time=${_ts_time:0:12}
_ts_date="$(date +%F)"
_ts="$_ts_time $_ts_date"
echo "$_ts"
}
