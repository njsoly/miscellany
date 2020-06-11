import requests

#OHLC data
r = requests.get('https://finnhub.io/api/v1/stock/candle?symbol=AAPL&resolution=1&from=1572651390&to=1572910590')
print(r.json())

#Tick data
r = requests.get('https://finnhub.io/api/v1/stock/tick?symbol=AAPL&date=2020-03-25')
print(r.json())

# Quote data
r = requests.get('https://finnhub.io/api/v1/quote?symbol=AAPL')
print(r.json())