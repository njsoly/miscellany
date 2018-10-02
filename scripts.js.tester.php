<?php
	ini_set("display_errors","0");

	ini_set("log_errors_max_len", 0);
	ini_set("display_startup_errors", (1));

	error_reporting (E_ALL & ~E_STRICT & ~E_DEPRECATED);

	ini_set("log_errors","On");
	ini_set("error_log", ".error_log");

	/* put last error msg. into $php_errormsg.*/
	ini_set("track_errors", "on");	

	/* ignore same errors from same line in */
	ini_set("ignore_repeated_errors","off");
	$x =8;
	$x = $y;
?>
<!DOCTYPE HTML>
<html>
<head>
	<title>:: scripts.js tester ::</title>
	<script	src="https://code.jquery.com/jquery-2.2.4.min.js"	integrity="sha256-BbhdlvQf/xTY9gja0Dq3HiwQF8LaCRTXxZKRutelT44="	crossorigin="anonymous"></script>
	<script src="scripts.js" type="text/javascript"></script>
</head>
<body title="" >
	<div>	<h1>scripts.js tester</h1>
		<a href=".error_log">errors log</a>
	</div>
	<div id="isJQueryLoaded" >
		
	</div>
	
</body>
</html>
