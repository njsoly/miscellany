/**    
 *        ****    2016-10-27    ****          
 * How about some code for the 3x3x3 LED cube.
 * Using duemilanove initially.
 * 
 */

#include <Arduino.h>
#include <new.h>
#include <avr/pgmspace.h>

//#include "DuemilanovePins.h"
//#include "LedCube333.h"
#define ROW1 A5
#define ROW2 A4
#define ROW3 A3

#define COL11 3
#define COL12 2
#define COL13 4
#define COL21 5
#define COL22 6
#define COL23 7
#define COL31 8
#define COL32 10
#define COL33 11

uint8_t toggle = LOW;
uint8_t ROWS[]={ROW1, ROW2, ROW3};
uint8_t COLS[][3]={{COL11,COL12,COL13},{COL21,COL22,COL23},{COL31,COL32,COL33}};

void setup() {
	for(int i = 0; i < 3; i++){
		pinMode(ROWS[i], OUTPUT);
		digitalWrite(ROWS[i], HIGH);

		for(int j = 0; j < 3; j++){
			pinMode(COLS[i][j], OUTPUT);
			digitalWrite(COLS[i][j], HIGH);
		}
	}
	
	Serial.begin(19200);
	Serial.println(F("done with setup()."));
}
uint8_t loopCount =  0;
void loop() {
	toggle = !toggle;
	Serial.println("loopCount = " + String(loopCount));
	iterate27(200);

	
}

void iterate27(uint8_t ms){
	for(uint8_t row = 0; row < 3; row++){
		for(uint8_t colx = 0; colx < 3; colx++){
			for(uint8_t coly = 0; coly < 3; coly++){
				setRow(row, LOW);
				setCol(colx, coly, HIGH);
				delay(ms);
				setRow(row, HIGH);
				setCol(colx, coly, LOW);
			}
		}
	}
}

void setCol(uint8_t colx, uint8_t coly, uint8_t level){
	digitalWrite(COLS[colx][coly], level);
}
void setRow(uint8_t row, uint8_t level){
	digitalWrite(ROWS[row], level);
}

void setAllRows(uint8_t level){
	digitalWrite(ROW1, level);
	digitalWrite(ROW2, level);
	digitalWrite(ROW3, level);
}
