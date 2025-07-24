/* FrazzlerEspNow.h - Arduino Library for general util functions.
 * by Nate Solyntjes 
 * 2025-06-01
 *
 */
#ifndef FrazzlerEspNow_h
#define FrazzlerEspNow_h

#include "Arduino.h"
#include "Stream.h"
#include <WString.h>
#include <HardwareSerial.h>
#include <esp_now.h>

#define SERIAL_BAUD 115200



/********** Commands Remote to Frazzler ***********
    Are you alive
    Get battery voltage
    Get hall sensor status
    Enable / disable motors
    Set Random On
    Extend random program timeout
    Set Random Speed
    Set exact speed of a single servo
    Set max servo speeds
 **********************************/

/********** Commands Frazzler to Remote **********
    Reed switch hit
	Report hall sensors
    Report battery voltage
    
    
 */

#define FRAZZLER_DEF_CMD_Q_SZ 8
#define FRAZZLER_DEF_CMD_SZ 32

/** Structure to send data over ESP-NOW */
typedef struct struct_message {
    uint16_t count = 0;
    char msg[FRAZZLER_DEF_CMD_SZ] = "";
} struct_message;


class FrazzlerComms {
	public:
		FrazzlerComms();
		bool isQueueFull();
		bool isQueueEmpty();
		bool pushCommand(struct_message newElement);
		struct_message popCommand();
	private:
		struct_message commandQueue[FRAZZLER_DEF_CMD_Q_SZ];
		uint8_t queueStart = 0;
		uint8_t queueVacancy = 0;
		uint8_t queueSize = 0;
		uint8_t queueCapacity = FRAZZLER_DEF_CMD_Q_SZ;
};

#endif /* FrazzlerEspNow_h */
