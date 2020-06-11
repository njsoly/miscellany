import requests

#OHLC data
#r = requests.get('https://finnhub.io/api/v1/stock/candle?token=brgrd1frh5r9t6gjc440&symbol=AAPL&resolution=1&from=1572651390&to=1572910590')
#print(r.json())

#Tick data
r = requests.get('https://finnhub.io/api/v1/stock/tick?token=brgrd1frh5r9t6gjc440&symbol=AAPL&date=2020-06-05')
print(r.json())

# Quote data
r = requests.get('https://finnhub.io/api/v1/quote?token=brgrd1frh5r9t6gjc440&symbol=AAPL')
print(r.json())
