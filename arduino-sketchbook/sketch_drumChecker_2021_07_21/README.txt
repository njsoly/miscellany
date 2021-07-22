###########################    drumChecker_2021_07_21    #############################
Findings: 
	In order to talk to the drum controller, you need the following setup:
		* One wire connected to GND
		* The other wire connected to a GPIO pin
			* Default (open circuit) state should be pinMode=INPUT, digitalWrite=HIGH
			* Close the circuit by changing to pinMode=OUTPUT, digitalWrite=LOW
	It looks like you need 70 ms of signal to trigger the pedal's function.

