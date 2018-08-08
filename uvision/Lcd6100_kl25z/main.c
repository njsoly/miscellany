/*	main.cpp	*/

#include <MKL25Z4.H>
#include "misc_defs.h"
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
	//lcd.clear(WHITE);
	lcd.clear(BLACK);
	
	PTB->PDDR |= (MASK(18) | MASK(19));
	PTB->PSOR = (MASK(RED_LED_POS)|MASK(GREEN_LED_POS));

	/* motor controller stuff. */
	ENABLE_CLOCK_TO_PORT(C);
	ENABLE_GPIO(D,4);	// en12
	ENABLE_GPIO(C,8);	// a1
	ENABLE_GPIO(C,9);	// a2
	ENABLE_GPIO(B,0);	// en34
	ENABLE_GPIO(B,2);	// a3
	ENABLE_GPIO(B,1);	// a4
	PTD->PDDR |= MASK(4);
	PTC->PDDR |= MASK(8);
	PTC->PDDR |= MASK(9);
	PTB->PDDR |= MASK(0);
	PTB->PDDR |= (MASK(2)|MASK(1));
	
	PTB->PDOR |= MASK(0);
	PTB->PDOR &= ~MASK(2);
	PTB->PDOR |= MASK(1);
	
	io EN12(PTD,4);
	io A1(PTC,8);
	io A2(PTC,9);
	A1.write(1);
	A2.write(0);
	EN12.write(0);
	
	io RGB_RED(PTB,RED_LED_POS);

	volatile uint32_t _loop_ct_ = 0UL;
	while(1){

		redSet(0);
		greenSet(0);
		blueSet(1);
		
		RGB_RED.write(_loop_ct_ % 4);
		
		greenSet(1);
		blueSet(0);
		//lcd.clear(WHITE);
		
		if(_loop_ct_ % 50)
			lcd.setStr((char *)"Hello.",10,16,BLUE,WHITE);
		
		int col; rect r;
		col = (!S1.read()) ? GREEN : pickColor(0x2,0x2,0x2);	
		r = s1;
		lcd.setRect(r.x0,r.y0,r.x1,r.y1,1,col);
		col = (!S2.read()) ? GREEN : pickColor(0x2,0x2,0x2);	
		r = s2;
		lcd.setRect(r.x0,r.y0,r.x1,r.y1,1,col);
		col = (!S3.read()) ? GREEN : pickColor(0x2,0x2,0x2);	
		r = s3;
		lcd.setRect(r.x0,r.y0,r.x1,r.y1,1,col);

		delay(500);
		_loop_ct_++;
	}
	
}

