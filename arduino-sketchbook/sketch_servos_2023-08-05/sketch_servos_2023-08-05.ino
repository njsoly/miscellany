/*
 Controlling a servo position using a potentiometer (variable resistor)
 by Michal Rinott <http://people.interaction-ivrea.it/m.rinott>

 modified on 8 Nov 2013
 by Scott Fitzgerald
 modified on 5 Aug 2023
 by Nate Solyntjes
 http://www.arduino.cc/en/Tutorial/Knob

 2023-08-05: Modified sketch to debug potentiometer reading to serial output.
*/

#include <Servo.h>

Servo myservo;  // create servo object to control a servo

int potpin = A0;  // analog pin used to connect the potentiometer (10K)
int val;    // variable to read the value from the analog pin

long lastSerial = 0;
const long SERIAL_INTERVAL = 1000;

void setup() {
  myservo.attach(9);  // attaches the servo on pin 9 to the servo object
  Serial.begin(19200);
}

void loop() {
  val = analogRead(potpin);            // reads the value of the potentiometer (value between 0 and 1023)
  
  writeValue(val);
  val = map(val, 0, 1023, 0, 180);     // scale it for use with the servo (value between 0 and 180)
  myservo.write(val);                  // sets the servo position according to the scaled value
  delay(15);                           // waits for the servo to get there
}

void writeValue(int value){
  if (millis() > lastSerial + SERIAL_INTERVAL){
    Serial.write("input value: ");
    Serial.println(val);
    
    lastSerial = millis();
  }

}