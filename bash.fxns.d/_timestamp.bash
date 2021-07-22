#!/bin/bash
__time_with_milliseconds () {
	_ts_time_full="$(date +%H:%M:%S.%N)";
	_ts_time=${_ts_time_full:0:12}
	echo $_ts_time
}
__seconds_since_epoch () {
	date +%s
}
__date_yyyy-mm-dd () {
	date +%F
}
__timestamp ()
{
	__timestamp_loggable
}
__timestamp_loggable ()
{
	echo "$(__date_yyyy-mm-dd) $(__time_with_milliseconds)"
}
