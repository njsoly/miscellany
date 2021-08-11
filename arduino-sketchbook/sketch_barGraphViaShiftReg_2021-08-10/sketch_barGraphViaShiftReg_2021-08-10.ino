/**
 * sketch_barGraphViaShiftReg_2021-08-10
 * 
 * SN74HC595 (x2) and YSLB-102510B5-10
 * 
 * Hookup: 
 * Ground connected to IC pins 1-10 via one 330-ohm resistor
 *  Note: The slightly-beveled corner of the bar graph IC highlights pin 1.
 * 
 * This sketch is to test rapid updating of the register, to figure out 
 * how to stamp out flicker.
 */

#include <Arduino.h>
#include <avr/pgmspace.h>
#include "ShiftRegHC595.h"

// pins
#define SER 2 // serial data output pin
#define RCLK 3 // register storage clock (latch)
#define SRCLK 4 // shift register

const boolean DEBUG = false;

ShiftRegHC595 shiftReg(
  SER,
  RCLK, 
  SRCLK,
  10,
  Serial
);

uint8_t barGraph[10]{1,0,1,0,0, 1,1,0,0,1};
int t = 500;
long lastUpdate = 0;
const int flashDelayMs = 200;

void setup() {

  Serial.begin(38400);
  shiftReg.init();

  shiftReg.shiftOut(barGraph, 10);
  Serial.println("finished setup");
  delay(500);
//  Serial.println("entering loop");
}

void loop() {
  if (millis() > lastUpdate + flashDelayMs) {
//    Serial.println("shifting");
    shiftReg.shiftOut(barGraph, 10);
    lastUpdate = millis();
  }
}
