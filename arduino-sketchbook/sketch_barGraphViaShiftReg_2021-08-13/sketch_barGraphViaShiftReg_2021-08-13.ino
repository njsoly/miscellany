/**
 * sketch_barGraphViaShiftReg_2021-08-13
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
#define RCLK 6 // register storage clock (latch)
#define SRCLK 7 // shift register

const boolean DEBUG = false;

ShiftRegHC595 shiftReg(
  SER,
  RCLK, 
  SRCLK,
  70, // length
  Serial
);

uint8_t barGraph[10]{1,0,1,0,0, 1,1,0,0,1};
int t = 500;
long lastUpdate = 0;
const int flashDelayMs = 100;

void setup() {

  Serial.begin(38400);
  shiftReg.init();

  shiftReg.shiftOut(barGraph, 10);
  Serial.println("finished setup");
  delay(500);
  Serial.println("entering loop");
}
void loop() {

  doubleStripeDemo(120); 

}

void doubleStripeDemo(int steps) {
  int step = 0;
    
  for (int i = 0; i < steps; i++) {
    
    if(step == 0 || step == 1){
      shiftReg.shiftOut(1);
    } else {
      shiftReg.shiftOut(0);
    }
    step++;
    if (step == 4) {
      step = 0;
    }
    delay(flashDelayMs);
  }   
}
