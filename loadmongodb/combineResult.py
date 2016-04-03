#jianmin
#combine all results in a month
#date 2016 Mar 20

from glob import iglob
import os.path
import io
import json
import re
import Sentiment

currentpath = os.path.dirname(os.path.abspath(__file__))
out = currentpath + '/input/'
des = currentpath + '/output/april-all'

def writeTweet(tweet):
   with io.open(des, 'a', encoding='utf-8') as f:
        f.write(unicode(json.dumps(tweet, ensure_ascii=False)))
	f.write('\n')


def loadData(fname):
	data = []
	with open(fname) as fin:		
		for line in fin:
		   try:
		   	tweet = json.loads(line)
			text = tweet['text']
			tm = tweet['timestamp_ms']
			result = Sentiment.get_sentiment_info(text)		
			data = {}
			data['text'] = text
			data['timestamp_ms'] = tm
                        data['sentiment'] = result
			print(text)
			json_data = json.dumps(data)		
			data.append(json_data)
		   except:
			continue
	#print(data)
        return data

tweets = []

for root, dirs, files in os.walk(out):
    for file in files:	
	valid = re.compile(r"^part")
	if re.match(valid, file):
        	#print('reading file:')
		#print(file)
                #print(os.path.join(root, file))
		path = os.path.join(root, file)
	 	tweet = loadData(path)
		tweets.extend(tweet)


for t in tweets:
      writeTweet(t)


