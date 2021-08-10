/**
 * Try out a few pins on the new 10-digit bar graph LEDs.
 * YSLB-102510B5-10
 * 
 * Hookup: 
 * Ground connected to IC pins 1-10 via one 100-ohm resistor
 *
 * Note: The slightly-beveled corner of the bar graph IC highlights pin 1.
 * 
 * This is a dumbed-down sketch from recent similar ones, because something 
 * seemed horribly off with my circuits.  Then I found out that my 830-tie breadboard's
 * power and ground rails disconnected halfway through.  My (only) previous one didn't.
 * 
 * Anyways, this is a very simple shift register tester, where if it doesn't work,
 * you've done something wrong.
 */

#include <Arduino.h>
#include <avr/pgmspace.h>

const boolean DEBUG = false;

// pins
#define SER 2 // serial data output pin
#define RCLK 3 // register storage clock
#define SRCLK 4 // shift register

int t = 500;

void setup() {

  Serial.begin(38400);

  // pin and data setup
  pinMode(SRCLK, OUTPUT);
  pinMode(RCLK, OUTPUT);
  pinMode(SER, OUTPUT);
  digitalWrite(SRCLK, LOW);
  digitalWrite(RCLK, LOW);
  digitalWrite(SER, LOW);

  Serial.println("finished setup");
}

void loop() {
    shiftValue(1);
    delay(t);
    shiftValue(0);
    delay(t);
    shiftValue(1);
    delay(t);
    shiftValue(1);
    delay(t);
    shiftValue(0);
    delay(t);
    shiftValue(0);
    delay(t);
}

void shiftValue(int bit) {
    digitalWrite(SRCLK, HIGH);
    digitalWrite(SRCLK, LOW);
    digitalWrite(SER, bit);
    digitalWrite(RCLK, HIGH);
    digitalWrite(RCLK, LOW);
}
