/**
 * sketch_barGraphViaShiftReg_2021-11-13
 * 
 * SN74HC595 (x9) and YSLB-102510B5-10 (Blue) (2), Green (2), Red (2), Yellow(1).
 * 
 * This sketch is to test the full circuit that will be my graphic equalizer.
 * It sends out 2-on, 2-off, repeating, in order to check out a daisy chain of shift registers.
 * 
 * Demo available at https://www.dropbox.com/s/cdx5om1nafhmon0/sketch_barGraphViaShiftReg_2021-08-13.demo.mp4?dl=0
 * 
 * This is an exact copy of sketch_barGraphViaShiftReg_2021-08-13, but with the addition of pins for OE_not and SRCLR_not,
 * to eliminate the need to attach these to VCC and GND with breadboard.
 */

#include <Arduino.h>
#include <avr/pgmspace.h>
#include "ShiftRegHC595.h"

// pins
#define SER 2 // serial data output pin
#define RCLK 6 // register storage clock (latch)
#define SRCLK 7 // shift register

#define OE_NOT 10 // output disable
#define SRCLR_NOT 11 // serial clear, active-low

const boolean DEBUG = false;

ShiftRegHC595 shiftReg(
  SER,
  RCLK, 
  SRCLK,
  70, // length
  Serial
);

long lastUpdate = 0;
const int flashDelayMs = 100;

void setup() {

  Serial.begin(38400);
  shiftReg.init();
  pinMode(OE_NOT, OUTPUT);
  pinMode(SRCLR_NOT, OUTPUT);
  digitalWrite(OE_NOT, LOW);
  digitalWrite(SRCLR_NOT, HIGH);

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
