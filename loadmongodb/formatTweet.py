#jianmin
#combine all results in a month
#add sentiment analysis using Python Vader lib
#date 2016 Mar 20

from glob import iglob
import os.path
import io
import json
import re
import VaderAnalysis

currentpath = os.path.dirname(os.path.abspath(__file__))
#in JSON array format
inputFolder = currentpath + '/output/april-all'
des = currentpath + '/output/april-all-clean'

def loadData(fname):
	data = []
	with open(fname) as fin:		
		for line in fin:
		   try:
		   	tweet = json.loads(line)
			#print(len(tweet))
			#break
			data.append(tweet)
		   except:
			continue
	#print(data)
     	return data

def getSentiment(tweets):
    for tweet in tweets:
          print(tweet)

tweets = loadData(inputFolder)
getSentiment(tweets)
