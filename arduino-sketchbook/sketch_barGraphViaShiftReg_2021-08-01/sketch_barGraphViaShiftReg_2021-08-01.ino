/**
 * Try out a few pins on the new 10-digit bar graph LEDs.
 * YSLB-102510B5-10
 * 
 * Hookup: 
 * Ground connected to IC pins 1-10 via one 100-ohm resistor
 *
 * Note: The slightly-beveled corner of the bar graph IC highlights pin 1.
 */

#define DATA_LENGTH 10
#define ROTR 0
#define ROTL 1
#define DEMO_MAX ROTL

#include <Arduino.h>
#include <avr/pgmspace.h>

const boolean DEBUG = false;

// pins
// const int graphPins[DATA_LENGTH] = {2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
#define RCLK 2 // register storage clock
#define SRCLK 3 // shift register
#define SER 4 // serial data output pin
#define OUTPUT_DISABLE 5 // OE_not


uint8_t graphData[DATA_LENGTH];

// has the LED been flashed since the data has been changed
boolean flashNeeded = true;

// how many millis to wait between stepping the demo
const long demoPeriod = 60L;
// last millis() count when the demo data was stepped.
unsigned long demoLast = 0L;

// how often (in ms) to switch the demo style
const long demoSwitchPeriod = 3000L;
uint32_t demoSwitchLast = 0L;

uint8_t demoMode = ROTR;

void setup() {

  Serial.begin(38400);

  // pin and data setup
  pinMode(SRCLK, OUTPUT);
  pinMode(RCLK, OUTPUT);
  pinMode(SER, OUTPUT);
  pinMode(OUTPUT_DISABLE, OUTPUT);
  digitalWrite(SRCLK, LOW);
  digitalWrite(RCLK, LOW);
  digitalWrite(SER, LOW);
  digitalWrite(OUTPUT_DISABLE, HIGH);

  writeBarGraph(0x010, graphData, DATA_LENGTH);
  demoSwitchLast = millis();
}

void loop() {
  if (flashNeeded) {
    flashBarGraph();
  }

  if (demoLast + demoPeriod < millis()) {
    stepDemo();
    demoLast = millis();
  }

  if (demoSwitchLast + demoSwitchPeriod < millis()) {
    switchDemo();
    demoSwitchLast = millis();
  }

}

void shiftValue(int bit) {
    digitalWrite(OUTPUT_DISABLE, HIGH);
    digitalWrite(SER, bit);
    digitalWrite(SRCLK, LOW);
    digitalWrite(RCLK, LOW);
    digitalWrite(SRCLK, HIGH);
    digitalWrite(RCLK, HIGH);
    digitalWrite(OUTPUT_DISABLE, LOW);
}

void flashBarGraph() {
    for (int i = DATA_LENGTH - 1; i >= 0; i--){
        shiftValue(graphData[i] > 0 ? HIGH : LOW);
    }
    flashNeeded = false;
}

void flashBarGraphLow(int pinCount) {
  for (int i = 0; i < pinCount; i++) {
    shiftValue(LOW);
  }

  flashNeeded = false;
}

// write bit 0 of x to dataArray[0], bit 1 to dataArray[1], etc.
void writeBarGraph(int x, uint8_t dataArray[], int dataArrayLength) {
  for (int i = 0; i < dataArrayLength; i++){
    dataArray[i] = 0x1 & x;
    x = (unsigned int)x >> 1;
  }

  flashNeeded = true;
}

void switchDemo() {
  if (demoMode == DEMO_MAX) {
    demoMode = 0;
  } else {
    demoMode++;
  }
  // make sure the first step of the next demo
  // is triggered immediately.
  demoLast = 0;
}

void stepDemo() {
  switch (demoMode) {
    case ROTR: graphDataRotateRight(graphData, DATA_LENGTH); break;
    case ROTL: graphDataRotateLeft(graphData, DATA_LENGTH); break;
    default: break;
  }

  flashNeeded = true;
}

void graphDataRotateRight(uint8_t graphData[], int dataLength) {
  int temp = graphData[dataLength - 1];
  for (int i = dataLength - 1; i > 0; i--) {
    graphData[i] = graphData[i - 1];
  }

  graphData[0] = temp;
}

void graphDataRotateLeft(uint8_t graphData[], int dataLength) {
  int temp = graphData[0];
  for (int i = 0; i < dataLength - 1; i++) {
    graphData[i] = graphData[i + 1];
  }

  graphData[dataLength - 1] = temp;
}
