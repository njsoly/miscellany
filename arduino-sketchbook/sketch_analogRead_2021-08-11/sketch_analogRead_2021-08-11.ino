/*
 * This started as a sketch meant for read a reed switch, but hey,
 * it'll read any digital input.  Basic.  Simple.  Stupid.
 * 
 * It'll print 1 or 0 every 50 milliseconds.
 * 
 * Tell how long the program has been running: millis().
 */

const int PIN_IN = A0;
const int GROUND = A1;
const long PRINT_PERIOD = 50;

long lastPrint = millis();

void setup() {
  pinMode(PIN_IN, INPUT);
  pinMode(GROUND, OUTPUT);
  digitalWrite(GROUND, LOW);
  Serial.begin(38400);
}

void loop() {

  if (millis() >= lastPrint + PRINT_PERIOD) {
    Serial.println(analogRead(PIN_IN));
    lastPrint = millis();
  }
}
