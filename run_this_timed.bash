run_this_timed () {
    _command="$@";

    _start=$(date +%H:%M:%S.%N);
    _start=${_start:0:12};
    _start_seconds=$(date +%s);
    _start_date=$(date +%F);
    _start_milliseconds=${_start_seconds}${_start: -3}
    _msg="starting \"${_command}\" (in $PWD) at $_start
$_start_date";
    echo $_msg;
    echo $_msg >> ~/.time_ab.log

    ${_command}
    _exit_status=$?

    _finish=$(date +%H:%M:%S.%N);
    _finish=${_finish:0:12};
    _finish_seconds=$(date +%s);
    _finish_date=$(date +%F);
    _finish_milliseconds=${_finish_seconds}${_finish: -3}
    _msg="finished \"${_command}\" (in $PWD) at $_finish
$_finish_date [exit code: ${_exit_status}]";
    echo $_msg;
    echo $_msg >> ~/.time_ab.log;

    _elapsed=$[ $_finish_milliseconds - $_start_milliseconds ]
    _msg="command \"${_command}\" [in $PWD] ran from $_start $_start_date to $_finish ($_elapsed ms) [exit code: ${_exit_status}]"
    echo $_msg;
    echo $_msg >> ~/.time_elapsed.log;
}
