#include "Lcd6100.hpp"
extern const uint8_t FONT8x16[97][16];


/*
 *  lcd6100.cpp	- library for the Nokia 6100 LCD shield from SparkFun electronics.
 *  This library copied basically all of the deficient, supplied code from 
 *  SparkFun and those associated, and began mending it to make it actually 
 *  useful.
 *  
 *  (It is still a work in progress.)
 *  
 */

// these are some method signatures from the header.
/*	void setPixel(int color, unsigned char x, unsigned char y);
	void setCircle (int x0, int y0, int radius, int color);

	void setChar(char c, int x, int y, int fColor, int bColor);
	void setStr(char *pString, int x, int y, int fColor, int bColor);

	void setLine(int x0, int y0, int x1, int y1, int color);
	void setRect(int x0, int y0, int x1, int y1, unsigned char fill, int color);
*/



int pickColor(char r, char g, char b){
	r = r << 0;
	g = g << 4;
	b = b << 8;
	return (r|g|b)^0xFFFF;
}


rgb getRGB(char r, char b, char g){
	rgb color;
	color.r=r;
	color.g=g;
	color.b=b;
	return color;
}

void Lcd6100::LCDCommand(unsigned char data){
	char jj;

	cbi(LCD_PORT_CS, CS);     // enable chip
	cbi(LCD_PORT_DIO, DIO);   // output low on data out (9th bit low = command)

	cbi(LCD_PORT_SCK, SCK);   // send clock pulse
	delayMicroseconds(1);
	sbi(LCD_PORT_SCK, SCK);

	for (jj = 0; jj < 8; jj++)
	{
		if ((data & 0x80) == 0x80)
			sbi(LCD_PORT_DIO, DIO);
		else
			cbi(LCD_PORT_DIO, DIO);

		cbi(LCD_PORT_SCK, SCK); // send clock pulse
		delayMicroseconds(1);
		sbi(LCD_PORT_SCK, SCK);

		data <<= 1;
	}

	sbi(LCD_PORT_CS, CS);     // disable
}

void Lcd6100::LCDData(unsigned char data)
{
	char j;

	cbi(LCD_PORT_CS, CS);     // enable chip
	sbi(LCD_PORT_DIO, DIO);   // output high on data out (9th bit high = data)

	cbi(LCD_PORT_SCK, SCK);   // send clock pulse
	delayMicroseconds(1);
	sbi(LCD_PORT_SCK, SCK);   // send clock pulse

	for (j = 0; j < 8; j++){
		if ((data & 0x80) == 0x80)
			sbi(LCD_PORT_DIO, DIO);
		else
			cbi(LCD_PORT_DIO, DIO);
	
		cbi(LCD_PORT_SCK, SCK); // send clock pulse
		delayMicroseconds(1);
		sbi(LCD_PORT_SCK, SCK);

		data <<= 1;
	}

	LCD_PORT_CS	|=	(1<<CS);  // disable
}

void Lcd6100::init(int type){
	PTA->PDDR |= (MASK(LCD_RES));
	PTD->PDDR |= (MASK(SCK) | MASK(DIO) | MASK(CS));

	driver = type;

	delay(100);

	cbi(LCD_PORT_SCK, SCK);     //CLK = LOW
	cbi(LCD_PORT_DIO, DIO);     //DIO = LOW
	delayMicroseconds(10);
	sbi(LCD_PORT_CS, CS);       //CS = HIGH
	delayMicroseconds(10);
	cbi(LCD_PORT_RES, LCD_RES); //RESET = LOW
	delay(100);
	sbi(LCD_PORT_RES, LCD_RES); //RESET = HIGH
	delay(100);
	sbi(LCD_PORT_SCK, SCK);     // SCK = HIGH
	sbi(LCD_PORT_DIO, DIO);     // DIO = HIGH
	delayMicroseconds(10);

	
	LCDCommand(SLEEPOUT); //sleep out(PHILLIPS)
	LCDCommand(BSTRON);   //Booset On(PHILLIPS)



	LCDCommand(COLMOD);   // Set Color Mode(PHILLIPS)
	LCDData(0x03);

	LCDCommand(MADCTL);   // Memory Access Control(PHILLIPS)
	LCDData(0xC8);


	LCDCommand(SETCON);   // Set Contrast(PHILLIPS)
	LCDData(0x30);

	LCDCommand(NOPP);     // nop(PHILLIPS)

	delayMicroseconds(100);

	LCDCommand(DISPON);   // display on(PHILLIPS)
	
}


void Lcd6100::setChar(char c, int x, int y, int fColor, int bColor){

	y	=	(COL_HEIGHT - 1) - y; // make display "right" side up
	x	=	(ROW_LENGTH - 1) - x;

	int             i,j;
	unsigned int    fontColCount;
	unsigned int    fontRowCount;
	unsigned int    numberOfBytes;
	unsigned char   pixelRow;
	unsigned char   Mask;
	unsigned int    word0;
	unsigned int    word1;
	unsigned char   *pFont;
	unsigned char   *pChar;

	// get pointer to the beginning of the selected font table
	pFont = (unsigned char *)FONT8x16;
	// get the nColumns, fontRowCount and numberOfBytes
	fontColCount = *pFont;
	fontRowCount = *(pFont + 1);
	numberOfBytes = *(pFont + 2);
	// get pointer to the last byte of the desired character
	pChar = pFont + (numberOfBytes * (c - 0x1F)) + numberOfBytes - 1;



	// Row address set (command 0x2B)
	LCDCommand(PASETP);
	LCDData(x);
	LCDData(x + fontRowCount - 1);
	// Column address set (command 0x2A)
	LCDCommand(CASETP);
	LCDData(y);
	LCDData(y + fontColCount - 1);

	// WRITE MEMORY
	LCDCommand(RAMWRP);
	// loop on each row, working backwards from the bottom to the top
	// pChar=pChar;  // stick pChar at the end of the row - gonna reverse print on phillips
	pChar+=numberOfBytes-1;  // stick pChar at the end of the row - gonna reverse print on phillips
	
	for (i = fontRowCount - 1; i >= 0; i--) {
	// for (i = 0; i < fontRowCount; i++) {
	
	// copy pixel row from font table and then decrement row
		pixelRow = *pChar--;
		// pixelRow = *pChar++;

		// loop on each pixel in the row (left to right)
		// Note: we do two pixels each loop
		Mask = 0x01;  // 
		
		// for (j = 0; j < fontColCount; j += 2){
		for (j = fontColCount - 1; j >= 0; j -= 2){
			// if pixel bit set, use foreground color; else use the background color
			// now get the pixel color for two successive pixels
			if ((pixelRow & Mask) == 0)
				word0 = bColor;
			else
				word0 = fColor;
			Mask = Mask << 1; // <- opposite of epson
			// Mask = Mask >> 1; // <- opposite of epson
			if ((pixelRow & Mask) == 0)
				word1 = bColor;
			else
				word1 = fColor;
			Mask = Mask << 1; // <- opposite of epson
			// Mask = Mask >> 1; // <- opposite of epson
	
		//	use this information to output three data bytes
			LCDData((word0 >> 4) & 0x00FF);
			LCDData(((word0 & 0x000F) << 4) | ((word1 >> 8) & 0xF));
			LCDData(word1 & 0xFF);
		}
	}
	
}

void Lcd6100::setStr(char *pString, int x, int y, int fColor, int bColor)
{
	y = y + 16;
	x = x + 8;
	// loop until null-terminator is seen
	while (*pString != 0x00) {
		// draw the character
		setChar(*pString++, y, x, fColor, bColor);
		// advance the y position
		x = x + 8;
		// bail out if y exceeds 131
		if (y >= COL_HEIGHT) break;
	}
}



void Lcd6100::setPixel(int color, unsigned char x, unsigned char y)
{
	y	=	(COL_HEIGHT - 2) - y; // -1 or -2 ?
	x = (ROW_LENGTH - 2) - x;	// -1 or -2 ?

	if (driver) // if it's an epson
	{
		LCDCommand(PASET);  // page start/end ram
		LCDData(x);
		LCDData(ENDPAGE);

		LCDCommand(CASET);  // column start/end ram
		LCDData(y);
		LCDData(ENDCOL);

		LCDCommand(RAMWR);  // write
		LCDData((color>>4)&0x00FF);
		LCDData(((color&0x0F)<<4)|(color>>8));
		LCDData(color&0x0FF);
	}
	else  // otherwise it's a phillips
	{
		LCDCommand(PASETP); // page start/end ram
		LCDData(y);
		LCDData(y);

		LCDCommand(CASETP); // column start/end ram
		LCDData(x);
		LCDData(x);

		LCDCommand(RAMWRP); // write

		LCDData((unsigned char)((color>>4)&0x0FF));
		LCDData((unsigned char)(((color&0x0F)<<4)));
	}
}

void Lcd6100::setCircle (int x0, int y0, int radius, int color)
{
	int f = 1 - radius;
	int ddF_x = 0;
	int ddF_y = -2 * radius;
	int x = 0;
	int y = radius;

	setPixel(color, x0, y0 + radius);
	setPixel(color, x0, y0 - radius);
	setPixel(color, x0 + radius, y0);
	setPixel(color, x0 - radius, y0);

	while(x < y)
	{
		if(f >= 0)
		{
			y--;
			ddF_y += 2;
			f += ddF_y;
		}
		x++;
		ddF_x += 2;
		f += ddF_x + 1;

		setPixel(color, x0 + x, y0 + y);
		setPixel(color, x0 - x, y0 + y);
		setPixel(color, x0 + x, y0 - y);
		setPixel(color, x0 - x, y0 - y);
		setPixel(color, x0 + y, y0 + x);
		setPixel(color, x0 - y, y0 + x);
		setPixel(color, x0 + y, y0 - x);
		setPixel(color, x0 - y, y0 - x);
	}
}

void Lcd6100::setLine(int x0, int y0, int x1, int y1, int color){
	int dy = y1 - y0; // Difference between y0 and y1
	int dx = x1 - x0; // Difference between x0 and x1
	int stepx, stepy;

	if (dy < 0)
	{
		dy = -dy;
		stepy = -1;
	}
	else
		stepy = 1;

	if (dx < 0)
	{
		dx = -dx;
		stepx = -1;
	}
	else
		stepx = 1;

	dy <<= 1; // dy is now 2*dy
	dx <<= 1; // dx is now 2*dx
	setPixel(color, x0, y0);

	if (dx > dy) 
	{
		int fraction = dy - (dx >> 1);
		while (x0 != x1)
		{
			if (fraction >= 0)
			{
				y0 += stepy;
				fraction -= dx;
			}
			x0 += stepx;
			fraction += dy;
			setPixel(color, x0, y0);
		}
	}
	else
	{
		int fraction = dx - (dy >> 1);
		while (y0 != y1)
		{
			if (fraction >= 0)
			{
				x0 += stepx;
				fraction -= dy;
			}
		y0 += stepy;
		fraction += dx;
		setPixel(color, x0, y0);
		}
	}
}

void Lcd6100::setRect(int x0, int y0, int x1, int y1, unsigned char fill, int color){
	// check if the rectangle is to be filled
	if (fill == 1)
	{
		int xDiff;
	
		if(x0 > x1)
			xDiff = x0 - x1; //Find the difference between the x vars
		else
			xDiff = x1 - x0;
	
		while(xDiff > 0)
		{
			setLine(x0, y0, x0, y1, color);
		
			if(x0 > x1)
				x0--;
			else
				x0++;
		
			xDiff--;
		}

	}
	else 
	{
		// best way to draw an unfilled rectangle is to draw four lines
		setLine(x0, y0, x1, y0, color);
		setLine(x0, y1, x1, y1, color);
		setLine(x0, y0, x0, y1, color);
		setLine(x1, y0, x1, y1, color);
	}
}

