#ifndef LCD6100_H_
#define LCD6100_H_

/* 	An arduino library for the sparkfun LCD shield
 * 	featuring the Nokia 6100 LCD screen and three 
 * 	pushbuttons.
 *	
 *	
 */

#include <MKL25Z4.H>
#include "FONT8x16.h"
#include "logo_spark.h"
#define PHILLIPS	0
#define EPSON		1

//*******************************************************
//						Macros
// set bit
#define sbi(var, mask)   ((var) |= (uint32_t)(1 << mask))
// clear bit
#define cbi(var, mask)   ((var) &= (uint32_t)~(1 << mask))
//*******************************************************

//********************************************************************
//
//					LCD Dimension Definitions
//
//********************************************************************
#define ROW_LENGTH	132
#define COL_HEIGHT	132
#define ENDPAGE     132
#define ENDCOL      130


//********************************************************************
//
//			PHILLIPS Controller Definitions
//
//********************************************************************
//LCD Commands
#define	NOPP		0x00	// No operation
#define	BSTRON		0x03	// Booster voltage on
#define SLEEPIN     0x10	// Sleep in
#define	SLEEPOUT	0x11	// Sleep out
#define	NORON		0x13	// Normal display mode on
#define	INVOFF		0x20	// Display inversion off
#define INVON      	0x21	// Display inversion on
#define	SETCON		0x25	// Set contrast
#define DISPOFF     0x28	// Display off
#define DISPON      0x29	// Display on
#define CASETP      0x2A	// Column address set
#define PASETP      0x2B	// Page address set
#define RAMWRP      0x2C	// Memory write
#define RGBSET	    0x2D	// Color set
#define	MADCTL		0x36	// Memory data access control
#define	COLMOD		0x3A	// Interface pixel format
#define DISCTR      0xB9	// Super frame inversion
#define	EC			0xC0	// Internal or external oscillator

#include "Epson_Defs.h"

//*******************************************************
//				12-Bit Color Definitions
//*******************************************************
#define RED			0x00F	// was 0xF00
#define BLUE		0xF00	// was 0x00F
#define	GREEN		0x0F0	// was 0x0F0

#define YELLOW		0x0FF	// was 0xFF0
#define CYAN		0xFF0	// was 0x0FF
#define MAGENTA		0xF0F	// was 0xF0F

#define PINK		0xA6F	// was 0xF6A
#define ORANGE 		0x0AF	// was 0xF40
#define SKYBLUE		0xEC8	// was 0x8CE
#define PURPLE 		0x808	// was 0x808
#define TURQUOISE	0xDE4	// was 0x4ED
#define INDIGO  	0x804	// was 0x408
#define SLATE 		0x442	// was 0x244

#define BLACK		0x000	// was 0x000
#define WHITE		0xFFF	// was 0xFFF
#define GRAY 		0x888	// was 0x888

#define NAVY 		0x800	// was 0x008
#define TEAL 		0x880	// was 0x088
#define EMERALD		0x5C0	// was 0x0C5
#define OLIVE 		0x286	// was 0x682
#define MAROON 		0x008	// was 0x800
#define BROWN		0x22B	// was 0xB22
#define CRIMSON 	0x31D	// was 0xD13
#define ORCHID 		0xD7D	// was 0xD7D
#define SALMON 		0x78F	// was 0xF87
#define CORAL 		0x57F	// was 0xF75
#define GOLD 		0x0DF	// was 0xFD0


#define FULLCIRCLE 1
#define OPENSOUTH  2
#define OPENNORTH  3
#define OPENEAST   4
#define OPENWEST   5
#define OPENNORTHEAST 6
#define OPENNORTHWEST 7
#define OPENSOUTHEAST 8
#define OPENSOUTHWEST 9

// FRDM-KL25Z stuff
	// bit numbers
	#define LCD_RES		13
	#define CS			5
	#define DIO			2
	#define SCK			1

	// ports
	#define LCD_PORT_CS		PTD->PDOR
	#define LCD_PORT_SCK	PTD->PDOR
	#define LCD_PORT_RES	PTA->PDOR
	#define LCD_PORT_DIO	PTD->PDOR
	
//	PTA->PDDR |= (MASK(LCD_RES));
//	PTD->PDDR |= (MASK(SCK) | MASK(DIO) | MASK(CS));
	
	static char x_offset = 0;
	static char y_offset = 0;


struct rgb {
	int r;
	int g;
	int b;
};

struct rgb getRGB(char r, char b, char g);

unsigned int pickColor(char r, char g, char b);
static int DEF_FG = GREEN, DEF_BG = BLACK;
class Lcd6100 {
	public:
			uint8_t driver;
	Lcd6100(){}
		int fg;
		int bg;
	void LCDCommand(unsigned char data);
	void LCDData(unsigned char data);
	void init(int type);
	void setChar(char c, int x, int y, int fColor, int bColor);
	void setStr(char *pString, int x, int y, int fColor, int bColor);
	void setPixel(int color, unsigned char x, unsigned char y);
	void setCircle (int x0, int y0, int radius, int color);

	void setLine(int x0, int y0, int x1, int y1, int color);
	
	void setRect(int x0, int y0, int x1, int y1, unsigned char fill, int color=DEF_FG);


	void clear(int color=BLACK){

		LCDCommand(PASETP);
		LCDData(0);
		LCDData(ENDPAGE);

		LCDCommand(CASETP);
		LCDData(0);
		LCDData(ENDCOL);

		LCDCommand(RAMWRP);	

		for(unsigned int i=0; i < (131*131)/2; i++)
		{
			LCDData((color>>4)&0x00FF);
			LCDData(((color&0x0F)<<4)|(color>>8));
			LCDData(color&0x0FF);
		}

		x_offset = 0;
		y_offset = 0;
	}

	void contrast(char setting){
		LCDCommand(SETCON);      // electronic volume, this is the contrast/brightness(EPSON)
		LCDData(setting);        // volume (contrast) setting - course adjustment,  -- original was 24

		LCDCommand(NOPP);         // nop(EPSON)
	}

private:
		
};


#endif // 
