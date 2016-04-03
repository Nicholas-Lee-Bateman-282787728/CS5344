from nltk.sentiment.vader import SentimentIntensityAnalyzer
from nltk import tokenize

def analyzeTweet(tweet):
	#tweet text c
	sid = SentimentIntensityAnalyzer()
	ss = sid.polarity_scores(tweet)	 
	return ss        
	#for k in sorted(ss):
	#	print(k)
	#   	print(ss[k])



import requests, urllib, pprint
 
text = urllib.quote_plus("I've watched Furious 7 two times already ?")
url = "https://jamiembrown-tweet-sentiment-analysis.p.mashape.com/api/"
r = requests.get(url, {"text": text})
pprint.pprint(r.json())
