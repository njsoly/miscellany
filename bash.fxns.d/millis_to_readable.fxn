#!/usr/bin/env bash
[[ $(type "millis_to_readable" 2> /dev/null) ]] || millis_to_readable () {
	DEBUG=0
	x=$1
	[[ -n "$x" ]] || ( echo "must pass a parameter"; exit 1 )
	m=$[$x / 60 / 1000]
	s=$[x / 1000 - ($m * 60)]
	ms=$[$x % 1000]
#	echo "$x millis: ${m}m, ${s}.${ms}s"
	[[ ! $m == 0 ]] && (
		echo "${m}m ${s}.${ms}s"
	) || (
		echo "${s}.${ms}s"
	)
}
