<?php
	/****************    This is a pile of document-starter PHP bits that I keep losing track of.    ***************/

	/* This little bit will source your favorite header PHP */
	$inc_file = '/php/standard-includes.php');
	include($_SERVER['DOCUMENT_ROOT'] . $inc_file);
?>
<?php 
	/* this lets the errors come through, so you can figure out what the hell has gone wrong.*/
	display_errors("1");           		
	ini_set("display_errors","On");		/* this is the inline equiv. of setting display_errors=1 in php.ini (?) */
	log_errors_max_len(0);         		/* this takes off the limit on log error messages (?) */
	
?>
