#jianmin
#load JSON to monogodb, make sure db is up
#date 2016 Mar 6

from glob import iglob
import os.path
from pymongo import MongoClient
import json
import re

currentpath = os.path.dirname(os.path.abspath(__file__))
out = currentpath + '/input/'


def loadData(fname):
	data = []
	with open(fname) as fin:		
		for line in fin:
		   try:
		   	tweet = json.loads(line)
		    	data.append(tweet)
		   except:
			continue
	#print(data)
        return data


client = MongoClient('localhost', 27017)
db = client['tweets']
collection = db['tweets_adaline']
	
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
   print('write to collection:')
   print(t)
   collection.insert(t)


#for fname in iglob(os.path.expanduser(currentpath + '/input/part-*')):
 #   print(fname)
