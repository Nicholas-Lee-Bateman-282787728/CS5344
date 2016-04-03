#jianmin
#calculate movie statistics
#date 2016 Mar 27

from glob import iglob
import os.path
import io
import json
import re

ffmv = ["furious 7", "fastfurious", "furious7"]
tlrmv = ["the longest ride",  "thelongestride"]
pbmv = ["paul blart: mall cop 2", "paulblartmovie", "blartridesagain"]
aoamv = ["the age of adaline", "ageofadaline"]
currentpath = os.path.dirname(os.path.abspath(__file__))
#in JSON array format
inputFolder = currentpath + '/output/april-all'
des = currentpath + '/output/stats-'

def isFF(tweet):  
	movie = 'ff'
	text = tweet['text']
	for ff in ffmv:
            found = text.find(ff)
            if found > -1: return (movie, True)
        return (movie, False)

def isTLR(tweet):  
	movie = 'tlr'
	text = tweet['text']
	for tlr in tlrmv:
            found = text.find(tlr)
            if found > -1: return (movie, True)
        return (movie, False)


def isPB(tweet):  
	movie = 'pb'
	text = tweet['text']
	for pb in pbmv:
            found = text.find(pb)
            if found > -1: return (movie, True)
        return (movie, False)


def isAOA(tweet):  
	movie = 'aoa'
	text = tweet['text']
	for aoa in aoamv:
            found = text.find(aoa)
            if found > -1: return (movie, True)
        return (movie, False)


data = {'ff':[],'tlr':[],'pb':[],'aoa':[]}	
def loadData(fname):
	with open(fname) as fin:		
		for line in fin:
		   try:
		   	tweet = json.loads(line)
			print(tweet)
			ff, ffflag = isFF(tweet)
                        if (ffflag):
                          data[ff].append(tweet)

			tlr, tlrflag = isTLR(tweet)
			if (tlrflag):
			  data[tlr].append(tweet)
		    
			pb, pbflag = isPB(tweet)
			if (pbflag):
			  data[pb].append(tweet)			

			aoa,aoaflag = isAOA(tweet)
			if (aoaflag):
                          data[aoa].append(tweet)			
 
		   except:
			continue
	#print(data)
     	#return data

def writeTweet(des, tweet):
   with io.open(des, 'a', encoding='utf-8') as f:
        f.write(unicode(json.dumps(tweet, ensure_ascii=False)))
	f.write(unicode('\n'))

def getSentiment(tweets):
    for tweet in tweets:
          print(tweet)

loadData(inputFolder)

for t in data:
        des = des + t
	for d in data[t]:
           writeTweet(des, d)
