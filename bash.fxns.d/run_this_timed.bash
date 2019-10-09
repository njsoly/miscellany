#!/bin/bash

# note: this doesn't work with the standard MacOS bash.
# you need bash-extras or something.
millis_to_readable () {
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

run_this_timed () {
	local start_stop_logfile="$HOME/.time_ab.log"
	local time_elapsed_logfile="$HOME/.time_elapsed.log"
	local debug_logfile="$HOME/.run_this_timed_debug"
	local DEBUG=0
    _command="$@";

    _start=$(date +%H:%M:%S.%N);
    _start=${_start:0:12};
    _start_seconds=$(date +%s);
    _start_date=$(date +%F);
    _start_milliseconds=${_start_seconds}${_start: -3}
    _msg="starting \"${_command}\" (in $PWD) at $_start $_start_date";

    [[ ! "$DEBUG" = 0 ]] && echo "_start_seconds: $_start_seconds" >> ${debug_logfile}
    [[ ! "$DEBUG" = 0 ]] && echo "_start_milliseconds: $_start_milliseconds" >> ${debug_logfile}

    echo ${_msg};
    echo ${_msg} >> ${start_stop_logfile}

    ${_command}
    _exit_status=$?

    _finish=$(date +%H:%M:%S.%N);
    _finish=${_finish:0:12};
    _finish_seconds=$(date +%s);
    _finish_date=$(date +%F);
    _finish_milliseconds=${_finish_seconds}${_finish: -3}
    _msg="finished \"${_command}\" (in $PWD) at ${_finish} ${_finish_date} [exit code: ${_exit_status}]";
    echo ${_msg};
    echo ${_msg} >> ${start_stop_logfile};

    _elapsed=$[ ${_finish_milliseconds} - $_start_milliseconds ]
    _elapsed_readable=$(millis_to_readable $_elapsed)
    _msg="command \"${_command}\" [in $PWD] ran from $_start $_start_date to ${_finish} ($_elapsed_readable) [exit code: ${_exit_status}]"
    echo ${_msg};
    echo ${_msg} >> ${start_stop_logfile};
    return ${_exit_status};
}
