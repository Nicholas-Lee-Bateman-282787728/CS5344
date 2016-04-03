#jianmin
#load JSON to monogodb, make sure db is up
#date 2016 Mar 6

from glob import iglob
import os.path
from pymongo import MongoClient
import json
import re

currentpath = os.path.dirname(os.path.abspath(__file__))
movies = ['water']


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


for mv in movies:
   out = currentpath + '/input/' + mv
   tweets = loadData(out)
   collection = db['tweets_' +mv]
   for t in tweets:
	   print('write to collection:')
	   print(t)
	   collection.insert(t)
