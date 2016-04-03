import unirest
import json

def analyzeTweet(tweet):
	#tweet text c
	url = "https://community-sentiment.p.mashape.com/text/"
	key = "l8qXF2XihymshauBjnOPxN95d62bp1bCGXMjsnU0hu16d9w0Fv"
        ctype = "application/x-www-form-urlencoded"
	accept = "application/json"
        response = unirest.post(url, headers = { "X-Mashape-Key": key,"Content-Type": ctype,"Accept": accept}, params={"txt": tweet})
	body = response.body
	#print(type(body))
	result = body['result']
	print(result['sentiment'])
	return result['sentiment']


#analyzeTweet('I went to watch fast and furious')
