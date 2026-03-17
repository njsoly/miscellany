/* FrazzlerEspNow.cpp - Arduino Library for general util functions.
 * by Nate Solyntjes 
 * 2025-06-01
 */

#include "Arduino.h"
#include "Stream.h"
#include "FrazzlerEspNow.h"


FrazzlerComms::FrazzlerComms() {}


bool FrazzlerComms::isQueueFull() {
	return queueSize >= queueCapacity;
}
bool FrazzlerComms::isQueueEmpty() {
	return queueSize == 0;
}

bool FrazzlerComms::pushCommand(struct_message &newElement) {
	if (isQueueFull()) {
		return false;
	}
	
	memcpy(&(commandQueue[queueVacancy]), &newElement, FRAZZLER_DEF_CMD_SZ);
	queueVacancy = (queueVacancy + 1 < queueCapacity) ? queueVacancy + 1 : 0;
	queueSize++;
	return true;
}

struct_message FrazzlerComms::popCommand() {
	
}

