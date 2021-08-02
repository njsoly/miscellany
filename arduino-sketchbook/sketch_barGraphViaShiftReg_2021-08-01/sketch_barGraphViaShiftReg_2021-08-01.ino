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

#include <Arduino.h>

const boolean DEBUG = false; 

// pins
// const int graphPins[DATA_LENGTH] = {2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
const int RCLK = 2; // register storage clock
const int SRCLK = 3; // shift register 
const int SER = 4; // serial data output pin


int graphData[DATA_LENGTH];

// last millis() count when the bar graph was pulsed / lit
long flashLast = 0L;
// how many millis to wait between flashing the bar graph
long flashPeriod = 2L;
// last millis() count when the demo data was stepped.
long demoLast = 0L;
// how many millis to wait between stepping the demo
long demoPeriod = 100L;

void setup() {

  Serial.begin(38400);

  // pin and data setup
  pinMode(SRCLK, OUTPUT);
  pinMode(RCLK, OUTPUT);
  pinMode(SER, OUTPUT);
  digitalWrite(SRCLK, LOW);
  digitalWrite(RCLK, LOW);
  digitalWrite(SER, LOW);

  writeBarGraph(0x010, graphData, DATA_LENGTH);
  
  flashLast = millis();
}

void loop() {
  if (flashLast + flashPeriod < millis()) {
    flashBarGraph();
    flashLast = millis();
  }

  if (demoLast + demoPeriod < millis()) {
    stepDemo();
    demoLast = millis();
  }

}

void shiftValue(int bit) {
    digitalWrite(SER, bit);
    digitalWrite(SRCLK, LOW);
    digitalWrite(RCLK, HIGH);
    digitalWrite(SRCLK, HIGH);
    digitalWrite(RCLK, LOW);
}

void flashBarGraph() {
    for (int i = DATA_LENGTH - 1; i >= 0; i--){
        shiftValue(graphData[i] > 0 ? HIGH : LOW);
    }

  delayMicroseconds(20);
  // allPinsLow(DATA_LENGTH);
  // delayMicroseconds(200);
}

// write bit 0 of x to dataArray[0], bit 1 to dataArray[1], etc.
void writeBarGraph(int x, int dataArray[], int dataArrayLength) {
  for (int i = 0; i < dataArrayLength; i++){
    dataArray[i] = 0x1 & x;
    x = (unsigned int)x >> 1;
  }
}

void stepDemo() {
  graphDataRotateRight(graphData, DATA_LENGTH);
}

void graphDataRotateRight(int graphData[], int dataLength) {
  int temp = graphData[dataLength - 1];
  for (int i = dataLength - 1; i > 0; i--) {
    graphData[i] = graphData[i - 1];
  }

  graphData[0] = temp;
}

void allPinsLow(int pinCount) {
  for (int i = 0; i < pinCount; i++) {
    shiftValue(LOW);
  }
}
