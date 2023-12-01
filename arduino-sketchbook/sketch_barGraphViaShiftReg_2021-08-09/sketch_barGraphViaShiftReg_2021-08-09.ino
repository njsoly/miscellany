/**
 * sketch_barGraphViaShiftReg_2021-08-09
 * 
 * SN74HC595 (x2) and YSLB-102510B5-10
 * 
 * Hookup: 
 * Ground connected to IC pins 1-10 via one 100-ohm resistor
 *  Note: The slightly-beveled corner of the bar graph IC highlights pin 1.
 * 
 * This sketch takes sketch_barGraphViaShiftReg_2021-08-08 and switches it 
 * over to my shift register library.
 */

#include <Arduino.h>
#include <avr/pgmspace.h>
#include <ShiftRegHC595.h>

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


int t = 500;

void setup() {

  Serial.begin(38400);
  shiftReg.init();

  Serial.println("finished setup");
}

void loop() {
    shiftReg.shiftOut(1);
    delay(t);
    shiftReg.shiftOut(0);
    delay(t);
    shiftReg.shiftOut(1);
    delay(t);
    shiftReg.shiftOut(1);
    delay(t);
    shiftReg.shiftOut(0);
    delay(t);
    shiftReg.shiftOut(0);
    delay(t);
}
