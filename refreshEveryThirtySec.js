/* refreshEveryThirtySec.js
 *
 * This is code I wrote to automatically find the "Refresh" button in SalesForce, every 30 seconds.
 */

refreshButton = document.getElementsByClassName("btn refreshListButton")[0];

/* this appears to only work after using Developer Tools to select the refresh button. */
function refreshEveryThirtySec(f){
	if(typeof f == "undefined"){

		function fxn(){
			refreshButton = document.getElementsByClassName("btn refreshListButton")[0];
			if(typeof refreshButton == "undefined"){
				console.error("something went wrong finding the refresh button.");
				return;
			}
			console.info("clicking the refresh button.");
			
refreshButton.click();			
		};
	}
	else {
		fxn = f;
	}
	interval = 30000;
	
	var x = setInterval(fxn, interval);
	console.debug("set interval to refresh list every " + interval + " ms.");
	
	if(typeof alerts == "undefined" || alerts == true){ alert("set interval to refresh list every " + interval + " ms.");	}
};

refreshEveryThirtySec();