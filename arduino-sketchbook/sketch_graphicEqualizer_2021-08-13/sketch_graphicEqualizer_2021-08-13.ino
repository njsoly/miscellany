#include <ShiftRegHC595.h>

/******************************************************************************
  Code adapted from:
  "SparkFun Spectrum Shield Demo"
  Wes Furuya @ SparkFun Electronics - January 2020
  https://github.com/sparkfun/Spectrum_Shield

*********************************************************************
  sketch_graphicEqualizer_2021-08-13
  by Nate Solyntjes
  2021-08-13

  It works!  Sort of.  I can tell I need to find a way to discern the volume being played, 
  and adjust the threshold accordingly.

  
*********************************************************************************/

// spectrum shield constants
#define STROBE 4
#define RESET 5
#define DC_One A0
#define DC_Two A1

// spectrum shield variables
int freq_amp;
int frequenciesLeft[7];
int frequenciesRight[7];
int i;

// shift register constants
#define SER 2
#define RCLK 6
#define SRCLK 7
#define LEDS_COUNT 70
#define SHIFT_REG_MIN_DELAY 2

// shift register data
ShiftRegHC595 shiftReg(SER, RCLK, SRCLK, LEDS_COUNT);
uint8_t shiftRegData[LEDS_COUNT];
long shiftRegLastUpdate = 0;

// constants
#define AVERAGE 0
#define MAX 1

// variables
int band;
//int baseline_values[]{72,  54,  53,  59,  53,  53,  60};
int baseline_values[]{81,  62,  61,  67,  60,  61,  69};

int calibrationMode = AVERAGE;

/********************Setup Loop*************************/
void setup() {
  //Set spectrum Shield pin configurations
  pinMode(STROBE, OUTPUT);
  pinMode(RESET, OUTPUT);
  pinMode(DC_One, INPUT);
  pinMode(DC_Two, INPUT);
  shiftReg.init();

  //Initialize Spectrum Analyzers
  digitalWrite(STROBE, LOW);
  digitalWrite(RESET, LOW);
  delay(5);

  Serial.begin(38400);
  delay(50);
}


/**************************Main Function Loop*****************************/
void loop() {
  readFrequencies();
  graphFrequencies();

  shiftFrequencies();
}

/*************** ***************/
void shiftFrequencies() {

  if (shiftRegLastUpdate + SHIFT_REG_MIN_DELAY <= millis()) {

    for (band = 0; band < 7; band++){
      int bandAvgRaw = ((frequenciesLeft[band] + frequenciesRight[band]) / 2);
      int bandValueMapped = map(bandAvgRaw, baseline_values[band], baseline_values[band] + 200, -1, 9);
      for (int led = 0; led < 10; led++) {
        if (bandValueMapped >= led) {
          shiftRegData[band * 10 + led] = 1;
        } else {
          shiftRegData[band * 10 + led] = 0;
        }
      }
    }
    shiftReg.shiftOut(shiftRegData, LEDS_COUNT);

    shiftRegLastUpdate = millis();
  }
  
}

/*******************Pull frquencies from Spectrum Shield********************/
void readFrequencies() {
  digitalWrite(RESET, HIGH);
  delayMicroseconds(200);
  digitalWrite(RESET, LOW);
  delayMicroseconds(200);

  //Read frequencies for each band
  for (freq_amp = 0; freq_amp < 7; freq_amp++)
  {
    digitalWrite(STROBE, HIGH);
    delayMicroseconds(50);
    digitalWrite(STROBE, LOW);
    delayMicroseconds(50);

    frequenciesLeft[freq_amp] = analogRead(DC_One);
    frequenciesRight[freq_amp] = analogRead(DC_Two);
  }
}

/*****************Print Out Band Values for Serial Plotter*****************/
void graphFrequencies() {
  for (i = 0; i < 7; i++)
  {
    Serial.print( (frequenciesLeft[i] + frequenciesRight[i]) / 2 );
    Serial.print("    ");
  }
  Serial.println();
}
