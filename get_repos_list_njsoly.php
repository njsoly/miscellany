<?php
	$repos_url="https://github.com/njsoly?tab=repositories";
	$curl_call="curl ";
	// parameters?
	$curl_call .= "--"; 
	$curl_call .= $repos_url;
	$ret = shell_exec("curl(".$repos_url.")");
	
?>
