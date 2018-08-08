/*	main.cpp	*/


#define __ARM_KL25Z__

#include <MKL25Z4.H>
#include "misc_defs.h"
#include "adc_defs.h"

#include "Lcd6100.cpp"

Lcd6100 lcd;
volatile float measured_voltage ;  // scaled value
volatile unsigned res ;            // raw value 
 
void readLcdButtons();
void Init_ADC();
unsigned Measure(void);
void	leds_to_cyan();



/*
 *  Initialise ADC 
 */
void Init_ADC() {
	/* Set the ADC0_CFG1 to 0x9C, which is 1001 1100
	   1 --> low power conversion
	   00 --> ADIV is 1, no divide
	   1 --> ADLSMP is long sample time
	   11 --> MODE is 16 bit conversion
	   00 --> ADIClK is bus clock / 2 */
	
	
	//enter a value to configure ADC channel 0 register CFG1 
	ADC0->CFG1 = 0x9c;
	
	/* Set the ADC0_SC2 register to 0
	   0 --> DATRG - s/w trigger
	   0 --> ACFE - compare disable
	   0 --> ACFGT - n/a when compare disabled
	   0 --> ACREN - n/a when compare disabled
	   0 --> DMAEN - DMA is disabled
	   00 -> REFSEL - defaults V_REFH and V_REFL selected */
	
	ADC0->SC2 = 0;
}

unsigned Measure(void) {

   unsigned res = 0 ;
	 
	 // Write to ADC0_SC1A 
	 //   0 --> AIEN Conversion interrupt diabled
	 //   0 --> DIFF single end conversion 
	 //   01000 --> ADCH, selecting AD8 
   
	ADC0->SC1[0] = ADC_CHANNEL ; // writing to this clears the COCO flag 
	 
	 // test the conversion complete flag, which is 1 when completed
   while (!(ADC0->SC1[0] & ADC_SC1_COCO_MASK)); // empty loop
	
   // Read results from ADC0_RA as an unsigned integer	
   res = ADC0->R[0] ; // reading this clears the COCO flag  
	 
   return res ; 
}


void leds_to_cyan(){
		redSet(1);
		greenSet(0);
		blueSet(1);

}
void initLCD(){
		// enable LCD pins as GPIO
	ENABLE_GPIO(A,13);	//
	ENABLE_GPIO(D,5);	//
	ENABLE_GPIO(D,2);	//
	ENABLE_GPIO(D,1);	// 
	// enable LCD shield buttons S1-S3 as GPIO
	ENABLE_GPIO(A,12);	// button S1
	ENABLE_GPIO(A,4);	// button S2
	ENABLE_GPIO(A,5);	// button S3


		// LCD pins: set direction -> OUTPUT
	PTA->PDDR |= (MASK(13));
	PTD->PDDR |= (MASK(1) | MASK(2) | MASK(5));
	// LCD buttons S1-S3: set as inputs.
	PTA->PDDR &= ~(MASK(12) | MASK(4) | MASK(5));
	PTA->PSOR = (MASK(12)|MASK(4)|MASK(5));	
		

	lcd.init(0);
	lcd.clear(WHITE);
	lcd.clear(BLACK);
	
}
int main(void){
	

	// enable clock to RGB LED ports
	ENABLE_CLOCK_TO_PORT(B);
	ENABLE_CLOCK_TO_PORT(D);

	// enable RGB-LED pins as GPIO
	ENABLE_GPIO(B,18);	// 
	ENABLE_GPIO(B,19);	// 
	ENABLE_GPIO(D,1);	// 

	
	// Enable clock to ADC
	SIM->SCGC6 |= (1UL << SIM_SCGC6_ADC0_SHIFT);
	
	// enable clock to lcd ports
	ENABLE_CLOCK_TO_PORT(A);
	ENABLE_CLOCK_TO_PORT(D);
	

	io RED_LED(PTB,RED_LED_POS), GREEN_LED(PTB,GREEN_LED_POS), BLUE_LED(PTD,BLUE_LED_POS);
	
	initLCD();

	// RGB LED 
	PTB->PDDR |= (MASK(RED_LED_POS) | MASK(GREEN_LED_POS));
	PTB->PSOR = (MASK(RED_LED_POS)|MASK(GREEN_LED_POS));

	// loop counter
	volatile uint32_t _loop_ct_ = 0UL;
	

	while(1){

		leds_to_cyan();
		delay(100);

		
		redSet(0);
		greenSet(1);
		blueSet(0);
		
		lcd.setStr((char *)"Hello.",10,16,BLUE,WHITE);
		
		
		res = Measure() ;
		measured_voltage =  VREF * (res / ADCRANGE); 		

		delay(500);
		_loop_ct_++;
	}
	
} // end main

void readLcdButtons(){
	
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
	
}

