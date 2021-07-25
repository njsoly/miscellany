/*
 * Sketch to mess around with the piezo element inside a Rock Band drum pad.
 * 
 * Used the "Knock" arduino IDE example as a jumping-off point.  Also, I used 
 * a 100K-ohm resistor rather than a megohm one, as I didn't currently have 
 * anything that high.
 */

/*
  Knock Sensor

  This sketch reads a piezo element to detect a knocking sound.
  It reads an analog pin and compares the result to a set threshold.
  If the result is greater than the threshold, it writes "knock" to the serial
  port, and toggles the LED on pin 13.

  The circuit:
  - positive connection of the piezo attached to analog in 0
  - negative connection of the piezo attached to ground
  - 1 megohm resistor attached from analog in 0 to ground

  created 25 Mar 2007
  by David Cuartielles <http://www.0j0.org>
  modified 30 Aug 2011
  by Tom Igoe

  This example code is in the public domain.

  https://www.arduino.cc/en/Tutorial/BuiltInExamples/Knock
*/


// these constants won't change:
const int ledPin = 13;      // LED connected to digital pin 13
const int knockSensor = A0; // the piezo is connected to analog pin 0
const int threshold = 300;  // threshold value to decide when the detected sound is a knock or not


// these variables will change:
int sensorReading = 0;      // variable to store the value read from the sensor pin
int ledState = LOW;         // variable used to store the last LED status, to toggle the light

// mode for threshold "Knock!" versus constant value sendback for serial plotter
const boolean KNOCK = false;

void setup() {
  pinMode(ledPin, OUTPUT); // declare the ledPin as as OUTPUT
  pinMode(knockSensor, INPUT);
//  digitalWrite(knockSensor, LOW);
  
  Serial.begin(9600);       // use the serial port
}

void loop() {
  // read the sensor and store it in the variable sensorReading:
  sensorReading = analogRead(knockSensor);

  if(KNOCK) {
    doKnockCheck();  
  } else {
    Serial.println(sensorReading);
  }
  delay(83);  // delay to avoid overloading the serial port buffer
}

void doKnockCheck() {
// if the sensor reading is greater than the threshold:
  if (sensorReading >= threshold) {
    // toggle the status of the ledPin:
    ledState = !ledState;
    // update the LED pin itself:
    digitalWrite(ledPin, ledState);
    // send the string "Knock!" back to the computer, followed by newline
    Serial.println("Knock!");
  }

  
}
