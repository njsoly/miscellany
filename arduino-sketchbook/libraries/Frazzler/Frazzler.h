/* Frazzler.h - Arduino Library for general util functions.
 * by Nate Solyntjes 
 * 2025-04-25
 *
 */
#ifndef Frazzler_h
#define Frazzler_h

#include "Arduino.h"
#include "Stream.h"
#include <WString.h>
#include <HardwareSerial.h>

#define SERVO_SPINNER 8
#define SERVO_FLICKER 9

#define BT_RX D0
#define BT_TX D1
// OR RX/TX for bluetooth might be on pins 0 and 1
#define BT_STATE_PIN 13
#define BT_BAUD 38400
#define BT_UART_CONFIG SERIAL_8N1

#define LIMITS_HALL
//#define LIMITS_REED

#ifdef LIMITS_REED
// Reed switch to limit flicker servo motion in the negative direction
#define REED_SWITCH_NEG 6
// Reed switch to limit flicker servo motion in the positive direction
#define REED_SWITCH_POS 7
#endif

#ifdef LIMITS_HALL
// Hall sensor 49E to limit flicker servo motion in the negative direction
#define HALL_NEG A2
// Hall sensor 49E to limit flicker servo motion in the positive direction
#define HALL_POS A3
#endif

#define OLED_I2C_ADDR 0x3C
#define OLED_SCL A4
#define OLED_SDA A5
//#define SSD1306_128_64


/********** Commands Remote to Frazzler ***********
    Are you alive
    Get battery voltage
    Get reed switch status
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
 

#endif /* Frazzler_h */
