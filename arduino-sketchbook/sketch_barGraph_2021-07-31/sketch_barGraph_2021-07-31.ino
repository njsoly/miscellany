/**
 * Try out a few pins on the new 10-digit bar graph LEDs.
 * YSLB-102510B5-10
 * 
 * Hook 4 outputs to 4 different digits.
 * This will briefly flash 1 and 3, then 2 and 4 every half second.
 */

#define SEG0 8
#define SEG1 9
#define SEG2 10
#define SEG3 11

boolean flash = false;

void setup() {

  Serial.begin(19200);
  
  pinMode(SEG0, OUTPUT);
  pinMode(SEG1, OUTPUT);
  pinMode(SEG2, OUTPUT);
  pinMode(SEG3, OUTPUT);
}

void loop() {

  digitalWrite(flash ? SEG0 : SEG1, HIGH);
  digitalWrite(flash ? SEG2 : SEG3, HIGH);
  delay(20);
  allLow();
  
  flash = !flash;
  delay(500);
}

void allLow() {
  digitalWrite(SEG0, LOW);
  digitalWrite(SEG1, LOW);
  digitalWrite(SEG2, LOW);
  digitalWrite(SEG3, LOW);
}
