/*
 * This started as a sketch meant for read a reed switch, but hey,
 * it'll read any digital input.  Basic.  Simple.  Stupid.
 * 
 * It'll print 1 or 0 every 50 milliseconds.
 * 
 * Tell how long the program has been running: millis().
 */

const int DIGITAL_IN = 12;

void setup() {
  pinMode(DIGITAL_IN, INPUT);

  Serial.begin(19200);
}

void loop() {
  Serial.println(digitalRead(DIGITAL_IN));
  delay(50);
}
