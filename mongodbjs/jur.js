//convert run only once

var dbtweets = db.tweets_jur;
var fixtime = 1433894400000;

dbtweets.find().forEach(function(x){ x.timestamp_ms = new NumberLong(x.timestamp_ms); dbtweets.save(x)})

var begin = fixtime;
var inc = 86400000;
for(var count = 0; count < 7; count++){
var timestamp = {};
timestamp.$gte = begin;
timestamp.$lt = begin + inc;
//print(JSON.stringify(timestamp))
var positive = 'Positive';
var negative = 'Negative';
var netural = 'Neutral';
//var c = dbtweets.find({timestamp_ms:timestamp}).count();
//print(c)

var cp = dbtweets.find({timestamp_ms:timestamp, sentiment:positive}).count();
//print('Positive')
print(cp)

begin += inc;

}
 
var begin = fixtime;
var inc = 86400000;
for(var count = 0; count < 7; count++){
var timestamp = {};
timestamp.$gte = begin;
timestamp.$lt = begin + inc;
//print(JSON.stringify(timestamp))
var positive = 'Positive';
var negative = 'Negative';
var netural = 'Neutral';

var cn = dbtweets.find({timestamp_ms:timestamp, sentiment:negative}).count();
//print('Negative')
print(cn)

begin += inc;

}
var begin = fixtime;
var inc = 86400000;
for(var count = 0; count < 7; count++){
var timestamp = {};
timestamp.$gte = begin;
timestamp.$lt = begin + inc;
//print(JSON.stringify(timestamp))
var positive = 'Positive';
var negative = 'Negative';
var netural = 'Neutral';

var cne = dbtweets.find({timestamp_ms:timestamp, sentiment:netural}).count();
//print('Neutral')
print(cne)

begin += inc;

}
