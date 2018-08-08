/*	main.cpp	*/

#include <MKL25Z4.H>
#include "../misc_defs.h"
#include "Lcd6100.cpp"


int main(void){
	

	// enable clock to RGB LED ports
	ENABLE_CLOCK_TO_PORT(B);
	ENABLE_CLOCK_TO_PORT(D);
	// enable RGB-LED pins as GPIO
	ENABLE_GPIO(B,18);	// 
	ENABLE_GPIO(B,19);	// 
	ENABLE_GPIO(D,1);	// 
	
	// enable clock to lcd ports
	ENABLE_CLOCK_TO_PORT(A);
	ENABLE_CLOCK_TO_PORT(D);
	// enable LCD pins as GPIO
	ENABLE_GPIO(A,13);	//
	ENABLE_GPIO(D,5);	//
	ENABLE_GPIO(D,2);	//
	ENABLE_GPIO(D,1);	// 

	
	// enable LCD shield buttons S1-S3 as GPIO
	ENABLE_GPIO(A,12);	// button S1
	ENABLE_GPIO(A,4);	// button S2
	ENABLE_GPIO(A,5);	// button S3
	
	PTA->PDDR &= ~(MASK(12) | MASK(4) | MASK(5));
	PTA->PSOR = (MASK(12)|MASK(4)|MASK(5));	
	
	PTA->PDDR |= (MASK(13));
	PTD->PDDR |= (MASK(1) | MASK(2) | MASK(5));

	Lcd6100 lcd;
	lcd.init(0);
	lcd.clear(WHITE);
	lcd.clear(BLACK);
	
	PTB->PDDR |= (MASK(18) | MASK(19));
	PTB->PSOR = (MASK(RED_LED_POS)|MASK(GREEN_LED_POS));
	
	
	io& rightSensor = PTD3, leftSensor = PTD0, 
		rightSensor_VCC = ARD_A4, leftSensor_VCC = ARD_A5,
		EN12 = ARD_D2, EN34 = ARD_A0,
		A1 = ARD_D6, A2 = ARD_D7, A3 = ARD_A2, A4 = ARD_A1;
	io all[]={rightSensor , leftSensor , 
		rightSensor_VCC , leftSensor_VCC ,
		EN12 , EN34 ,
		A1 , A2 , A3, A4};
	
	for(int i = 0; i < 8; i++){
		all[i].enableGPIO();
		//all[i].enableClockToPort();
	}

	EN12.write(0);
	EN34.write(0);

	A1.write(0);
	A2.write(0);
	A3.write(0);
	A4.write(0);
	
	volatile uint32_t _loop_ct_ = 0UL;
	while(1){
		EN12.write(1);
		A1.write(1);
		
		redSet(1);
		greenSet(0);
		blueSet(1);
		
		redSet(0);
		greenSet(1);
		blueSet(0);
		
		lcd.setStr((char *)"Hello.",10,16,BLUE,WHITE);
		
		int col; rect r;
		col = (!S1.read()) ? GREEN : pickColor(0x2,0x2,0x2);	r = s1;
		lcd.setRect(r.x0,r.y0,r.x1,r.y1,1,col);
		col = (!S2.read()) ? GREEN : pickColor(0x2,0x2,0x2);	
		r = s2;
		lcd.setRect(r.x0,r.y0,r.x1,r.y1,1,col);
		col = (!S3.read()) ? GREEN : pickColor(0x2,0x2,0x2);	r = s3;
		lcd.setRect(r.x0,r.y0,r.x1,r.y1,1,col);

		delay(500);
		_loop_ct_++;
	}
	
}

