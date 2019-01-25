#!/bin/bash
run_this_timed () { 
    _command="$@";

    _start=$(date +%H:%M:%S.%N);
    _start=${_start:0: -6};
    _start_seconds=$(date +%s);
    _start_date=$(date +%F);
    _start_milliseconds=${_start_seconds}${_start: -3}
    _msg="starting \"${_command}\" (in path $PWD) at $_start $_start_date.";
    echo $_msg;
    echo $_msg >> ~/.time_ab.log

	# actual execution of the command
    ${_command}

    _finish=$(date +%H:%M:%S.%N);
    _finish=${_finish:0: -6};
    _finish_seconds=$(date +%s);
    _finish_date=$(date +%F);
    _finish_milliseconds=${_finish_seconds}${_finish: -3}
    _msg="finished \"${_command}\" (in path $PWD) at $_finish $_finish_date.";
    echo $_msg;
    echo $_msg >> ~/.time_ab.log;

    _elapsed=$[ $_finish_milliseconds - $_start_milliseconds ]
    _msg="command \"${_command}\" (in path $PWD) ran from $_start $_start_date to $_finish ($_elapsed ms)"
    echo $_msg;
    echo $_msg >> ~/.time_elapsed.log;
}
