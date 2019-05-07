#!/bin/bash
run_this_timed () {
    _command="$@";

    _start=$(date +%H:%M:%S.%N);
    _start=${_start:0:12};
    _start_seconds=$(date +%s);
    _start_date=$(date +%F);
    _start_milliseconds=${_start_seconds}${_start:0,-3}
    _msg="starting \"${_command}\" at $_start
$_start_date ($_start_milliseconds)";
    echo $_msg;
    echo $_msg >> ~/.time_ab.log

    ${_command}

    _finish=$(date +%H:%M:%S.%N);
    _finish=${_finish:0:12};
    _finish_seconds=$(date +%s);
    _finish_date=$(date +%F);
    _finish_milliseconds=${_finish_seconds}${_finish:0,-3}
    _msg="finished \"${_command}\" at $_finish
$_finish_date ($_finish_milliseconds)";
    echo $_msg;
    echo $_msg >> ~/.time_ab.log;

    _elapsed=$[ $_finish_milliseconds - $_start_milliseconds ]
    _msg="command \"${_command}\" ran from $_start to $_finish ($_elapsed ms)"
    echo $_msg;
    echo $_msg >> ~/.time_elapsed.log;
}
