<!DOCTYPE HTML>
<html>
<head>

	<title id=counterTitle>counter </title>
	<script src="scripts.js" type="text/javascript"></script>
	<meta charset="utf-8" />
	<meta http-equiv='Pragma' content='no-cache' />
	<link rel="stylesheet" type="text/css" href="boxy_dark.css" />
	

	<style>
	.topDiv {
		max-width: 99vw;
		max-height: 99vh;
		border: 1px dashed rgba(0,0,0,0.24);
		background-color: rgba(0,200,255,0.5);
	}
	#timerDiv {
		min-height: 10vh;
		font-size: 2.5em;
		text-align: center;
		padding-top: 2.24vh;
		background-color: #835;
		font-family: monospace;
	}
	button {
		font-size: 3em;
	}
	fieldset {
		background-color: rgba(0,48,20,0.4);
	}
	html {
		background-color: #292933;
		color: rgb(222,224,240);
	}
	</style>
</head>
<body>
	<main>
				
		<div class="topDiv">
			<div id="timerDiv" title="the timer.">
			</div>
			<div class=btns>
				<fieldset>
					<button onclick="toggleTimer();" id=timerToggle>
					stop
					</button>

					<button onclick="resetTimer();">reset</button>
				</fieldset>			
			</div>

		</div>
	</main>
	
	<script id=timerDivJsScript>
		timerCounter = 0;
		timerRunning = false;
		var x;
		incrementTimer = function (){
			document.getElementById("timerDiv").innerText = timerCounter + "s";
			timerCounter++;
		
		};
		resetTimer = function(){
			timerCounter = -1;
			/*if(timerRunning == true){
				stopTimer();
			}*/
			incrementTimer();
			incrementTimer();
		};
		function startTimer(){
			(x = setInterval(
				function(){
					incrementTimer();
				}, 1000
			));
			// this making too many timers
			timerRunning = true;
		};
		
		// this does not work.
		function stopTimer(){
			try{
			clearInterval(x);
			} catch(err){
				console.error("didn't stop timer.");
			}
			timerRunning = false;
		};
		function toggleTimer(){
			toggleButton = document.getElementById("timerToggle");
			if(timerRunning == true){
				stopTimer();
				toggleButton.value = "start";
			}
			else {
				startTimer();
				toggleButton.value = "stop";
			}
			return timerRunning;
		};
		
	</script>
	<script>
	startTimer();
	</script>
</body>
</html>
