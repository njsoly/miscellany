#ifndef MISC_DEFS_H_
#define MISC_DEFS_H_

#ifndef __AVR_ATmega2560__
#include <MKL25Z4.H>
#endif

#include <stdint.h>
#include <stdlib.h>

#ifdef __cplusplus
//	extern "C" {
#endif

#define LED_INTERVAL 127

#ifdef __ARM_KL25Z__
GPIO_Type * const PTs[]={PTA,PTB,PTC,PTD,PTE};
PORT_Type * const PORTAE[] =PORT_BASE_PTRS;
GPIO_Type * const GPIOAE[] = GPIO_BASE_PTRS; 

#define PTx(AZ) PT##AZ
#define PORTx(AZ) PORT##AZ
	
#define PDDR(AZ) PT##AZ->PDDR
#define PSOR(AZ) (PT##AZ->PSOR)
#define PDOR(AZ) (PT##AZ->PDOR)
#define PCR(AZ) (PORTx(AZ)->PCR)
#define ENABLE_GPIO(az,n) PCR(az)[n] &= ~PORT_PCR_MUX_MASK; PCR(az)[n] |= PORT_PCR_MUX(1)
#define ENABLE_CLOCK_TO_PORT(az) SIM->SCGC5 |= SIM_SCGC5_PORT##az##_MASK
//SIM_SCGC5_PORTA_MASK  ==  0x200u
#endif

#define MASK(x) (1UL << (x))  
typedef uint8_t u8;
typedef uint32_t u32;
typedef u8 _io_;


uint8_t pos(_io_ x);
void delayMicroseconds(volatile uint32_t delay_us);
void delay(volatile unsigned int delay_ms);

#if defined(__ARM_KL25Z__)
void delayMicroseconds(volatile uint32_t delay_us){
	volatile uint32_t t;
	while(delay_us-- > 0){
		for(t=2; t > 0; t--){
			
		}
	}
}

void delay(volatile uint32_t delay_ms){
	volatile int t;
	while(delay_ms--){
		for(t=4000; t>0; t--){
		}
	}

}
#endif

#ifdef __ARM_KL25Z__		

GPIO_Type* get_port(_io_ x){
	if(x <= 5 && x >= 1)		return PTs[x-1];
	else {
		x >>= 5;
		if(x <= 5 && x >= 1)	return PTs[x-1];
		else 					return 0;
	}		
	
}
uint8_t pos(_io_ x){
	return x & 0x1f;
}
// Freedom KL25Z LEDs
#define RED_LED_POS (18)		// on port B
#define GREEN_LED_POS (19)	// on port B
#define BLUE_LED_POS (1)		// on port D
		
#define _us_divider (CPU_XTAL_CLK_HZ/1000000UL)
#define _ms_divider (_us_divider*1000UL)
#endif


class io;
class button;
struct rect;


struct rect {
	uint8_t x0;
	uint8_t y0;
	uint8_t x1;
	uint8_t y1;
	rect(){};
	rect(uint8_t xa, uint8_t ya, uint8_t xb, uint8_t yb):x0(xa),y0(ya),x1(xb),y1(yb){
		rect();	
	};
	
} s1(2,48,12,58), s2(2,75,12,85), s3(2,102,12,112);

#ifdef __ARM_KL25Z__
class io {
public:
	GPIO_Type* port;
	const uint8_t bit;
	const uint8_t port_id;

	io(uint8_t port, uint8_t pin):port(PTs[port-1]),bit(pin),port_id(port){
		
	}
	io(GPIO_Type* gpio_t, u8 pin):port(gpio_t),bit(pin),port_id(init_port_id(gpio_t)){
	
	}
	io(_io_ x):port(PTs[(x>>5)-1]),bit(x & 0x1f),port_id(x >> 5){}
	
	unsigned int read(){
		uint32_t y = port->PDIR & MASK(this->bit);
		return y == 0 ? 0 : (uint8_t) (y);
	}
	void write(unsigned int x){
		if(x==0){
			port->PDOR &= ~MASK(bit);
		}
		else
			port->PDOR |= MASK(bit);
	}
	
	void setDirection(uint8_t dir){
		if(dir == 0)
			port->PDDR &= ~MASK(bit);
		else
			port->PDDR |= MASK(bit);
	}
	void toggle(){
		port->PTOR = MASK(bit);
	}
	void enableClockToPort(){
		SIM->SCGC5 |= (9<<(port_id-1));
	}
	void enableGPIO();
	
	
private:
	uint8_t init_port_id(GPIO_Type* gpio){
		for(unsigned int i = 1; i <= 5; i++){
			if(PTs[i-1]==gpio){
				return i;
			}
		}
		return 0;
	}
};
class button : public io{
protected:
public:
	button(GPIO_Type* port_pdir, uint8_t bit):io(port_pdir,bit){}

	rect r;
	unsigned int bg;// = pickColor(0x2,0x2,0xA);
	unsigned int fg;// = pickColor(0x0,0xD,0x1);


	bool dispEn;

}; 
button S1(PTA,12), S2(PTA,4), S3(PTA,5);


struct JHEADER {
	uint8_t numPins;
	
};
#endif



#ifdef __ARM_KL25Z__
void redSet(uint8_t red){
	if(red){
		PTB->PDOR &= ~MASK(RED_LED_POS);
	}
	else {
		PTB->PDOR |= MASK(RED_LED_POS);
	}
}
void blueSet(uint8_t blu){
	if(blu){
			 PTD->PDOR &= ~MASK(BLUE_LED_POS) ;
	}
	else {
		PTD->PDOR |= MASK(BLUE_LED_POS);
	}
}
void greenSet(uint8_t grn){
	if(grn){
			 PTB->PDOR &= ~MASK(GREEN_LED_POS) ;
	}
	else
		PTB->PDOR |= MASK(GREEN_LED_POS);
	
}
void ledOff(void){
	
	PTD->PDOR |= MASK(BLUE_LED_POS); ;
	PTB->PDOR |= MASK(RED_LED_POS) | MASK(GREEN_LED_POS) ;
}
#endif
#ifdef __ARM_KL25Z__
void io::enableGPIO(){
	PORT_Type *port_base = ((PORT_Type *) (PORTAE[port_id-1]));
	port_base->PCR[bit] &= ~PORT_PCR_MUX_MASK;
	port_base->PCR[bit] |= PORT_PCR_MUX(1);
	//PCR(az)[n] &= ~PORT_PCR_MUX_MASK; PCR(az)[n] |= PORT_PCR_MUX(1)
}
#endif 

#ifdef __cplusplus
//	}
#endif

#endif // MISC_DEFS_H_

