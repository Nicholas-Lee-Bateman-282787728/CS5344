from __future__ import division
from math import log, exp
from operator import mul
from collections import Counter
import os
import pickle
from glob import iglob
import io
import json
import re

class MyDict(dict):
    def __getitem__(self, key):
        if key in self:
            return self.get(key)
        return 0

pos = MyDict()
neg = MyDict()
features = set()
totals = [0, 0]
delchars = ''.join(c for c in map(chr, range(128)) if not c.isalnum())

# CDATA_FILE = "countdata.pickle"
currentpath = os.path.dirname(os.path.abspath(__file__))

FDATA_FILE = currentpath +"/reduceddata.pickle"

out = currentpath + '/input/'
des = currentpath + '/output/april-all'

def negate_sequence(text):
    """
    Detects negations and transforms negated words into "not_" form.
    """    
    negation = False
    delims = "?.,!:;"
    result = []
    words = text.split()
    prev = None
    pprev = None
   
    for word in words:
        # stripped = word.strip(delchars)
        stripped = word.strip(delims).lower()
        negated = "not_" + stripped if negation else stripped
        result.append(negated)
        if prev:
            bigram = prev + " " + negated
            result.append(bigram)
            if pprev:
                trigram = pprev + " " + bigram
                result.append(trigram)
            pprev = prev
        prev = negated

        if any(neg in word for neg in ["not", "n't", "no"]):
            negation = not negation

        if any(c in word for c in delims):
            negation = False
    #print(result)
    return result

def classify2(text):
    """
    For classification from pretrained data
    """
    #print(pos)
    #print(neg)
    words = set(word for word in negate_sequence(text) if word in pos or word in neg)
    #print(words)
    if (len(words) == 0): return True, 0
    # Probability that word occurs in pos documents
    pos_prob = sum(log((pos[word] + 1) / (2 * totals[0])) for word in words)
    neg_prob = sum(log((neg[word] + 1) / (2 * totals[1])) for word in words)
    return (pos_prob > neg_prob, abs(pos_prob - neg_prob))

def feature_selection_trials():
    """
    Select top k features. Vary k and plot data
    """
    global pos, neg, totals, features
    retrain = False
    if not retrain and os.path.isfile(FDATA_FILE):
        pos, neg, totals = pickle.load(open(FDATA_FILE))
	#print(pos)
	#print(neg)
        return


def setup():
    feature_selection_trials()

def get_sentiment_info(text):
	flag, confidence = classify2(text)
	#print(confidence)
	if confidence > 0.5:
		sentiment = "Positive" if flag else "Negative"
	else:
		sentiment = "Neutral"
	
	return sentiment



def writeTweet(tweet):
   with io.open(des, 'a', encoding='utf-8') as f:
        f.write(unicode(json.dumps(tweet, ensure_ascii=False)))
	f.write(unicode('\n'))


def loadData(fname):
	results = []
	with open(fname) as fin:		
		for line in fin:
		   try:
		   	tweet = json.loads(line)
			text = tweet['text']
			tm = tweet['timestamp_ms']
			result = get_sentiment_info(text)		
			data = {}
			data['text'] = text
			data['timestamp_ms'] = tm
                        data['sentiment'] = result
			print(data)		
			results.append(data)
		   except:
			continue
	#print(data)
        return results


#run
setup()

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
